
## 通过docker运行mysql
```bash
docker run -d --name xianxia-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=xianxia_db mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

## 通过docker运行redis
```bash
docker run -d --name xianxia-redis -p 6379:6379 redis:7-alpine
```