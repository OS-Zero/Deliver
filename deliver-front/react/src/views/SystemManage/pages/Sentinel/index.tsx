import { useState } from 'react';
import { Input } from 'antd';
import styles from './index.module.scss';

const Sentinel = () => {
  const [iframeSrc, setIframeSrc] = useState('https://oszero.cn/');

  return (
    <div className={styles['iframeContainer']}>
      <Input
        className={styles['iframeInput']}
        placeholder="请输入网址"
        value={iframeSrc}
        onChange={(e) => setIframeSrc(e.target.value)}
      />
      <iframe
        src={iframeSrc}
        frameBorder="0"
        width="100%"
        height="100%"
        sandbox="allow-same-origin allow-scripts allow-popups allow-forms"
        referrerPolicy="no-referrer"
      />
    </div>
  );
};

export default Sentinel;
