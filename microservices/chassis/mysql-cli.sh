#! /bin/bash -e

docker run ${1:--it} \
   --name mysqlterm --network=${PWD##*/}_default --rm \
   mysql:8.0.27 \
   sh -c 'exec mysql -hmysql  -uroot -prootpassword -o service_template'
