FROM bellsoft/liberica-openjdk-alpine-musl:17

WORKDIR /app

COPY target/perudoBot.jar /app/java-app.jar

ENV SERVER_PORT=8080

ENTRYPOINT ["sh", "-c", "java -jar /app/java-app.jar --server.port=${SERVER_PORT}"]
