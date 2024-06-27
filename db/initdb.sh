DB_URL=localhost
DB_PORT=12345
DB_USER=root
DB_PASSWORD=rootPassword
DB_NAME=mariadb

mysql --protocol=tcp -h $DB_URL -P $DB_PORT -u $DB_USER -p$DB_PASSWORD $DB_NAME < ../src/main/resources/schema.sql && \
mysql --protocol=tcp -h $DB_URL -P $DB_PORT -u $DB_USER -p$DB_PASSWORD $DB_NAME < ./sql/nickname.sql && \
mysql --protocol=tcp -h $DB_URL -P $DB_PORT -u $DB_USER -p$DB_PASSWORD $DB_NAME < ./sql/quote.sql && \
mysql --protocol=tcp -h $DB_URL -P $DB_PORT -u $DB_USER -p$DB_PASSWORD $DB_NAME < ./sql/discover.sql && \
mysql --protocol=tcp -h $DB_URL -P $DB_PORT -u $DB_USER -p$DB_PASSWORD $DB_NAME < ../src/main/resources/data.sql
