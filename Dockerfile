# Sử dụng Maven để build dự án thành file .war
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Sử dụng Tomcat 10 để chạy file .war
FROM tomcat:10.1-jdk17
ENV RENDER=true
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]