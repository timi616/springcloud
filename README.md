1.创建Maven项目 删除项目中的src目录
	1.2.配置父类的pom.xml
	1.3.创建新module 选择 maven 创建项目（springcloud-api）
	1.4.配置springcloud-api 中的pom.xml
	1.5.创建实体类 pojo —> Ooeg
	1.6.创建新module 选择 maven 创建项目(springcloud-provider-ooeg-8001) provider:提供者 ooeg:谁提供的 8001:端口
	1.7.配置springcloud-provider-ooeg-8001 中的pom.xml



2.Eureka服务注册中心 (AP原则) C/S架构： zookeeper(CP)
	A：可以用性
	C：一致性
	P：分区容错性
	三大角色：
		Eureka Server:提供服务的注册于发现
		Service Provider:将自身的服务注册到Eureka中
		Service Consumer:服务消费方从Eureka中获取注册服务列表，从而找到服务

	2.1、创建Eureka模块(springcloud-eureka-7001)

	2.2.服务提供者(springcloud-provider-ooeg-8001)中的
		2.2.1.pom.xml配置引用
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-eureka</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>
		
		<!--actuator 完善监控信息-->
		<dependency>
	   	    <groupId>org.springframework.boot</groupId>
    		    <artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>


		2.2.2.application.yml中配置
		#Eureka 的配置，服务注册的哪里
		eureka:
	  		client:
			    	service-url:
			      	  defaultZone: http://localhost:7001/eureka/
	
		#info配置
		info:
	  	  app.name: qs-springcloud #应用名称
		  company.name: com.qs.com #公司名称


		2.2.3.在启动类里添加 @EnableEurekaClient //在服务启动后自动注册到Eureka中

	2.3.测试方式： 先启动服务注册模块(springcloud-eureka-7001),在启动服务提供模块(springcloud-provider-8001)在页面上访问服务注册模块的地址： http://localhost:7001/
	
	2.4. http://localhost:7001/ 页面上有红色提示  ： 自我保护机制
	
	2.5.获取服务信息
		1.在服务提供模块中的Controller中添加方法
		//注册进来的服务，获取一下信息
		@GetMapping("/Ooeg/discovery")
		public Object discovery(){
		    //获取微服的列表清单
		    List<String> services = client.getServices();
		    System.out.println("微服务清单："+services);

		    //得到一个具体的微服务信息,通过具体的微服务id获取
		    List<ServiceInstance> instances = client.getInstances("SPRINGCLOUD-PROVIDER-OOEG");
		    for (ServiceInstance instance:instances) {
		        System.out.println(
	                instance.getHost()+"\t"+
        		        instance.getPort()+"\t"+ 
                		instance.getUri()+"\t"+
                		instance.getServiceId()
        			);
    			}
    
		    return  this.client;
		}
		2.在启动类里天加 @EnableDiscoveryClient //服务发现
	
	2.6.注册集群 springcloud-eureka-7001\springcloud-eureka-7002\springcloud-eureka-7003
		2.6.1.一个服务停止 也没有问题 会到其他服务找
		windows上域名映射：Windows/System32/drivers/etc/hosts

3.ribbon是基于Netflix Ribbon实现的一套  客户端负载均衡的工具(LB:Load Balance)，是根据微服务名称来实现负载均衡的
	
	。服务提供者 注册的eureka 形成可以使用的服务列表
	。ribbon 到eureka中获取可以使用的服务列表
	。ribbon 以默认轮巡的方式请求  服务提供者 （实现负载均衡）
	
	3.1.分类
		集中式LB ：如Nginx:翻新代理服务器
		进程式LB ：消费方从服务注册中心获取有那些地址能用
	
	springcloud-consumer-Ooeg-80
	
	3.2.在客户模块的pom.xml配置依赖
		<!--集成Ribbon 负载均衡-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-ribbon</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>
		<!--要的服务注册中心那地址，所以配置eureka-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-eureka</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>
	
	3.3.在application.yml 配置
		#Eureka配置
		eureka:
		  client:
		    register-with-eureka: false #不想Eureka注册自己
		    service-url:
		#      单机 http://localhost:7003/eureka/
		#      集群 http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://	eureka7003.com:7003/eureka/
		      defaultZone: http://localhost:7003/eureka/
	3.4 在启动类里添加 @EnableEurekaClient //Ribbon 和 Eureka 整合之后，客户端可以直接调用，不用关心IP端口和地址
	

	3.5.在ConfigBean中添加注解
		/*
		这样就可以把RestTemplate模板注册到Bean中
		*/

		//配置负载均衡实现RestTemplate 添加注解@LoadBalanced
		@Bean	
		@LoadBalanced //Ribbon
		public RestTemplate getRestTemplate(){
		    return  new RestTemplate(); //使用默认模板
		}
	
	3.6.在OoegconsumerController 中 修改变量
	//使用Ribbon负载均衡，这里的 REST_URL_PREFIX 变量 应该是eureka注册中心的服务名称
	private static final String REST_URL_PREFIX = "http://SPRINGCLOUD-PROVIDER-OOEG";

	3.7.测试 ：启动注册中心springcloud-eureka-700*，启动服务提供者springcloud-provider-ooeg-8001，启动客户模块springcloud-consumer-Ooeg-80        之后访问http://localhost/consumer/ooeg/list  (客户模块中的方法)

	
	3.8.创建多个服务提供者 springcloud-provider-ooeg-8001 springcloud-provider-ooeg-8002 springcloud-provider-ooeg-8003    :  测试负载均衡，默认是轮巡的方式去访问不同的提供模块
		
		
	3.9.自定义负载均衡算法
		3.9.1 在客户端的ConfigBean中
			/*
 			* 自定义Ribbon 算法
 			*
			 * 1.IRule
			 * 2.RoundRobinRule 轮询
			 * 3.RandomRule 随机
			 * 4.Retry 会先按照轮询获取服务，如果服务获取失败则会在指定的时间内进行重试
			 * 5.AvailabilityFilteringRule : 会先过滤掉 跳闸、访问故障的服务，对剩下的服务进行轮询
			 *
 			* */
			@Bean
			public IRule myRule(){
			    return new RandomRule(); //使用提供的
			}
		
		3.9.2 创建自己均衡算法  
			3.8.2.1.不能和启动类同级否则会直接扫描到  在com.qs包下创建com.qs.myrule.QsRule
			3.8.2.2.在启动类里添加注解 @RibbonClient(name = "SPRINGCLOUD-PROVIDER-OOEG",configuration = QsRule.class)  //在微服务启动的时候就能去加载我们自定义的Ribbon类
			3.8.2.3.创建 com/qs/myrule/QsRandomRule.java 复制的系统方法   自己修改符合自己的方法


4.Feign 负载均衡 根据接口和注解实现的，旨意是使在编写Java Http客户端变的更容易
	4.1.在springcloud-api中添加service包
	 4.2.创建一个新的模块 springcloud-consumer-Ooeg-feign 复制 springcloud-consumer-Ooeg-80，删除启动类里的注解@RibbonClient(name = "SPRINGCLOUD-PROVIDER-OOEG",configuration = QsRule.class)  //在微服务启动的时候就能去加载我们自定义的Ribbon类
	 4.3.在4.1中添加一个接口OoegClientService
	4.4.在springcloud-consumer-Ooeg-feign和springcloud-api模块的pom.xml中添加依赖
		<!--集成Feign 负载均衡 基于接口和注解-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-feign</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>
	4.5.在4.3中添加注解 @FeignClient(value = "SPRINGCLOUD-PROVIDER-OOEG") //value 微服务名称
	4.6.在springcloud-consumer-Ooeg-feign 模块中的OoegConsumerController 中定义的变量REST_URL_PREFIX注释
使用@Autowired
private OoegConsumerController ooegConsumerController;
	4.7 在springcloud-consumer-Ooeg-feign的启动类里配置注解
		@EnableFeignClients(basePackages = {"com.qs.springcloud"}) //扫描
		@ComponentScan("com.qs.springcloud") //扫描
	4.8 启动测试 7003 \ 8003 \ feign



5.Hystrix 是一个用于处理分布式系统的延迟和容错的开源库，
在5秒内20次调用失败就会启动熔断机制，熔断机制的注解@HystrixCommand，
在链路上的某个微服务的调用响应超时或者不用  那当前服务的调用占用系统资源就会越来越多  进而引起系统崩溃即“雪崩效应”
	
	
	。服务熔断：
	5.1 复制springcloud-provider-ooeg-8001 改成 springcloud-provider-ooeg-hystrix-8001
	5.2 导入hystrix的依赖
		<!--hystrix 的依赖-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-hystrix</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>
	5.3 编辑Controller 在要熔断的方法上加注解@HystrixCommand(fallbackMethod = "hystrixGet") //只要这个方法失败就会调用自己写的备选方法
	5.4 在启动类上加注解 @EnableCircuitBreaker//添加对熔断的支持
	
	。服务降级：
	5.1 在springcloud-api模块中的service包中建类 OoegClientServiceFailbackFactory
	5.2 在OoegClientService 方法上的@FeignClient的注解上添加参数
		@FeignClient(value = "SPRINGCLOUD-PROVIDER-OOEG",fallbackFactory = OoegClientServiceFallbackFactory.class) //测试Hystrix降级添加参数fallbackFactory
	5.3 在springcloud-consumer-Ooeg-feign模块中上application.yml中开启
		#开启降级 feign.hystrix
		feign:
		  hystrix:
		    enabled: true
	5.4 开启测试 启动 7003 | 8001 | feign 三个模块 进行访问，关闭 8001 在访问 会提示自己定义的错误
	
	。服务监控 Dashboard流监控
	5.1 创建 springcloud-consumer-hystrix-dashboard 模块，在该模块中引入依赖  并确保提供者模块有监控依赖

		<!--Hystrix 熔断依赖-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-hystrix</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>
		<!--hystrix dashboard 监控依赖-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>

	5.2 开启springcloud-consumer-hystrix-dashboard 模块 （报错没有影响）
	5.3 访问 http://localhost:9001/hystrix 会出现一个页面
	
	实时监控
	5.4 启动7003 访问 http://localhost:7003/
	5.5 启动 hystrix-dashboard 访问 http://localhost:9001/hystrix  放在那里
	5.5 启动hystrix-two-8001     在启动之前引入依赖
			5.5.1 在启动类中添加一个方法  固定的
			//增加一个Servlet
			@Bean
			public ServletRegistrationBean HystrixMetricsStreamServlet(){
   				ServletRegistrationBean registrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
    				registrationBean.addUrlMappings("/actuator/hystrix.stream");
    				return registrationBean;
			}

 	5.6 访问 localhost:8001/actuator/hystrix.stream 苹果电脑会让下载一个东西可以不下
	5.7 将 localhost:8001/actuator/hystrix.stream 放在5.5页面上的地址位置，delay:2000，title:自己定义，之后回车会出现一个页面
	5.8 可能会没有数据，请求hystrix-two-8001模块中的方法 http://localhost:8001/ooeg/get/012 ，就会有图形和数据
	
	
6.Zuul 网关  包含了对请求的路由和过滤两个主要的功能：
	其中路由功能负载将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础
	过滤功能则是对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础	
	
	6.1 创建模块 springcloud-zuul 添加依赖
		<!--zuul-->
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-zuul</artifactId>
		    <version>1.4.6.RELEASE</version>
		</dependency>

	6.2  配置application.yml
	6.3  启动类添加注解@EnableZuulProxy //
	6.4  启动 7001 |  hystrix-8001 | zuul
	6.5  使用这个地址http://localhost:8001/ooeg/get/012 能访问到数据
	6.6  使用这个地址也能访问到数据 http://localhost:9527/springcloud-provider-ooeg/ooeg/get/012，springcloud-provider-ooeg服务名称
	6.7 将项目名称隐藏 改成自定义的
		zuul:
		  routes:
		    user-service:
		      path: /mydept/**
		      serviceId: springcloud-provider-ooeg
      ##    ignored-services: springcloud-provider-ooeg #不能再使用这个路径在访问，springcloud-provider-ooeg 隐藏单个，*隐藏全部
      ##    prefix: /qs #设置公共的前缀



