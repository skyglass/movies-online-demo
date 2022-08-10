FROM adoptopenjdk:11-jre-hotspot as builder
ARG JAR_FILE=build/libs/service-template-main-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# TODO RUN true - intermittent https://github.com/moby/moby/issues/37965

FROM adoptopenjdk:11-jre-hotspot

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

RUN addgroup springboot && adduser --system --ingroup springboot springboot
USER springboot

HEALTHCHECK --start-period=30s --interval=5s CMD curl -f http://localhost:8080/actuator/health || exit 1

COPY --from=builder dependencies/ ./
RUN true
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
RUN true
COPY --from=builder application/ ./
