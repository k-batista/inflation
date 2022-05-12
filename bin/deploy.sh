#!/bin/sh

docker build -t inflations . 
heroku container:login
heroku container:push web --app inflations
heroku container:release web --app inflations
