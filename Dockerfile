FROM maven:3-eclipse-temurin-17-alpine AS build
COPY . ./
RUN mvn clean package -B -DskipTests

FROM eclipse-temurin:17
COPY --from=build target/*.jar app.jar
COPY admin-setup.sh admin-setup.sh
ENTRYPOINT ["java", "-jar", "app.jar"]