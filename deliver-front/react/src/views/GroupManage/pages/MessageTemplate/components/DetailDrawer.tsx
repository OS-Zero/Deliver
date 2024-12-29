import React, { forwardRef, useImperativeHandle, useState } from 'react';
import { Drawer } from 'antd';
import { ProDescriptions } from '@ant-design/pro-components';

const DetailDrawer: React.FC = forwardRef((props, ref) => {
  // TODO: props用来传递data
  const [open, setOpen] = useState(false);

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  // 暴露一个validate方法
  useImperativeHandle(ref, () => ({
    getDetail: () => {
      return showDrawer();
    }
  }));

  return (
    <Drawer title="数据详情" placement="left" closable={false} onClose={onClose} open={open}>
      {/* TODO：配置化columns */}
      <ProDescriptions column={2}>
        <ProDescriptions.Item
          span={2}
          valueType="text"
          contentStyle={{
            maxWidth: '80%'
          }}
          renderText={(_) => {
            return _ + _;
          }}
          ellipsis
          label="文本"
        >
          这是一段很长很长超级超级长的无意义说明文本并且重复了很多没有意义的词语，就是为了让它变得很长很长超级超级长
        </ProDescriptions.Item>
        <ProDescriptions.Item label="金额" tooltip="仅供参考，以实际为准" valueType="money">
          100
        </ProDescriptions.Item>
        <ProDescriptions.Item label="百分比" valueType="percent">
          100
        </ProDescriptions.Item>
        <ProDescriptions.Item
          label="选择框"
          valueEnum={{
            all: { text: '全部', status: 'Default' },
            open: {
              text: '未解决',
              status: 'Error'
            },
            closed: {
              text: '已解决',
              status: 'Success'
            },
            processing: {
              text: '解决中',
              status: 'Processing'
            }
          }}
        >
          open
        </ProDescriptions.Item>
      </ProDescriptions>
    </Drawer>
  );
});

DetailDrawer.displayName = 'DetailDrawer';

export default DetailDrawer;
