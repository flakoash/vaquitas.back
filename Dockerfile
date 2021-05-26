FROM maven:3.6.1-jdk-8-slim
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY application.properties /workspace
RUN mvn -f pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["mvn","spring-boot:run"]