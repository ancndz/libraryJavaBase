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
COPY ./target/libraryJavaBase.jar libraryJavaBase.jar

CMD ["java","-jar","libraryJavaBase.jar"]