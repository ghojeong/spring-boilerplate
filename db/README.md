# MariaDB 의 세팅

## 다운로드


```sh
sudo dnf install mariadb105-server 
sudo systemctl enable mariadb
sudo systemctl restart mariadb
```

## 유저의 세팅

```sql
GRANT ALL ON *.* TO 'admin'@'%' IDENTIFIED BY 'adminPassword' WITH GRANT OPTION;
GRANT SELECT, INSERT, UPDATE, DELETE ON *.* TO 'manager'@'%' IDENTIFIED BY 'managerPassword' WITH GRANT OPTION;
GRANT SELECT ON *.* TO 'reader'@'%' IDENTIFIED BY 'readerPassword' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

## DB 세팅

```sql
SET GLOBAL character_set_client = 'utf8mb4';
SET GLOBAL character_set_connection = 'utf8mb4';
SET GLOBAL character_set_database = 'utf8mb4';
SET GLOBAL character_set_filesystem = 'utf8mb4';
SET GLOBAL character_set_results = 'utf8mb4';
SET GLOBAL character_set_server = 'utf8mb4';
SET GLOBAL collation_connection = 'utf8mb4_unicode_ci';
SET GLOBAL collation_server = 'utf8mb4_unicode_ci';
SET GLOBAL long_query_time = 1;
SET GLOBAL slow_query_log = 1;
SET GLOBAL time_zone = 'Asia/Seoul';
SET GLOBAL max_connections = 12000;
SET GLOBAL max_connect_errors = 4000;
```

## 원격 접속

`/etc/mysql/mariadb.conf.d/50-server.cnf` 에 다음 내용 수정

```cnf
[mysqld]
bind-address = 0.0.0.0
```

```sh
sudo systemctl restart mariadb
```

```mysq
mysql -h IP_주소 -P 3306 --protocol=tcp -u reader -p
```

## Reference

- https://www.digitalocean.com/community/tutorials/how-to-install-mariadb-on-ubuntu-20-04
