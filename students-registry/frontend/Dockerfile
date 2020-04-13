
FROM node:latest AS builder

ENV CONTAINER_PATH /usr/src/app

WORKDIR $CONTAINER_PATH

COPY package.json /usr/src/app/package.json

RUN npm install

COPY . /usr/src/app/

EXPOSE 80 443 3005 5000

ENTRYPOINT [ "yarn","start" ]
