import React from 'react';
import { Card, Dropdown, Modal } from 'antd';
import {
  EllipsisOutlined,
  PlusOutlined,
  FieldTimeOutlined,
  ExclamationCircleOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { GroupCard } from '@/views/GroupManage/type';
import styles from './index.module.scss';

interface CardProps {
  data?: GroupCard;
  isEmpty?: boolean;
  isTop?: boolean;
  showAction?: boolean;
  onTop?: () => void;
  onEdit?: () => void;
  onDelete?: () => void;
  onClick?: () => void;
}

const GroupCardComponent: React.FC<CardProps> = ({
  data,
  isEmpty = false,
  isTop = false,
  // showAction = false,
  onTop,
  onEdit,
  onDelete,
  onClick
}) => {
  const navigate = useNavigate();

  const handleMore = () => {
    navigate('/groupManage/template');
    localStorage.setItem('group_id', data?.groupId + '' || '');
  };

  const showConfirm = () => {
    Modal.confirm({
      title: '确认删除该分组?',
      icon: <ExclamationCircleOutlined />,
      okText: '确认',
      cancelText: '取消',
      onOk: onDelete
    });
  };

  const handleOperation = (op: 'onTop' | 'onEdit' | 'onDelete' | 'cancelTop') => {
    if (op === 'onTop' && onTop) onTop();
    if (op === 'onEdit' && onEdit) onEdit();
    if (op === 'onDelete') showConfirm();
  };

  const items = isTop
    ? [{ label: '取消置顶', key: 'cancelTop' }]
    : [
        { label: '置顶', key: 'onTop' },
        { label: '编辑', key: 'onEdit' },
        { label: '删除', key: 'onDelete' }
      ];

  return isEmpty ? (
    <Card className={styles['empty_card']} onClick={onClick} style={{ width: 200 }}>
      <PlusOutlined className={styles['empty_icon']} />
      <div className={styles['empty_desc']}>添加</div>
    </Card>
  ) : (
    <Card className={styles['card']} hoverable onClick={() => handleMore()}>
      <Dropdown
        menu={{
          items,
          onClick: (e) => handleOperation(e.key as 'onTop' | 'onEdit' | 'onDelete' | 'cancelTop')
        }}
        placement="bottom"
      >
        <EllipsisOutlined className={styles['card_more']} />
      </Dropdown>
      <div className={styles['card_content']}>
        <h3 className={styles['card_title']}>{data?.groupName}</h3>
        <div>{data?.groupDescription}</div>
      </div>
      <div className={styles['card_time']}>
        <FieldTimeOutlined />
        {data?.updateTime}
      </div>
    </Card>
  );
};

export default GroupCardComponent;
