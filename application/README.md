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

#Dockerization
* `docker build -t application/web-ui .` - build Docker image
* `docker run -d -v "$APPLICATION_HOME:/opt/application" --name web-ui -p 8080:8080 application/web-ui` - build and run docker container. Now you can see this WEB UI into your browser by http://localhost:8080. Please **pay attention** that **APPLICATION_HOME** should be defined in advance!
* `docker stop web-ui` - stop a container
* `docker start web-ui` - start a container