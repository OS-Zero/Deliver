import React from 'react';
import { Flex, Spin } from 'antd';

const Loading: React.FC = () => {
  return (
    <Flex gap="middle" align="start" vertical>
      <Flex style={{ width: '100vw', height: '100vh' }} justify={'center'} align={'center'}>
        <Spin size="large" />
      </Flex>
    </Flex>
  );
};

export default Loading;
