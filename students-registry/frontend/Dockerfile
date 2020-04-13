
FROM mhart/alpine-node AS builder
WORKDIR /usr/src/app

COPY package.json package.json
RUN yarn install
COPY . .
RUN yarn run build

FROM mhart/alpine-node
RUN yarn global add serve
WORKDIR /app
COPY --from=builder /app/build .
CMD ["serve", "-p", "80", "-s", "."]
