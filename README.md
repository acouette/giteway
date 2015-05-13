#Giteway


###Deployment instructions

Requirement : 
* java >=7
* maven

**JETTY**

To start the web app with the jetty plugin, run the following command from the app root:

```sh
$ mvn jetty:run [options]

options:
-Dhttp.proxyHost=<arg>
-Dhttp.proxyPort=<arg>
```

**WAR**

To build the war, run the following command from the app root:
```sh
$ mvn package
```
