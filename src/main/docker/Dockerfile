FROM openjdk:8-jre-alpine

WORKDIR /opt/app
ADD json-data-generator-1.4.2-SNAPSHOT-bin.tar ./
WORKDIR /opt/app/json-data-generator-1.4.2-SNAPSHOT
RUN mv json-data-generator-1.4.2-SNAPSHOT.jar app.jar

RUN touch conf/default_Simulator.json
RUN touch conf/default_Workflow.json


CMD ["java", "-jar", "app.jar", "default_Simulator.json"]
