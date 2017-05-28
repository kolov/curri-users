FROM kolov/java8
VOLUME /tmp
ADD *.jar /tmp
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENV SERVER_PORT=9000
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]