RDS=$1
PROTOCOL='-P 3306 --protocol=tcp'
DB=mariadb
ID=admin
PW=adminPassword

RESET_FILE=sql/reset_db.sql
SCHEMA_FILE=../src/main/resources/schema.sql
SQL_FILE=dump.sql

mysql -h $RDS $PROTOCOL -u $ID -p$PW < $RESET_FILE && \
mysql -h $RDS $DB $PROTOCOL -u $ID -p$PW < $SCHEMA_FILE && \
mysql -h $RDS $DB $PROTOCOL -u $ID -p$PW < $SQL_FILE
