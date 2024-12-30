import { forwardRef, useImperativeHandle, useState } from 'react';
import { Drawer } from 'antd';
import { ProDescriptions } from '@ant-design/pro-components';
import { detailColumns } from '../constant.tsx';
import { MessageTemplate } from '../type.ts';

const DetailDrawer = forwardRef((_, ref) => {
  const [templateDetail, setTemplateDetail] = useState<MessageTemplate>();
  const [open, setOpen] = useState(false);

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  useImperativeHandle(ref, () => ({
    getDetail: (data: MessageTemplate) => {
      setTemplateDetail(data);
      return showDrawer();
    }
  }));

  return (
    <Drawer title="数据详情" placement="left" closable={false} onClose={onClose} open={open}>
      <ProDescriptions dataSource={templateDetail} column={1} columns={detailColumns} />
    </Drawer>
  );
});

DetailDrawer.displayName = 'DetailDrawer';

export default DetailDrawer;
