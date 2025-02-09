import React, { useState } from 'react';
import { List, Button, Dropdown, Menu, Card, Tooltip, ConfigProvider } from 'antd';
import { EditOutlined, DownOutlined } from '@ant-design/icons';
import styles from '../index.module.scss';
import { getImg } from '@/utils/getTagStyle';
import { ChannelApp } from '../type';
import zh_CN from 'antd/es/locale/zh_CN';

interface ChannelCardViewProps {
  dataSource: {
    records: ChannelApp[];
    total: number;
  };
  filterOpen: boolean;
  onChangeStatus: (appId: number, appStatus: number) => void;
  onHandleActions: (action: string, data: any) => void;
  renderCardButtons: () => React.ReactNode;
  renderCardPagination: any;
}

const ChannelCardView: React.FC<ChannelCardViewProps> = ({
  dataSource,
  filterOpen,
  onChangeStatus,
  onHandleActions,
  renderCardButtons,
  renderCardPagination
}) => {
  const ChannelCard: React.FC<{ data: ChannelApp }> = ({ data }) => {
    const [keys, setKeys] = useState<number>(0);

    const menu = (
      <Menu>
        <Menu.Item key="more" onClick={() => onHandleActions('more', data)}>
          查看更多
        </Menu.Item>
      </Menu>
    );

    const handleChangeStatus = async () => {
      await onChangeStatus(data?.appId, data?.appStatus ? 0 : 1);
      data.appStatus = data?.appStatus ? 0 : 1;
      setKeys((prev) => prev + 1);
    };

    return (
      <Card className={styles.card}>
        <div className={styles.card_header}>
          <Tooltip title="编辑">
            <Button
              type="link"
              icon={<EditOutlined />}
              className={styles.edit_btn}
              onClick={() => onHandleActions('edit', data)}
            />
          </Tooltip>
          <img className={styles.card_img} src={getImg(data.channelType).src} alt="" />
          <div className={styles.card_info}>
            <div className={styles.card_title}>
              <h4>{data.appName}</h4>
              <span
                key={keys}
                className={`${styles.card_status} ${data.appStatus === 1 ? styles.open : styles.close}`}
              />
            </div>
            <div className={styles.info_creator}>创建者：{data.createUser}</div>
          </div>
        </div>
        <div className={styles.card_btns}>
          <div className={styles.btn_operation}>
            {data.appStatus ? (
              <Button onClick={() => handleChangeStatus()}>禁用</Button>
            ) : (
              <>
                <Button type="primary" onClick={() => handleChangeStatus()}>
                  开启
                </Button>
                <Button onClick={() => onHandleActions('delete', data)}>删除</Button>
              </>
            )}
          </div>
          <Dropdown overlay={menu} placement="bottomRight">
            <div className={styles.more_btn}>
              更多操作
              <DownOutlined />
            </div>
          </Dropdown>
        </div>
      </Card>
    );
  };

  return (
    <div
      className={styles['card-container']}
      style={{
        width: filterOpen ? 'calc(100% - 300px)' : '100%',
        transition: 'width 0.3s ease-in-out'
      }}
    >
      <div className={styles['card-btns']}>{renderCardButtons()}</div>
      <List
        grid={{
          gutter: 16,
          xs: 1,
          sm: 2,
          md: 3,
          lg: 3,
          xl: 4,
          xxl: 5
        }}
        dataSource={dataSource?.records || []}
        renderItem={(item) => (
          <List.Item>
            <ChannelCard data={item} />
          </List.Item>
        )}
      />
      <ConfigProvider locale={zh_CN}>{renderCardPagination()}</ConfigProvider>
    </div>
  );
};

export default ChannelCardView;
