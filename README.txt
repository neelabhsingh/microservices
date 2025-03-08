add this as vm argument from preferences installed jres  In case inaccessible 
security exception
 
 
--add-opens java.base/java.lang=ALL-UNNAMED
 
 
Create Oracle Table
==================
Create below table
 
create table Currency (id number(6),CONVERSION_MULTIPLE  number (5,2) , ENV varchar2(8),CURRENCY_FROM  varchar2(5),CURRENCY_TO  varchar2(5) );
insert into Currency(id,conversion_multiple,env,currency_from,currency_to)
values(10001,86,'','USD','INR');
insert into Currency (id,conversion_multiple,env,currency_from,currency_to)
values(10002,88,'','EUR','INR');
insert into Currency(id,conversion_multiple,env,currency_from,currency_to)
values(10003,54,'','AUD','INR');
commit;
select * from Currency;
 
 
# Configure the datasource conating database information
#DataScource Configuration for H2
#spring.datasource.url=jdbc:h2:mem:testdb  
#spring.datasource.driver-class-name=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=
 
#DataScource Configuration for Oracle
 
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=system
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
 
#JPA Hibernate properties configuration  
# This only Required if you wants use Hibernate/Spring Data JPA
spring.jpa.hibernate.ddl-auto =update
spring.jpa.show-sql=true
#spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.defer-datasource-initialization=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
 
#spring.h2.console.enabled=true
#spring.h2.console.path=/h2
#
 
 
 
MSA Setp Guide
----------------
 
Day-1 Create the currency-exchange-service add beolw dependices
 
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
 
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-devtools</artifactId>
<scope>runtime</scope>
<optional>true</optional>
</dependency>
 
 
Change the server port and give the application name as below
==============================================================
server.port=8000
spring.application.name=currency-exchange
 
Create the controller named as  CurrencyExchangeController inside 
com.web.springcloud.controllers package
 
Declare a webservice method to return a response Welcome to  CurrencyExchange Service .
 
Create a class named as CurrencyExchange  inside com.web.springcloud.bean package
 
public class CurrencyExchange {
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	private String env;
Genrate setter/getter/noargs constructor / parametrized constructor ,	
}
 
Create the controller named as  CurrencyExchangeController inside 
com.web.springcloud.controllers package
 
Rturn  the hard coded response using http://localhost:8000/currency-exchange/from/USD/to/INR
Response structure
 
{"id":1000,"from":"USD","to":"INR","conversionMultiple":50}
 
 
Add Below dependency incase of data base 
===============================================
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
 
 
<dependency>
<groupId>com.oracle.ojdbc</groupId>
<artifactId>ojdbc8</artifactId>
<version>19.3.0.0</version><!--$NO-MVN-MAN-VER$-->
</dependency>
 
 
#Oracle settings
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=hr
spring.datasource.password=hr
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
 
#Hibernate properties  
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
 
 
Create a reposistory named as CurrencyExchangeReposistory and autowire 
to CurrencyExchangeController
----------------------------------------------------------------------------------------------------
 
 
Create  currency-conversion-service add beolw dependices
============================================================
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
 
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-devtools</artifactId>
<scope>runtime</scope>
<optional>true</optional>
</dependency>
 
 
Change the server port and give the application name as below
==============================================================
server.port=8100
spring.application.name=currency-conversion
 
 
Create a class named as CurrencyConversion  inside com.web.springcloud.bean package
-------------------------------------------------------------------------------------
public class CurrencyConversion {
private Long id;
private String from;
private String to;
private BigDecimal quantity;
private BigDecimal conversionMultiple;
private BigDecimal totalCalculatedAmount;
private String env;
//Genrate setter/getter/noargs constructor / parametrized constructor ,	
}
 
 
Create the controller named as  CurrencyConversionController inside com.web.springcloud.controllers package
------------------------------------------------------------------------------------------------------------
@RestController
public class CurrencyConversionController {
private static Logger logger= LoggerFactory.getLogger(CurrencyConversion.class);
}
 
Creating a Eureka Server
-------------------------
 
Step-1 Create a Spring Boot application named as eureka-server and 
Add spring-cloud-starter-netflix-eureka-server dependency
 
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
 
Step-2
 
Add @EnableEurekaServer annotation on top of the class  annotated with @SpringBootApllication .
By doing so this application now acts as Service Registry all 
other Microservices can register themselves by using this service URL
 
Step-3 
------
After enabling Eureka  Service Registry add  following  configuration inside 
application.properties file
 
spring.application.name=eureka-server
eureka.client.serviceUrl.defaultZone:http://localhost:8761/eureka/
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
 
Let’s Understand use of Above Properties 
----------------------------------------------
spring.application.name : Unique name for Eureka server service
 
eureka.client.serviceUrl.defaultZone –  
It consult with other Eureka server for sync the service registry as it is 
standalone mode so we nee to give the local server address.
 
server.port : On which port server will be bound.
 
eureka.client.register-with-eureka
 
This determines is this server register itself as client as Eureka 
server is also act as client so that it  can sync the registry.
The value false means prevent itself act as a client.
 
eureka.client.fetch-registry  : Not register itself in service registry
 
Step-4
 
Eureka server setup is completed   now run this  application and  acees the Eureka dashboard on 
following url http://localhost:8761 it will display Eureka dashboard
 
 
Creating a Eureka client 
-----------------------------
 
Step-1 Add spring-cloud-starter-netflix-eureka-client  dependency  
 
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
 
Modify the POM.xml file with below code 
------------------------------------------
 
<dependencyManagement>
<dependencies>
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-dependencies</artifactId>
<version>2020.0.2</version>
<type>pom</type>
<scope>import</scope>
</dependency>
</dependencies>
</dependencyManagement>
 
Step-2
 
After this use @EnableEurekaClient annotation on top of the class  annotated with @SpringBootApllication .
The @EnableEurekaClient annotation makes your Spring Boot application act as a Eureka client.
@EnableEurekaClient annotation is optional if we have the spring-cloud-starter-netflix-eureka-client dependency on the classpath.
 
 
Step-3
 
After enabling the discovery client, add following configuration inside application.properties file
 
spring.application.name=client-service 
server.port=8100
 
eureka.client.service-url.default-zone=http://localhost:8761/eureka
 
Here we configured
Spring application name to uniquely identify our client in the list of registered applications 
the port of Eureka client and the URL of Eureka Service.
 
Step-4
 
Now start the Eureka client application it will register to the Eureka service