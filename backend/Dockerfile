# Etapa 1: Compila a aplicação
FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Executa a aplicação
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .env
EXPOSE 8080
# Carrega vari�veis do .env antes de rodar o jar
ENTRYPOINT ["sh", "-c", ". .env && java -jar app.jar"]
