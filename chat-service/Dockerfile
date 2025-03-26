FROM openjdk:17-jdk-slim
WORKDIR /chat-service
COPY target/chat-service-*.jar /chat-service/chat-service.jar
ENTRYPOINT ["java", "-jar", "/chat-service/chat-service.jar"]

#docker build -t venkydasari/chat .
#docker push venkydasari/chat