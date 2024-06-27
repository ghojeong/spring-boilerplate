# systemctl

## Timezone 설정

```sh
sudo timedatectl set-timezone Asia/Seoul
```

## Ubuntu 에 설치할 패키지

```sh
sudo apt update && sudo apt upgrade -y && sudo apt autoremove -y && sudo apt clean
sudo apt install -y openjdk-21-jdk nginx
sudo chmod 757 -R /etc/nginx/sites-available
# 포트 변경을 통한 무중단 배포를 위해 chmod 필요
```

## Amazon Linux 에 설치할 패키지

```sh
sudo dnf update && sudo dnf upgrade -y && sudo dnf autoremove -y && sudo dnf clean all
sudo dnf install -y java-21-amazon-corretto-headless nginx mariadb105

# ec2/nginx/prod/8080.conf 에 내용을 복붙해서 옮기기
sudo vim /etc/nginx/conf.d/sites-available.conf

# 포트 변경을 통한 무중단 배포를 위해 chmod 필요
sudo chmod 757 -R /etc/nginx/conf.d

sudo systemctl enable nginx
sudo systemctl restart nginx

# SSH 설정
sudo dnf install -y certbot-nginx
sudo certbot --nginx -d apistage.ghojeong.net
sudo certbot --nginx -d apidev.ghojeong.net
```

## EC2 에 Service 로 등록

- `/etc/systemd/system` 에 systemd/app.service 파일을 등록
- `/etc/systemd/system` 에 systemd/blue-green.service 파일을 등록
- `/etc/systemd/system/app.service.d` 의 systemd/env.conf 에 환경변수 등록
- `/etc/systemd/system/blue-green.service.d` 의 systemd/env.conf 에 환경변수 등록

```sh
# 서비스를 등록
sudo systemctl daemon-reload

# 부팅시 서비스가 재시작 되도록 함 (disable 시 켜지지 않음)
sudo systemctl enable app

# app 을 시작
sudo systemctl restart app
```

## app service 의 로그 보기

기본적으로 로그는 `/home/ubuntu/logs/` 안에 저장되어 있다.

```sh
journalctl -u app --since "2023-07-04 00:25:51" --until "2023-07-06 01:28:54"
journalctl -u app -f
systemctl -l status app
```

## Swap 메모리

```sh
# 스왑 파일 만들기
sudo dd if=/dev/zero of=/swapfile bs=1M count=8000

# 스왑 파일에 권한 설정
sudo chmod 600 /swapfile

# 스왑 설정
sudo mkswap /swapfile && sudo swapon /swapfile

# 스왑 재부팅 설정
sudo vi /etc/fstab
# /swapfile swap swap auto 0 0

# 스왑 확인
free -h
```

## 디스크 용량 확보 정리

```sh
# 현재 폴더에서 파일 용량이 큰 순서대로 출력
sudo du -ah --max-depth=1 | sort -hr | head

sudo chmod 660 /var/log/syslog.1

sudo crontab -e
```

```cron
0 18 * * 5 rm /home/ubuntu/logs/archive/*.gz
0 18 * * 5 chmod 660 /var/log/syslog.1 && cat /dev/null > /var/log/syslog.1
0 18 * * 5 journalctl --vacuum-size=100M
```

## Blue-Green 무중단 배포 전략

- 8080 포트를 쓰는 app 과 8081 포트를 쓰는 blue-green 를 systemctl로 2개를 동시에 돌린다.
- Nginx 의 Redirect 를 8081 로 바꾼다. (미리 준비된 설정파일을 덮어쓰고 Nginx 를 reload 한다.)
- 8080 포트를 쓰는 app 을 restart 한다.
- 30초 sleep 한다.
- Nginx 의 Redirect 를 8080 로 바꾼다.
- 8081 포트를 쓰는 blue-green 를 restart 한다.

## 환경변수 등록

`/etc/environment` 에 환경변수 등록

```conf
SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/mariadb
SPRING_DATASOURCE_USERNAME=manager
SPRING_DATASOURCE_PASSWORD=managerPassword
```
