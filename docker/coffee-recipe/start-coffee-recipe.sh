#!/bin/sh
: "${AUTH_HOST:?Need to set AUTH_HOST non-empty}"
: "${COMA_DB_HOST:?Need to set COMA_DB_HOST non-empty}"

./wait-for-it.sh $AUTH_HOST:80 -t 0
./wait-for-it.sh $COMA_DB_HOST:3306 -t 0

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
