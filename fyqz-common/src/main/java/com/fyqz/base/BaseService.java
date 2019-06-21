package com.fyqz.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fyqz.cache.CacheUtil;
import com.fyqz.constants.Constants;
import com.fyqz.exception.BusinessException;
import com.fyqz.result.ResultEnum;
import com.fyqz.util.DataUtil;
import com.fyqz.util.InstanceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Title: BaseService
 * @ProjectName: fyqz-platform
 * @Description: TODO
 * @author: zengchao
 * @date: 2019/5/16 10:17
 */
public abstract class BaseService<T extends BaseModel, M extends BaseMapper<T>> implements ApplicationContextAware {
    protected Logger logger = LogManager.getLogger();
    /**
     * 缓存键值
     */
    Map<Class<?>, String> cacheKeyMap = new HashMap<>();
    @Autowired
    protected M mapper;

    protected ApplicationContext applicationContext;

    @Value("${db.reader.list.maxThread}")
    int maxThread = 50;
    @Value("${db.reader.list.threadWait}")
    int threadSleep = 5;

    ExecutorService executorService = new ThreadPoolExecutor(maxThread, maxThread,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * @param entity 查询参数
     * @return 查询页数
     */
    public List<T> selectList(Wrapper<T> entity) {
        return mapper.selectList(entity);
    }



    /**
     * 根据Id查询(默认类型T)
     */
    public List<T> getList(final List<String> ids) {
        final List<T> list = InstanceUtil.newArrayList();
        if (DataUtil.isEmpty(ids)) {
            return list;
        }
        int size = ids.size();
        for (int i = 0; i < size; i++) {
            list.add(null);
        }
        final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
        for (int i = 0; i < size; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        list.set(index, queryById(ids.get(index)));
                    } catch (Exception e) {
                        logger.error("", e);
                    } finally {
                        thread.put(index, 0);
                    }
                }
            });
        }
        while (thread.size() < size) {
            try {
                Thread.sleep(threadSleep);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        return list;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        try {
            mapper.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        try {
            CacheUtil.getCache().del(getCacheKey(id));
        } catch (Exception e) {
            logger.error(ResultEnum.ERROR.getMsg(), e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByEntity(T t) {
        Wrapper<T> wrapper = new EntityWrapper<>(t);
        return mapper.delete(wrapper);
    }

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public T update(T record) {
        try {
            if (record.getId() == null) {
                mapper.insert(record);
                try {
                    CacheUtil.getCache().set(getCacheKey(record.getId()), record);
                } catch (Exception e) {
                    logger.error(ResultEnum.ERROR.getMsg(), e);
                }
            } else {
                String lockKey = getLockKey("U" + record.getId());
                if (CacheUtil.tryLock(lockKey)) {
                    try {
                        T org = null;
                        String key = getCacheKey(record.getId());
                        try {
                            org = (T) CacheUtil.getCache().get(key);
                        } catch (Exception e) {
                            logger.error(ResultEnum.ERROR.getMsg(), e);
                        }
                        if (org == null) {
                            org = mapper.selectById(record.getId());
                        }

                        T update = InstanceUtil.getDiff(org, record);
                        if (null != update) {
                            update.setId(record.getId());
                            mapper.updateById(update);
                        }
                        record = mapper.selectById(record.getId());
                        try {
                            CacheUtil.getCache().set(key, record);
                        } catch (Exception e) {
                            logger.error(ResultEnum.ERROR.getMsg(), e);
                        }
                    } finally {
                        CacheUtil.unlock(lockKey);
                    }
                } else {
                    throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
                }
            }
        } catch (DuplicateKeyException e) {
            logger.error(ResultEnum.ERROR.getMsg(), e);
            throw new RuntimeException("已经存在相同的配置.");
        } catch (Exception e) {
            logger.error(ResultEnum.ERROR.getMsg(), e);
            throw new BusinessException(e.getCause());
        }
        return record;
    }

    protected void sleep(int millis) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    public T queryById(String id) {
        return queryById(id, 1);
    }

    @SuppressWarnings("unchecked")
    private T queryById(String id, int times) {
        String key = getCacheKey(id);
        T record = null;
        try {
            record = (T) CacheUtil.getCache().get(key);
        } catch (Exception e) {
            logger.error(ResultEnum.ERROR.getMsg(), e);
        }
        if (record == null) {
            String lockKey = getLockKey(id);
            if (CacheUtil.tryLock(lockKey)) {
                try {
                    record = mapper.selectById(id);
                    try {
                        CacheUtil.getCache().set(key, record);
                    } catch (Exception e) {
                        logger.error(ResultEnum.ERROR.getMsg(), e);
                    }
                } finally {
                    CacheUtil.unlock(lockKey);
                }
            } else {
                if (times > 3) {
                    return mapper.selectById(id);
                }
                logger.debug(getClass().getSimpleName() + ":" + id + " retry queryById.");
                sleep(20);
                return queryById(id, times + 1);
            }
        }
        return record;
    }

    /**
     * 获取缓存键值
     */
    protected String getCacheKey(Object id) {
        String cacheName = getCacheKey();
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
    }

    /**
     * 获取缓存键值
     */
    protected String getLockKey(Object id) {
        String cacheName = getCacheKey();
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":LOCK:").append(id).toString();
    }

    /**
     * @return 缓存key
     */
    private String getCacheKey() {
        Class<?> cls = getClass();
        String cacheName = cacheKeyMap.get(cls);
        if (StringUtils.isBlank(cacheName)) {
            CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
            if (cacheConfig != null && DataUtil.isNotEmpty(cacheConfig.cacheNames())) {
                cacheName = cacheConfig.cacheNames()[0];
            } else {
                cacheName = getClass().getName();
            }
            cacheKeyMap.put(cls, cacheName);
        }
        return cacheName;
    }
}
