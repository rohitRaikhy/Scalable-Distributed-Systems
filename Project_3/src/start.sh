#!/bin/bash

docker network create project_network

docker build -t server .
docker build -t client -f client.Dockerfile .
docker build -t coord -f coord.Dockerfile .

docker run -d -p 1001:1001 -ti --rm --name server_one server  1001 server_one 2000 host.docker.internal
docker run -d -p 1002:1002 -ti --rm --name server_two server  1002 server_two 2000 host.docker.internal
docker run -d -p 1003:1003 -ti --rm --name server_three server  1003 server_three 2000 host.docker.internal
docker run -d -p 1004:1004 -ti --rm --name server_four server  1004 server_four 2000 host.docker.internal
docker run -d -p 1005:1005 -ti --rm --name server_five server  1005 server_five 2000 host.docker.internal

docker run -d -p 2000:2000 -ti --rm coord 2000 1001,1002,1003,1004,1005
#docker run -d -p 3000:3000 -ti --rm client host.docker.internal 1001 server_one 2000
