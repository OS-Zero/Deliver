import React from 'react';
import { Row, Col, Card, Avatar } from 'antd';
import styles from './index.module.scss';

interface Card {
  title: string;
  desc: string;
  link: string;
}

const Welcome: React.FC = () => {
  const cards: Card[] = [
    {
      title: '社区官网',
      desc: '欢迎访问 OSZero 官方网站，Open Source Zero由一群充满技术热情的开发者创建。加入我们，一同探索开源世界！',
      link: 'https://oszero.cn'
    },
    {
      title: '了解Deliver',
      desc: '快速了解Deliver企业消息推送平台，了解我们的产品特点和功能',
      link: 'https://oszero.cn'
    },
    {
      title: '快速开始',
      desc: '立即启动 Deliver 企业消息推送平台，发送属于您的首条消息~',
      link: 'https://oszero.cn'
    },
    {
      title: '用户指南',
      desc: '在这里，您将学会如何创建消息模板、配置必要信息以及发送各种类型的消息。现在就开始吧~',
      link: 'https://oszero.cn'
    },
    {
      title: '运维部署',
      desc: '支持多种部署方式（本地、Docker、Linux Shell），简单快捷的部署方式，让我们开始搭建吧！',
      link: 'https://oszero.cn'
    },
    {
      title: '交流讨论',
      desc: '欢迎加入我们的社区官方交流讨论群，快速获得答疑解惑，赶紧一起加入吧~',
      link: 'https://oszero.cn'
    }
  ];

  return (
    <div className={styles['welcomeContainer']}>
      <h1>欢迎使用 Deliver 企业消息推送平台</h1>
      <p>
        Deliver
        是一个面向企业的全面消息推送平台，旨在提供企业内部沟通和协作的便捷解决方案。它以轻量级部署、简单易用、支持多种通信渠道为特点，为企业提供高效的消息传递和通知功能。
      </p>
      <Row gutter={[16, 16]}>
        {cards.map((item, index) => (
          <Col span={8} key={item.title}>
            <Card
              className={styles['gutterRow']}
              style={{ height: '100%', border: '0px solid #ececec' }}
              actions={[
                <a
                  key="more"
                  style={{ color: '#1677ff' }}
                  href={item.link}
                  target="_blank"
                  rel="noreferrer"
                >
                  了解更多 &gt;
                </a>
              ]}
            >
              <Card.Meta
                avatar={
                  <>
                    <Avatar
                      size={40}
                      src="https://gw.alipayobjects.com/zos/bmw-prod/daaf8d50-8e6d-4251-905d-676a24ddfa12.svg"
                    />
                    <span
                      style={{
                        position: 'absolute',
                        top: '24.5px',
                        left: '38.5px',
                        color: '#fff'
                      }}
                    >
                      {index + 1}
                    </span>
                  </>
                }
                title={item.title}
                description={item.desc}
                style={{ height: '100px' }}
              />
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default Welcome;
