import { forwardRef, useImperativeHandle, useState } from 'react';
import { Drawer } from 'antd';
import { ProDescriptions, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { MessageTemplate } from '@/views/GroupManage/pages/MessageTemplate/type';

const DetailDrawer = forwardRef((props: {columns: ProDescriptionsItemProps<MessageTemplate>[]}, ref) => {
  const {columns} = props;
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
      <ProDescriptions dataSource={templateDetail} column={1} columns={columns} />
    </Drawer>
  );
});

DetailDrawer.displayName = 'DetailDrawer';

export default DetailDrawer;
