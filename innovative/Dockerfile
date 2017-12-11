FROM tomcat

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY /var/lib/docker/volumes/3361dba3fc762415edc574b52251d719dfa68a6c63489b1cf11542e2d2149169/_data/workspace/maven/innovative/target/innovative-0.0.1-SNAPSHOT.jar innovative-0.0.1-SNAPSHOT.jar

EXPOSE 81
ENTRYPOINT ["java","-jar","innovative-0.0.1-SNAPSHOT.jar"]
