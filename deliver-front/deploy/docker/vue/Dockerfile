# 使用基于Nginx的官方镜像作为基础
FROM nginx

# 删除默认的Nginx配置文件
RUN rm /etc/nginx/conf.d/default.conf

# 复制自定义的Nginx配置文件到容器中
COPY nginx.conf /etc/nginx/conf.d/

# 复制前端打包生成的dist文件夹到容器中
COPY ../../../vue/dist/ /usr/share/nginx/html

# 暴露容器的80端口（Nginx默认端口）
EXPOSE 80

# 容器启动时执行的命令
CMD ["nginx", "-g", "daemon off;"]
