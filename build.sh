#!/usr/bin/env bash
echo "开始进行maven构建"
mvn clean package -P prod
echo "maven构建完成，判断docker镜像是否存在"
#获取镜像信息
imagesId=`docker images|grep -i loozb-web|awk '{print $3}'`

#判断镜像是否存在，如果存在强行删除
if ! -n "$imagesId";then
  echo "当前镜像不存在"
else
  docker rmi $imagesId -f
fi

#进入目录
cd loozb-api

#构建镜像
docker build -t loozb-api .

#判断容器是否存在，如果存在删除
if docker ps -a|grep -i loozb-web;then
   docker rm -f loozb-web
fi

#启动容器
docker run -p 8088:8080 --name loozb-web -v /home/config/loozb:/home/config/loozb -d loozb-web