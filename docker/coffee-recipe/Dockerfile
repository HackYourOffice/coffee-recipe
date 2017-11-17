FROM frolvlad/alpine-oraclejdk8:slim

EXPOSE 8080

VOLUME /tmp

RUN apk add --update bash && rm -rf /var/cache/apk/*

ADD start-coffee-recipe.sh start-coffee-recipe.sh
RUN chmod +x start-coffee-recipe.sh

ADD /maven/${docker.finalName}.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "./start-coffee-recipe.sh" ]
