#!/bin/bash
clear
cd /home/4points/docker/mongodb/
docker-compose down
docker-compose up --build -d
