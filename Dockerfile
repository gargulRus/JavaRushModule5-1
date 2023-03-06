FROM tomcat:9.0.64

LABEL maintainer="Nikolay Gabaraev"

ADD target/gabaraev-jr51.war /usr/local/tomcat/webapps/gabjr51.war

EXPOSE 8080

CMD ["catalina.sh", "run"]