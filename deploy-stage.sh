SERVER=ec2-user@apistage.ghojeong.net
KEY=stage-2023-09-26.pem
ENV=stage

sh ./deploy-blue-green.sh $SERVER $KEY $ENV
