# Getting Started

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