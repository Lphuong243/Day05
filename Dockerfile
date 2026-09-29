# Sử dụng Maven để build dự án thành file .war
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
# Copy file cấu hình Maven
COPY pom.xml .
# Copy thư mục source code
COPY src ./src
# Build dự án ra thư mục target
RUN mvn clean package -DskipTests

# Sử dụng Tomcat 10 để chạy file .war
FROM tomcat:10.1-jdk17
# Copy file .war vừa build sang thư mục webapps của Tomcat và đổi tên thành ROOT.war để dễ truy cập
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
# Mở cổng 8080
EXPOSE 8080
# Chạy Tomcat
CMD ["catalina.sh", "run"]