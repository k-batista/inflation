FROM clojure:lein-2.9.8-alpine
COPY . /usr/src/app
WORKDIR /usr/src/app

RUN lein deps

EXPOSE $PORT

CMD ["lein", "run", "server"]