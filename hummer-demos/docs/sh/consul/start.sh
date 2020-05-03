#将容器8500端口映射到主机8500端口，同时开启管理界面
docker run -d --name=consul1 -p 8500:8500 consul agent  --server --bind=0.0.0.0  --client=0.0.0.0 -ui -bootstrap-expect=1
