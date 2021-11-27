# API Key Proxy

Spring Boot application that acts as a proxy between the Google Maps API and any client application. 

Even though this application only proxies requests to Google Maps API specifically, it can be used with virtually any API. You just need to adapt it ;-)

I created this project for my blog [nice-code.dev](https://nice-code.dev)

Check out this blog post for more information: [Building an API Key Proxy - Part 1](https://nice-code.dev/app-development/building-an-api-key-proxy-part-1/)

## Run it local
To run the application local open a terminal, cd into the root directory of the project and execute the following commands:

    ./mvnw spring-boot:run -e -Dspring.profiles.active=local

## Run it in a production environment
At first, you have to adapt the properties <em>proxy.appId</em> and <em>proxy.secrets.projectId</em> in <em>application.yaml</em> according to your needs.

I set the default profile on AppEngine to **prod**.

If you want to change the active Spring Boot profile, you can do that so in <em>app.yaml</em>.