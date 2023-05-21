FROM adoptopenjdk/openjdk11
COPY  target/micro-streaming-analytics.jar streaming-analytics.jar
ENTRYPOINT ["java", "-jar", "streaming-analytics.jar"]