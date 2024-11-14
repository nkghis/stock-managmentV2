FROM openjdk:8
EXPOSE 8089


ADD target/stock.jar stock.jar


ENTRYPOINT ["java", "-jar", "/stock.jar"]