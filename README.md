# UserManagement
User management REST application (sandbox app)

---------------------------------------

## How to...
### ...build and start
build: gradle build
test:
run: java -jar build/libs/um-rest-service-0.1.0.jar

---------------------------------------

## Choosed solution and frameworks
##### REST, IoC and utility - I choose Spring boot based on following points:
 *   Low support cost (it's mainstream framework, easy to find new developer familiar with it, a lot of documentation and use cases)
 *   Stable, no license fee (mainstream and open-source)
 *   Quick project launch and it's possible to scale to huge project without framework change
 
##### Why I use REST parameters mapping with @RequestParam annotation and do not pass JSON as input:
 * To have more control on the input

---------------------------------------

## Open questions
- Do we need to store "duplicates" accounts? If not what is the criteria for account duplication?

---------------------------------------

## Known issues (for testing purposes)
- Basic authentication vulnerable for attacks like Cross Site Request Forgery and Main in the Middle. For production application basic authentication need to be changed for separate loging procedure.
- User without "administrator" can successfully call administrator access level services
