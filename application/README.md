# About  
Application for searching algorithms like search, iterate, some logical tasks and so on. It provides extended information about each algorithm: **complexity**, **resources usage** and so on.  
Each authorized users to upload their own algorithms with related metadata and share theirs with other users.  
Has a simple **WEB interface** and endpoints for integrating with 3rd-party applications through the **REST** protocol.
# Technical details
All the components of the application are **Docker images** - computation nodes with an **OpenShift** as an orchestrator.

Using technology stack:
* Spring boot - for WEB UI
* Apache Kafka as a communication buffer between application and search engine
* Apache SOLR server as full-text engine
* Jupiter as a Unit Test framework

Additional technologies:
* SonarQube Docker - for code quality support

#Already implemented
* Spring core module: authorization/registration
* DB maintenance module: on top of Liquibase
* Simple WEB UI
* User roles support: ADMIN, USER with Spring Security mechanism