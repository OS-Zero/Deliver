<div align="center">

<img alt="logo" src="image/logo.png" width="168" height="150">

<h1 style="margin: 30px 0 30px; font-weight: bold;">Deliver</h1>

<h4 align="center">一个轻量级企业消息推送平台，旨在提供企业内部沟通和协作的便捷解决方案！</h4>

<a href="https://oszero.cn" target="_blank">官方网站</a> |
<a href="https://gitee.com/OS-Zero/deliver/issues" target="_blank">需求收集</a> |
<a href="https://gitee.com/OS-Zero/deliver/issues" target="_blank">问题反馈</a>

<a href='https://gitee.com/OS-Zero/deliver/stargazers'><img src='https://gitee.com/OS-Zero/deliver/badge/star.svg?theme=dark' alt='star'></img></a>
<a href='https://gitee.com/OS-Zero/deliver/members'><img src='https://gitee.com/OS-Zero/deliver/badge/fork.svg?theme=dark' alt='fork'></img></a>
</div>

--------------------------

## 项目介绍

**Deliver**
消息推送平台，企业内部的消息推送系统，旨在提供企业内部沟通和协作的便捷解决方案。它以轻量级部署、简单易用、支持多种通信渠道为特点，为企业提供高效的消息传递和通知功能。我们相信高效的内部沟通对企业的成功至关重要，Deliver
将成为您实现这一目标的得力助手。无论您需要电话通知、短信提醒、邮件通告，还是钉钉、企业微信和飞书的消息推送，Deliver 都能满足您的需求。

## 系统架构
![](image/architecture_diagram.png)

## 消息发送时序图
<img alt="消息时序图-生产者" src="image/消息时序图-生产者.png">
<img alt="消息时序图-消费者" src="image/消息时序图-消费者.png">

## 核心特性

- 支持对接了阿里云、腾讯云的电话以及短信消息推送功能；
- 支持邮件消息推送功能；
- 支持钉钉文本、图片、语音、文件、链接、OA、markdown、卡片、机器人等消息类型推送；
- 支持企业微信文本、图片、语音、视频、文件、文本卡片、图文、markdown 等消息类型推送；
- 支持飞书文本、富文本、图片、消息卡片、分享群名片、语音、视频、文件等消息类型推送；
- 支持钉钉、企业微信、飞书，消息文件上传功能，统一进行文件管理，便于发送多媒体消息；
- 支持圈定人群的定时单次、定时循环消息推送；
- 轻量级部署，仅依赖 MySQL、MQ、Redis（最轻量级只需依赖 MySQL、Redis）；
- 支持多种 MQ，包括 RabbitMQ、RocketMQ、Redis Stream、Disruptor 等，后续支持 Apache Pulsar、Kafka 等；
- 管理端前端支持 Vue3、React 两种版本可供选择，UI 设计采用 Ant Design，界面美观简洁；
- 更多功能正在赶来的路上🎉🎉🎉。

## 在线演示
- Vue端：http://vue-deliver.oszero.cn:6060/ 账号：oszero@qq.com 密码：oszero
- React端：http://react-deliver.oszero.cn:7070/ 账号：oszero@qq.com 密码：oszero

## 技术栈
### 后端技术选型
Spring Boot、Mybatis-Plus、MySQL、Redis、RocketMQ、RabbitMQ、Disruptor、Quartz
###  前端技术选型
- Vue项目：Vue3 + vite + Vue-router + pinia + ts + Ant-design-vue + axios + mockjs
- React项目：React18 + vite + React-router + ts + Ant-design + axios + mockjs + css-module

## 接入登记
如果您和您的公司或组织使用了 Deliver，非常感谢您的支持与信任，请 [在此](https://gitee.com/OS-Zero/deliver/issues/I8IPY3) 进行接入登记，您的回复将成为维护者、社区用户和观望者的信心来源。感谢支持 💖
> 登记信息仅用于推广本产品～