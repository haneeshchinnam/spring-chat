FROM eclipse-temurin:17-jdk-focal

WORKDIR /app


COPY . .

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/practice-0.0.1-SNAPSHOT.jar"]
