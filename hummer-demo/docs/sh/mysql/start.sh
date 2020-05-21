docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=123456 -v /Users/Shared/mysql_home/lib:/var/lib/mysql  -d mysql
docker exec -it mysql bash
mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER;
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';
FLUSH PRIVILEGES;

docker docker cp mysql:/etc/mysql/my.cnf ./
vim ./my.cnf
default_authentication_plugin=mysql_native_password
docker cp ./my.cnf mysql:/etc/mysql/my.cnf

docker restart mysql