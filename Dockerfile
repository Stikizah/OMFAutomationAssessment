FROM openjdk:8
COPY ./out/production/HelloWorld/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","HelloWorld"]