#!/bin/bash

docker network create project_four_network

docker build -t server .
docker build -t client -f Client.Dockerfile .

docker run -d -p 3001:3001 -ti --rm --name s1 server  3001 s1 host.docker.internal 196 3001,3002,3003 S1,s2,s3
docker run -d -p 3002:3002 -ti --rm --name s2 server  3002 s2 host.docker.internal 197 3001,3002,3003 S1,s2,s3
docker run -d -p 3003:3003 -ti --rm --name s3 server  3003 s3 host.docker.internal 198 3001,3002,3003 S1,s2,s3
#docker run -d -p 3004:3004 -ti --rm --name s4 server  3004 s4 host.docker.internal 199 3001,3002,3003,3004,3005 S1,s2,s3
#docker run -d -p 3005:3005 -ti --rm --name s5 server  3005 s5 host.docker.internal 200 3001,3002,3003 S1,s2,s3

#docker run -d -p 2000:2000 -ti --rm coord 2000 1001,1002,1003,1004,1005
#docker run -d -p 3000:3000 -ti --rm client host.docker.internal 1001 server_one 2000

#docker run -p 3000:3000 -ti --rm client host.docker.internal 3001 s1
