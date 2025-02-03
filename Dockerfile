FROM openjdk:17-bullseye

ARG RUN_JAVA_VERSION
ENV LANG='en_US.UTF-8' \
    LANGUAGE='en_US:en'

RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p /app

RUN curl -L "https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh" \
      -o /app/run-java-sh && \
    chmod +x /app/run-java-sh

ENV JAVA_OPTIONS="-XX:-OmitStackTraceInFastThrow"

COPY build/libs/*.jar /app.jar

EXPOSE 8080

# Use the downloaded run-java-sh script as the entry point.
ENTRYPOINT ["/app/run-java-sh"]