FROM tomcat

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY target/innovative-0.0.1-SNAPSHOT.jar innovative-0.0.1-SNAPSHOT.jar

EXPOSE 81
ENTRYPOINT ["java","-jar","innovative-0.0.1-SNAPSHOT.jar"]
