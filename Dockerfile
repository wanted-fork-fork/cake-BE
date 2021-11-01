FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/cake-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY keystore.p12 keystore.p12
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]