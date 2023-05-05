# Service-Name APIs
## Prerequisites
 In order to build the project, you will have to install the following:

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven](https://maven.apache.org/download.cgi
* IDE With [lombok enable](https://github.com/spring-boot-template/wiki/FakeJWT-locally/IDE-Setting)
* [Familiar with git command](https://git-school.github.io/visualizing-git/)
* [Familiar with maven command](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
## Need to read before start


## Prepare
* [Maven setting](https://github.com/spring-boot-template/wiki/maven-setup) in ~/.m2/setting.xml or C:\Users\<LoginUser>\.m2\setting.xml
* [Jenkins setting](https://github.com/101digital/spring-boot-template/wiki/Jenkin-setup)

## Local config
This configuration use for local development only.[SHOUD NOT] commit to git using [.gitignore](https://git-scm.com/docs/gitignore)

* Spring boot config ./config/application.yml
```yaml
server.port: 8074
security.jwt.enable: false
logging.level:
  io.marketplace: TRACE
```
[more..](https://github.com/101digital/spring-boot-template/wiki)

* Fake JWT for local environment  ./src/main/java/io/marketplace/services/service_name/config/LocalAutoConfiguration.java [LocalAutoConfiguration.java](https://github.com/101digital/spring-boot-template/wiki/FakeJWT-locally)

## Run application locally
```bash
mvn spring-boot:run
```
Verify following url:
* http://localhost:8074/swagger-ui.htm
* http://localhost:8074/actuator
* http://localhost:8074/actuator/prometheus

## Update Service Name following 101 naming convention
<i>Replace all service_name to *realname* </i>
* [change api-docs setting ](https://github.com/101digital/spring-boot-template/wiki/Api-Doc-Setting)


## Do deployment
Notes: In 101digital sanbox#eks. service port is 8080 and management port is 8081
```bash
git checkout -b develop
git push origin develop
```
* Verify using 101digital Jenkins https://jenkins.io/
* Verify api doc portal https://.oneapi.world/apidocs/

## Start implement spring-boot rest api with API-FIRST approarch
* Change API spec file: src/main/resource/META-INF/api.yml
* Run spring boot again with maven `maven spring-boot:run`
* Execute api using swagger-ui: http://localhost:8074/swagger-ui.htm
* Implement api delegate: src/main/java/io/marketplace/services/service_name/api/delegate/
* Implement service layer: src/main/java/io/marketplace/services/service_name/services/

Notes: Move all business to service layer and keep in mind that api/delegate cani be changed anytime
### Changlenge :
* Reusable Data Object
* Java type mapping: `integer -> Long` `number --> BigDecimal`
 
[Read more about API-FIRST](https://github.com/spring-boot-template/wiki) <br/>
https://reflectoring.io/spring-boot-openapi/

## Mandatory for 101 developer
### Using 101 logging framework
** Important**:
Using
```java
import io.marketplace.commons.logging.Logger;
import io.marketplace.commons.logging.LoggerFactory;
```
instead of other logging framework.
```java
//-- SLf4j
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//---Log4J ---------
import org.apache.log4j.Logger;
//--------------

//-- Apache commong loging
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;