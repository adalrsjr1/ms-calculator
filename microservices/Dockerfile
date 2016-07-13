FROM java:alpine

RUN mkdir app
COPY jars app/

WORKDIR app/

ENTRYPOINT operation-v1.sh $PORT $OPERATION
