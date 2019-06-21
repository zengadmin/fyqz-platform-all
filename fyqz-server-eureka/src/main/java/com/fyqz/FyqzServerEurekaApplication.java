package com.fyqz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Scanner;


@EnableEurekaServer
@SpringBootApplication
public class FyqzServerEurekaApplication {
    public static void main(String[] args) {
        System.out.println("============================================================= 开始启动eureka服务 =============================================================");
        System.out.println("请在控制台输入您要启动的 eureka 服务器的节点名称 —— [详细配置请查看application.yml 文件中配置的 spring.profiles 的值]");
        System.err.println("请输入 slave1 或者 slave2");
        Scanner scanner = new Scanner(System.in);
        String profiles = scanner.nextLine();
        new SpringApplicationBuilder(FyqzServerEurekaApplication.class).profiles(profiles).run(args);//启动项目
        System.out.println("============================================================= eureka服务启动成功 =============================================================");
    }
}
