## 数字声像资料鉴别分析系统部署文档

### 后端部署

将backend-system-0.0.1-SNAPSHOT.jar与application.yml放置在同一文件夹

在application.yml文件中配置如下配置项：
![image-20230412201443129](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20230412201443129.png)

server.port：配置后端程序运行端口号

graduation.backend-system中：

- python：默认使用python环境变量。如python有多个版本，需要在此配置目标版本对应可执行文件绝对路径
- algorithm-file-base：配置算法文件夹基路径，内部文件夹根据121.46.19.2创建
- upload-base：配置文件上传地址，需要是绝对路径,内部文件夹根据121.46.19.2创建
- file-base-url：后端部署地址+端口号
- result-base-path：算法执行结果文件夹绝对路径，内部文件夹根据121.46.19.2创建

配置好所有配置项后，命令行运行java -jar -Dfile.encoding=UTF-8 backend-system-0.0.1-SNAPSHOT.jar即可运行后端程序



### 中间件与前端配置

需提前配置Nginx，并找到Nginx配置文件，位于下载目录/conf/nginx.conf中

更改以下配置：

![image-20230412202722532](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20230412202722532.png) 

改为后端服务器部署地址，权值根据后端服务器性能设置

![image-20230412202809249](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20230412202809249.png) 

其中server.root字段设置为前端项目静态页面目录，dist文件夹中包含前端项目生成的静态文件，部署前端时将整个dist文件夹使用ftp迁移即可，并在root字段配置其绝对路径