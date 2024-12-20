import React from 'react';
import { Card, Modal } from 'antd';
import {
  DeleteOutlined,
  EditOutlined,
  VerticalAlignTopOutlined,
  PlusOutlined,
  ExclamationCircleOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { GroupCard } from '@/views/GroupManage/type';
import styles from './index.module.scss';

interface CardProps {
  data?: GroupCard;
  isEmpty?: boolean;
  showAction?: boolean;
  onTop?: () => void;
  onEdit?: () => void;
  onDelete?: () => void;
  onClick?: () => void;
}

const GroupCardComponent: React.FC<CardProps> = ({
  data,
  isEmpty = false,
  showAction = false,
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

  const renderActions = () =>
    showAction
      ? [
          <VerticalAlignTopOutlined key="top" onClick={onTop} />,
          <EditOutlined key="edit" onClick={onEdit} />,
          <DeleteOutlined key="delete" onClick={showConfirm} />
        ]
      : [];

  return isEmpty ? (
    <Card className={styles['empty_card']} onClick={onClick}>
      <PlusOutlined className={styles['empty_icon']} />
    </Card>
  ) : (
    <Card
      className={styles['ant_card']}
      hoverable
      cover={<img onClick={handleMore} alt="example" src="/folder.svg" />}
      actions={renderActions()}
    >
      <Card.Meta description={data?.groupDescription} />
      <Card.Meta description={data?.updateTime} />
    </Card>
  );
};

export default GroupCardComponent;
