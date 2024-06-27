RDS=localhost
DB=mariadb
PROTOCOL=' -P 12345 --protocol=tcp '
ID=root
PW=rootPassword

mysqldump --lock-tables=false --column-statistics=0 \
--no-create-info --complete-insert --skip-triggers \
-h $RDS $DB $PROTOCOL -u $ID -p$PW > dump.sql
