"# java_test" 

App is constructed on the base of Spring boot.
Basic dependencies: 
spring-boot-starter-security, spring-boot-starter-web, spring-boot-starter-cache, spring-boot-starter-test, 

Another dependencies:
opencsv, log4j-api, log4j-core, springdoc-openapi-ui

Basic interfaces and classes: 
  API: RecommendationsController
  Business layer: RecommendationsService, RecommendationsServiceImpl, 
  Data layer: CryptorLoader, CSVCryptoLoader (Caching is enabled here - may cause appropriate behavior during debug.)
  
Swagger enabled, is under it's default path.
