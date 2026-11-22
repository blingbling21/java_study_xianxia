
## 通过dockers运行mysql
```bash
docker run -d --name xianxia-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=xianxia_db mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
