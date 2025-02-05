import { forwardRef, useImperativeHandle, useState } from 'react';
import { Card, Drawer, Space } from 'antd';
import { ProDescriptions, ProDescriptionsItemProps } from '@ant-design/pro-components';
import { MessageTemplate } from '@/views/GroupManage/pages/MessageTemplate/type';

// 因为需要复用所以要兼容其他属性，这里因为ProDescriptionsItemProps的限制不得不写成any
const DetailDrawer = forwardRef(
  (
    props: {
      columns: {
        title: string;
        key: string;
        children: ProDescriptionsItemProps<any>[];
      }[];
      title?: string;
    },
    ref
  ) => {
    const { columns, title } = props;
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
      <Drawer
        title={title}
        placement="left"
        closable={false}
        onClose={onClose}
        open={open}
        width={500}
      >
        <Space direction="vertical" style={{ width: '100%' }} size="middle">
          {columns.map((section) => (
            <Card key={section.key} title={section.title} size="small">
              <ProDescriptions
                dataSource={templateDetail}
                column={1}
                columns={section.children}
                layout="vertical"
                size="middle"
              />
            </Card>
          ))}
        </Space>
      </Drawer>
    );
  }
);

DetailDrawer.displayName = 'DetailDrawer';

export default DetailDrawer;
