SERVER_PATH=$1
KEY_PATH=/Users/pyro/.ssh/keys/$2
LOCAL_NGINX=./ec2/nginx/$3
NGINX_CONF=/etc/nginx/conf.d/sites-available.conf

# jar 파일을 빌드 후 서버로 전송
# ./gradlew clean build && \
scp -i $KEY_PATH ./build/libs/application.jar $SERVER_PATH:~  && \

# 8081 포트 앱을 시작
ssh -i $KEY_PATH $SERVER_PATH 'mkdir -p /home/ec2-user/blue-green' && \
ssh -i $KEY_PATH $SERVER_PATH 'sudo systemctl restart blue-green' && \
echo "Restart Initiated: $(date)" && sleep 20 && \

# 8081 포트 앱으로 nginx 를 연결 후, 8080 포트 앱을 재시작
scp -i $KEY_PATH $LOCAL_NGINX/8081.conf $SERVER_PATH:$NGINX_CONF  && \
ssh -i $KEY_PATH $SERVER_PATH 'sudo systemctl reload nginx' && \
ssh -i $KEY_PATH $SERVER_PATH 'sudo systemctl restart app' && \
sleep 20 && echo "Restart Finished: $(date)" && \

# 8080 포트 앱으로 nginx 를 연결 후, 8081 포트 앱을 종료
scp -i $KEY_PATH $LOCAL_NGINX/8080.conf $SERVER_PATH:$NGINX_CONF  && \
ssh -i $KEY_PATH $SERVER_PATH 'sudo systemctl reload nginx' && \
ssh -i $KEY_PATH $SERVER_PATH 'sudo systemctl stop blue-green'
