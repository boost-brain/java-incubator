
FROM node:latest AS builder
ENV CONTAINER_PATH /usr/src/app

WORKDIR $CONTAINER_PATH

COPY package.json /usr/src/app/package.json

COPY package.json package.json
RUN yarn install
COPY . /usr/src/app/
RUN yarn run build

FROM node:latest
RUN yarn global add serve
WORKDIR /app
COPY --from=builder /app/build .
CMD ["serve", "-p", "80", "-s", "."]
