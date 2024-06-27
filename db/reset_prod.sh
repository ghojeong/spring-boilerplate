# SERVER_PATH=ubuntu@api.ghojeong.com
# KEY_PATH=/Users/pyro/.ssh/keys/production-2023-10-04.pem

# RESET_FILE=sql/reset_db.sql
# SCHEMA_FILE=../src/main/resources/schema.sql
# SQL_FILE=dump.sql

read -p "Are you sure? " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1
fi

# ssh -i $KEY_PATH $SERVER_PATH 'mysql -h $RDS $PROTOCOL -u $ID -p$PW' < $RESET_FILE && \
# ssh -i $KEY_PATH $SERVER_PATH 'mysql -h $RDS $DB $PROTOCOL -u $ID -p$PW' < $SCHEMA_FILE && \
# ssh -i $KEY_PATH $SERVER_PATH 'mysql -h $RDS $DB $PROTOCOL -u $ID -p$PW' < $SQL_FILE
