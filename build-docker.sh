./gradlew build && \
docker build -t ghojeong/was . && \
sudo docker run -d --name was -p 8080:8080 ghojeong/was
