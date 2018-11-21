# AppMonProbitInputProcessor

This application can serve as a server accepting Dynatarce data as it is streamed through purelytics towards the Probit system.


### how to use

The project can be cloned into your private workspace, can be modified where needed to comply with internal policies
it can be build by maven like

```
mvn clean install
```

This will result in a file located in the target directory
target/dynatraceBulkProcessor-1.0.0.jar


No you are able to launch this jar, it will create a tomcat server accepting data on port 9090, and sending it out to the chosen endpoint. The streaming to probit starts after 'Delay' seconds to ensure actions are created for the ended visits.


```
java -jar target/dynatraceBulkProcessor-1.0.0.jar -DApplications="App1,App2" -DEndpoint="https://myTenant.probit.cloud/session/enqueue" -DUser=probitUser -DPassword=probitPassword -DDelay=120
```