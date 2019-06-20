FROM alpine:3.10

RUN apk --update add openjdk8-jre &&\
	mkdir -p opt/jdk &&\
	ln -s /usr/lib/jvm/java-1.8-openjdk/bin /opt/jdk

RUN apk add --update curl &&\
	curl -LO https://www-us.apache.org/dist/tomcat/tomcat-8/v8.5.41/bin/apache-tomcat-8.5.41.tar.gz &&\
	tar xzvf apache-tomcat-8.5.41.tar.gz -C /opt/ &&\
	rm -f apache-tomcat-8.5.41.tar.gz &&\
	ln -s /opt/apache-tomcat-8.5.41/ /opt/tomcat &&\
	apk del curl &&\
	rm -rf /var/cache/apk/*

COPY target/vendas-facil.war /opt/tomcat/webapps/
CMD /opt/tomcat/bin/catalina.sh run
