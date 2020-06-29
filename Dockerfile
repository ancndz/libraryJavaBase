MAINTAINER ancndz
EXPOSE 8080
COPY ./target/libraryJavaBase.jar libraryJavaBase.jar

CMD ["java","-jar","libraryJavaBase.jar"]