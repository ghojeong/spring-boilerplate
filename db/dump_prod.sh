PEM_KEY=/Users/pyro/.ssh/keys/production-2023-10-04.pem
SERVER=ubuntu@batch.ghojeong.org

ssh -i $PEM_KEY $SERVER 'mysqldump --lock-tables=false --column-statistics=0 \
--no-create-info --complete-insert --skip-triggers \
-h $RDS $DB $PROTOCOL -u $ID -p$PW > dump.sql'
scp -i $PEM_KEY "$SERVER:/home/ubuntu/dump.sql" .
ssh -i $PEM_KEY $SERVER 'rm /home/ubuntu/dump.sql'
