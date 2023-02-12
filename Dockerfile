FROM openjdk:17-jdk-alpine

COPY target/steam-price-history-0.0.1-SNAPSHOT.jar /opt/lib/

ENTRYPOINT ["java"]
CMD ["-Dspring.profiles.active=docker","-jar","/opt/lib/steam-price-history-0.0.1-SNAPSHOT.jar"]
