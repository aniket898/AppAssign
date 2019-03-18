# ComponentService

Service for creating and storing components and their relations

## Installation

Use mvn to compile jar

```bash
cd componentservice/
mvn clean install
cd target/
```

## Usage
1. Please note you have to be running a MySQL local instance on port 3306 before running the jar below
2. Create the schemas and table as given in creationscripts.sql in the folder
3. Run the jar using below command. By default the component service will start on port 8080
```java
java -jar ComponentService-0.0.1-SNAPSHOT.jar
```
## API And Model Information
Please find the API and model information either using SwaggerUI mentioned below or the document attached.

## Swagger UI
Please refer to the swagger UI for looking up API and Model information on [default-swaggerui-port-8080](http://localhost:8080/componentservice/swagger-ui.html)
 
OR Modify the link : http://localhost:8080/componentservice/swagger-ui.html

## Create a sample workspace to start with
1. Workspace Creation API
```
POST /componentservice/v1/workspaces/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: application/json
Cache-Control: no-cache
Postman-Token: 894e5558-3d51-fd5d-e360-35e273d97262

{
    "workspaceName": "Workspace1",
    "ownerGroupId": "Ownergroup1",
    "environments": [
        {
            "environmentName": "Environment1",
            "databases": [
                {
                    "databaseName": "Database1"
                }
            ]
        },
        {
            
            "environmentName": "Environment2",
            "databases": [
                {
                    "databaseName": "Database2"
                }
            ]
        }
    ],
    "sourceRepositories": [
        {
            "repositoryName": "SourceRepo1"
        }
    ]
}
```

Now you can use any API's to link and unlink parent and child components as specified in APIInformation.pdf