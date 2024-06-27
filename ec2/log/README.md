# 정기적으로 api_log dump 를 떠서 S3 에 저장

EC2 에 `AWSEC2AccessS3` Role 을 지정한다.

```sh
sudo apt install -y awscli
```

### dump_log.sh

```sh
SQL_FILE="$(date +"%Y-%m-%d").sql"
mysqldump --lock-tables=false --column-statistics=0 \
--no-create-info --complete-insert --skip-triggers \
-h $ETC_RDS $DB api_log $PROTOCOL -u $ETC_ID -p$ETC_PW > $SQL_FILE && \
aws s3 cp $SQL_FILE s3://ghojeong-api-log && \
mysql -h $ETC_RDS $DB $PROTOCOL -u $ETC_ID -p$ETC_PW -e 'TRUNCATE api_log' && \
mysql -h $ETC_RDS $DB $PROTOCOL -u $ETC_ID -p$ETC_PW -e 'OPTIMIZE TABLE api_log' && \
rm $SQL_FILE
```

### crontab -e

```cron
30 9 * * TUE sh /home/ubuntu/dump_log.sh
```