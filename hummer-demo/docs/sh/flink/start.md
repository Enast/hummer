###相关端口
The Web Client is on port 8081
JobManager RPC port 6123
TaskManagers RPC port 6122
TaskManagers Data port 6121

###docker-compose.yml
```shell script
vim docker-compose.yml
```

```yaml
version: "2.1"
services:
  jobmanager:
    image: flink
    expose:
      - "6123"
    ports:
      - "8081:8081"
    command: jobmanager
    environment:
      - JOB_MANAGER_RPC_ADDRESS=jobmanager
  taskmanager:
    image: flink
    expose:
      - "6121"
      - "6122"
    depends_on:
      - jobmanager
    command: taskmanager
    links:
      - "jobmanager:jobmanager"
    environment:
      - JOB_MANAGER_RPC_ADDRESS=jobmanager
```
###生成启动
```shell script
docker-compose build
docker-compose up -d --force-recreate
docker-compose down
docker-compose restart
```

