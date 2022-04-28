FROM openjdk:11
ADD jm-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8091 5433
ENTRYPOINT ["java", "-jar", "-DDB_HOST=pg_db", "-DDB_PORT=5432", "-DDB_NAME=MyBase", "-DDB_USERNAME=postgres", "-DDB_PASSWORD=1q", "-Dspring.profiles.active=dev", "backend.jar"]