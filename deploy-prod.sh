SERVER='ec2-user@52.24.113.204'
KEY=production-2023-10-04.pem
ENV=prod

read -p "Are you sure? " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1
fi

sh ./deploy-blue-green.sh $SERVER $KEY $ENV
