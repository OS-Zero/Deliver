import React, { useState } from 'react';
import { List, Button, Dropdown, Menu, Card, Tooltip, Input } from 'antd';
import {
  EditOutlined,
  DownOutlined,
  AppstoreOutlined,
  FilterOutlined,
  MenuOutlined,
  SearchOutlined
} from '@ant-design/icons';
import styles from '../index.module.scss';
import { getImg } from '@/utils/getTagStyle';
import { ChannelApp } from '../type';

interface ChannelCardViewProps {
  dataSource: ChannelApp[];
  filterOpen: boolean;
  isTableView: boolean;
  addRef: React.MutableRefObject<{ addChannelDrawer: () => void } | undefined>;
  setIsTableView: (value: boolean) => void;
  setFilterOpen: (value: boolean) => void;
  onChangeStatus: (appId: number, appStatus: number) => void;
  onHandleActions: (action: string, data: any) => void;
}

const ChannelCardView: React.FC<ChannelCardViewProps> = ({
  dataSource,
  filterOpen,
  addRef,
  isTableView,
  setFilterOpen,
  setIsTableView,
  onChangeStatus,
  onHandleActions
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

  const btn = () => {
    return (
      <>
        <div style={{ marginRight: 'auto', width: '200px' }}>
          <Input
            width={300}
            placeholder="请输入应用名进行查询"
            style={{ borderRadius: '50px' }}
            prefix={<SearchOutlined />}
            onBlur={(e) => onHandleActions('search', e)}
            onPressEnter={(e) => onHandleActions('search', e)}
          />
        </div>
        <Button
          key="add"
          type="primary"
          style={{ marginRight: '5px' }}
          onClick={() => addRef?.current?.addChannelDrawer()}
        >
          新增
        </Button>
        <div style={{ marginLeft: '15px', display: 'inline-block' }}>
          <Tooltip title="表格视图">
            <Button
              icon={<MenuOutlined />}
              size="small"
              type={isTableView ? 'primary' : 'text'}
              onClick={() => setIsTableView(true)}
              style={{ marginRight: '5px' }}
            />
          </Tooltip>
          <Tooltip title="卡片视图">
            <Button
              icon={<AppstoreOutlined />}
              size="small"
              type={!isTableView ? 'primary' : 'text'}
              onClick={() => setIsTableView(false)}
            />
          </Tooltip>
        </div>
        <Button
          shape="circle"
          icon={<FilterOutlined />}
          onClick={() => {
            setFilterOpen(!filterOpen);
            onHandleActions('filter', {});
          }}
          style={{ marginLeft: '11px' }}
        />
      </>
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
      <div className={styles['card-btns']}>{btn()}</div>
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
        dataSource={dataSource}
        renderItem={(item) => (
          <List.Item>
            <ChannelCard data={item} />
          </List.Item>
        )}
      />
    </div>
  );
};

export default ChannelCardView;
