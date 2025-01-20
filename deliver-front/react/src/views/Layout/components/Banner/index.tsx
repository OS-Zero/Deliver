import React, { useState } from 'react';
import { CloseOutlined } from '@ant-design/icons';
import { Tooltip } from 'antd';
import styles from './index.module.scss';

const Banner: React.FC = () => {
  const [closed, setClosed] = useState(false);

  if (closed) return null;

  return (
    <div className={styles['banner']}>
      <div>
        ⭐️ 如果你喜欢 Deliver 消息推送平台，请给它一个 Star&nbsp;
        <a target="_blank" rel="noopener noreferrer" href="https://gitee.com/OS-Zero/deliver">
          Gitee
        </a>
        ，你的支持将是我们前行的动力，项目正在积极开发，欢迎大家提交 PR 提供建设、共建社区生态。👏🏻
        <Tooltip>
          <i className={styles['iconClose']} onClick={() => setClosed(true)}>
            <CloseOutlined />
          </i>
        </Tooltip>
      </div>
    </div>
  );
};

export default Banner;
