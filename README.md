# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

## Example for application.properties
```application.properties
# Spring Boot Actuator
management.endpoints.web.exposure.include=health,metrics,mappings,threads,heapdump,logfile
management.info.env.enabled=true

# Spring Boot actuator Admin credentials
spring.security.user.name=USER_NAME
spring.security.user.password=PASSWORD

# Postgres DB
spring.datasource.username=DATABASE_PASSWORD
spring.datasource.url=jdbc:postgresql://DATABSE_URL:5432/DATABASE_USER
spring.datasource.password=DATABASE_PASSWORD
# Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

#Logging
logging.level.org.springframework.web=ERROR

# Hikari Connection Pool
spring.datasource.hikari.maximum-pool-size=1
spring.datasource.hikari.minimum-idle=0
spring.datasource.hikari.connection-timeout=1800000

# User defined properties
seeding.count=20
```