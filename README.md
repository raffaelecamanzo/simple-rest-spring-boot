Simple Spring Boot application
=============

This super-quick-and-dirty Spring Boot is a demo app of the poc-swagger-plugin capabilities.


Usage
-----

In order to try this out:
 
 - Clone the poc-swagger-plugin repo and install the library in your .m2 (mvn clean install)
 - Clone the definition-box-server and launch the HTTP server (follow instructions in the README.md of the project)

The operations listed above allow you to generate source files (interfaces and model) and package them in the Spring 
Boot app starting from the Swagger definition files (OpenAPI Spec 2.0) hosted by the HTTP server. 

Don't modify the generated source files! They will be re-generated immediately before the Maven compile task runs. 
If you want to insert/modify/delete paths or methods the right way is modify the YAML definition files. 

When you run the Spring Boot app in your local environment you can view paths and resources available in the Swagger UI 
(through SpringFox) at this URL: 
 
        http://localhost:8080/my-service/v1/swagger-ui.html