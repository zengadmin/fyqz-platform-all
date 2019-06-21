package com.fyqz;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Scanner;

@EnableSwagger2Doc
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FyqzServerChessApplication {

	public static void main(String[] args) {
		System.out.println("================================================== 开始启动 fyqz-service-chess =============================================================");
		System.out.println("请在控制台指定fyqz-service-chess应用的端口号 —— [端口号随意指定，注意不要与本机端口号出现冲突即可]");
		Scanner scanner = new Scanner(System.in);
		String port = scanner.nextLine(); //让用户指定端口号
		new SpringApplicationBuilder(FyqzServerChessApplication.class).properties("server.port=" + port).run(args);//启动项目
		System.out.println("==================================================应用fyqz-service-chess 启动成功 =============================================================");
	}
}
