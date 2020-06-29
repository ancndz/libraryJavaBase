### BUILD image
FROM maven:3-jdk-8 as builder
# create app folder for sources
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
#Download all required dependencies into one layer
RUN mvn -B dependency:resolve dependency:resolve-plugins
#Copy source code
COPY src /build/src
# Build application
RUN mvn clean package

FROM java:8 as runtime
#Set app home folder
ENV APP_HOME /app
#Create base app folder
RUN mkdir $APP_HOME
#Create folder to save configuration files
RUN mkdir $APP_HOME/config
#Create folder with application logs
RUN mkdir $APP_HOME/log
VOLUME $APP_HOME/log
VOLUME $APP_HOME/config

WORKDIR $APP_HOME

MAINTAINER ancndz
EXPOSE 8080
COPY --from=builder ./bulid/target/libraryJavaBase.jar libraryJavaBase.jar

CMD ["java","-jar","libraryJavaBase.jar"]