# Estágio 1: Build da Aplicação com Maven
# Usamos uma imagem que já vem com Maven e JDK 21 (a mesma versão do seu pom.xml)
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de camadas do Docker.
# Se as dependências não mudarem, o Docker não vai baixá-las de novo.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código fonte
COPY src ./src

# Executa o build do Maven para gerar o arquivo .jar
# -DskipTests pula a execução dos testes para acelerar o build
RUN mvn package -DskipTests


# Estágio 2: Imagem Final de Execução
# Usamos uma imagem JRE (Java Runtime Environment) que é muito menor que a JDK completa
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação quando o container for executado
ENTRYPOINT ["java", "-jar", "app.jar"]