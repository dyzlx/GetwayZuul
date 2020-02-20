FROM java:8
MAINTAINER duyunzelx@outlook.com
ADD getway-zuul-0.0.1.jar /app.jar
EXPOSE 8081
ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]