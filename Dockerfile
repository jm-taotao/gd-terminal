FROM openjdk:8
WORKDIR /app
COPY ./*.jar /app/terminal.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","terminal.jar"]