import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import React from 'react';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: 'OSZero 开源社区出品',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${'OSZero 开源社区出品'}`}
      links={[
        {
          key: 'Deliver 消息推送平台',
          title: 'Deliver 消息推送平台',
          href: 'https://gitee.com/OS-Zero/deliver',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://gitee.com/OS-Zero',
          blankTarget: true,
        },
        {
          key: 'OSZero',
          title: 'OSZero',
          href: 'https://os-zero.gitee.io/website/',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
