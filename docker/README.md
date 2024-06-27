# 도커 관련 설명

## 스크립트 설명

```sh
# 도커 컨테이너를 실행한다.
sh ./docker-start.sh

# DB 의 스키마를 정의하고 더미 데이터를 넣는다.
sh ./initdb.sh

# 모든 도커 컨테이너와 이미지를 삭제한다.
sh ./docker-clear.sh
```

|||
|--|--|
|사용자이름|manager|
|비밀번호|managerPassword|
|데이터베이스|mariadb|

## MariaDB 도커 실행법

```sh
docker build --tag mariadb:1.0 .

docker run -d -p 12345:3306 --name=mariadb mariadb:1.0

mysql -h localhost -P 12345 -D mariadb --protocol=tcp -u manager -pmanagerPassword
```
