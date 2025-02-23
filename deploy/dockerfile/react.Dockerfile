# 定义前端项目路径变量
ARG FRONTEND_PATH=deliver-front/react

# 定义外部 nginx 配置路径变量
ARG NGINX_CONF_PATH=deploy/nginx/nginx.conf

# 第一阶段：使用 Node.js 20 进行构建
FROM node:20-alpine as builder

# 设置工作目录
WORKDIR /react

# 复制项目的 package.json 和 package-lock.json
COPY ${FRONTEND_PATH}/package*.json ./

# 安装依赖
RUN npm install

# 复制整个前端项目
COPY ${FRONTEND_PATH} .

# 构建项目
RUN npm run build

# 第二阶段：使用 Nginx 进行部署
FROM nginx:stable-alpine

# 删除 Nginx 默认配置
RUN rm /etc/nginx/conf.d/default.conf

# 复制自定义的 Nginx 配置文件
COPY ${NGINX_CONF_PATH} /etc/nginx/conf.d/

# 从第一阶段复制构建好的文件到 Nginx 的静态文件目录
COPY --from=builder /react/dist /usr/share/nginx/html

# 暴露端口
EXPOSE 6060

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]