import React from 'react';
import { useNavigate } from 'react-router-dom';
import { EllipsisOutlined, PlusOutlined, FieldTimeOutlined, SoundFilled } from '@ant-design/icons';
import { Dropdown, MenuProps } from 'antd';
import styles from './index.module.scss';

interface CardProps {
  data?: any;
  isEmpty?: boolean;
  isTop?: boolean;
  onTop?: (data?: any) => void;
  onEdit?: (data?: any) => void;
  onDelete?: (data?: any) => void;
  setOpen?: () => void;
}

const Card: React.FC<CardProps> = ({
  data,
  isEmpty = false,
  isTop,
  onTop,
  onEdit,
  onDelete,
  setOpen
}) => {
  const navigate = useNavigate();

  const handleJumpLink = () => {
    if (data?.groupId) {
      localStorage.setItem('group_id', data.groupId.toString());
      navigate('/groupManage/template');
    }
  };

  const handleOperation = (key: string) => {
    console.log(data);
    switch (key) {
      case 'top':
        onTop?.(data);
        break;
      case 'edit':
        onEdit?.(data);
        break;
      case 'delete':
        onDelete?.(data);
        break;
    }
  };

  const items: MenuProps['items'] = [
    {
      key: 'top',
      label: isTop ? '取消置顶' : '置顶'
    },
    {
      key: 'edit',
      label: '编辑'
    },
    {
      key: 'delete',
      label: '删除'
    }
  ];

  const menuProps: MenuProps = {
    items,
    onClick: ({ key }) => handleOperation(key)
  };

  if (isEmpty) {
    if (isTop) {
      return (
        <div className={`${styles['card']} ${styles['top-card-empty']}`} onClick={setOpen}>
          <div className={styles['empty-desc']}>
            当前置顶分组为空，你可以选择置顶分组方便后续查找分组
          </div>
        </div>
      );
    }
    return (
      <div className={`${styles['card']} ${styles['card-empty']}`} onClick={setOpen}>
        <PlusOutlined className={styles['empty-icon']} />
        <div className={styles['empty-desc']}>添加分组</div>
      </div>
    );
  }

  return (
    <div className={styles['card']} onClick={handleJumpLink}>
      <div className={styles['card-more']}>
        <Dropdown menu={menuProps} placement="bottom">
          <EllipsisOutlined />
        </Dropdown>
      </div>
      <SoundFilled className={styles['sound-icon']} />
      <h4 className={styles['card-title']}>{data?.groupName}</h4>
      <div className={styles['card-content']}>{data?.groupDescription}</div>
      <div className={styles['card-time']}>
        <span className={styles['time-icon']}>
          <FieldTimeOutlined />
        </span>
        <span className={styles['time-text']}>{data?.updateTime}</span>
      </div>
    </div>
  );
};

export default Card;
