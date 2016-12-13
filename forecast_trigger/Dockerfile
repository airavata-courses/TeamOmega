FROM maven:3.3.9-jdk-8-alpine

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY . /usr/src/app
RUN mvn install

CMD ["mvn", "exec:java", "-Dexec.mainClass=edu.sga.omega.forecast_trigger.Forecast"]
