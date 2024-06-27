DB_IMAGE=mariadb:1.0
DB_CONTAINER=`docker ps -aq --filter ancestor=$DB_IMAGE`

docker stop $DB_CONTAINER
docker rm $DB_CONTAINER
docker rmi $DB_IMAGE;

docker-compose up --build -d

sleep 5

cd ../db && sh initdb.sh
