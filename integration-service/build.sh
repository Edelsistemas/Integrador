#!/bin/bash
clear

targetBranch="$1"
if [ $# -eq 0 ]; then
        echo "Branch Empty Default to: master"
        targetBranch="master"
fi

echo "Building Dev Environment"
cd /home/4points/docker/integration-service/sources/ede-integration-service
echo "Git Pull from: $targetBranch"
git reset --hard HEAD
git fetch --all
git reset --hard origin/$targetBranch
echo "Clean Instal Project"
mvn clean install -Dmaven.test.skip
cd /home/4points/docker/integration-service
echo "Generating Docker Image and Run"
docker-compose -p integration_service down
docker-compose -p integration_service up --build --remove-orphans -d
echo "Clean Dangling Images"
docker rmi $(docker images -f "dangling=true" -q)
