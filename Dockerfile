FROM amazonlinux:latest
RUN yum update -y && yum install -y java-17-amazon-corretto net-tools procps curl --allowerasing
RUN curl -sSf https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh -o /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY build/libs/*.jar spring-base.jar
EXPOSE 8081
ENTRYPOINT ["./wait-for-it.sh", "redis:6379", "--", "java", "-jar", "/spring-base.jar"]
