# tourism_eyes_springboot 
旅游之眼微信小程序后端支持
# 架构
Springboot + Shiro + Redis + MongoDB + RabbitMQ
Shiro 权限认证
Mongo 系统数据库，储存各类信息
Redis 缓存数据库，用于登陆Token等经常访问数据缓存
RabbitMQ 消息队列，用于上传图片任务队列
