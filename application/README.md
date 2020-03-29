# About  
Web module, which includes all required code and configurations according to the Web UI.

# Used Tools & Technologies
  * Spring boot
  * Spring Security
  * Thymeleaf as HTML template engine
  * Liquibase - DB management
  * Tomcat - web container
  * Junit Jupiter - Unit tests engine
  * Docker - it also contains Dockerfile - so you can launch this module as a Docker image.

# Build process
* `mvn clean package spring-boot:repackage` - create executable jar file
* `java -jar target/application.jar` - run **fat** jar 