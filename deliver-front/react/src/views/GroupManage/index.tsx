import React, { useState, useEffect, useCallback, useRef } from 'react';
import { Drawer, Form, Input, Button, message, FormInstance } from 'antd';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import Card from './components/Card';
import { addGroup, getGroupData, updateGroup, deleteGroup, toTopGroup } from '@/api/group';
import { GroupCard, GroupCardList } from './type';
import styles from './index.module.scss';
import { groupDescriptionRule, groupNameRule } from './constant';
import { SearchOutlined } from '@ant-design/icons';
import deleteConfirmModal from '@/components/DeleteConfirmModal';

// 操作类型
type Operation = 'add' | 'edit' | 'delete' | 'toTop' | 'cancelTop';

const GroupManage: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const cardRef = useRef<FormInstance>(null);
  const [state, setState] = useState({
    mainPage: true,
    open: false,
    operation: 'add' as Operation,
    search: ''
  });
  const [groupList, setGroupList] = useState<GroupCardList>({
    topUpGroupList: [],
    defaultGroupList: []
  });
  const [groupId, setGroupId] = useState<number>();

  // 获取分组数据
  const fetchCardData = async (groupName = '') => {
    try {
      const res = await getGroupData({ groupName });
      setGroupList(res);
    } catch (error) {
      console.error('获取分组数据失败', error);
    }
  };

  // 操作成功处理
  const handleOperation = useCallback(
    async (operation: Operation, data?: GroupCard) => {
      try {
        if (operation === 'add' || operation === 'edit') {
          await cardRef?.current?.validateFields();
        }
        const cardRefValue = cardRef.current?.getFieldsValue();
        const operationMap = {
          add: () => addGroup(cardRefValue),
          edit: () => updateGroup({ groupId, ...cardRefValue }),
          delete: () =>
            deleteConfirmModal({
              onConfirm: () => {
                deleteGroup({ ids: [Number(data?.groupId)] });
                fetchCardData();
              }
            }),
          toTop: () => toTopGroup({ groupId: Number(data?.groupId), topUp: 1 }),
          cancelTop: () => toTopGroup({ groupId: Number(data?.groupId), topUp: 0 })
        };
        const successMessages = {
          add: '新增成功',
          edit: '编辑成功',
          delete: null,
          toTop: '置顶成功',
          cancelTop: '取消置顶成功'
        };
        await operationMap[operation]();
        if (successMessages[operation]) {
          message.success(successMessages[operation]);
        }
        if (operation === 'add' || operation === 'edit') {
          setState((prev) => ({ ...prev, open: false }));
          cardRef.current?.resetFields();
        }
        fetchCardData();
      } catch (error) {
        console.error('操作失败', error);
      }
    },
    [cardRef, groupId, fetchCardData]
  );

  // 渲染分组卡片
  const renderGroupCards = (groupList: GroupCard[], isTopGroup = false) => (
    <div className={styles[isTopGroup ? 'top-cards' : 'bottom-cards']}>
      {groupList.map((item) => (
        <Card
          key={item.groupId}
          data={item}
          isTop={!!item?.topUp}
          onTop={() => handleOperation(item?.topUp ? 'cancelTop' : 'toTop', item)}
          onEdit={() => {
            setState((prev) => ({ ...prev, operation: 'edit', open: true }));
            setGroupId(item.groupId);
            cardRef.current?.setFieldsValue(item);
          }}
          onDelete={() => handleOperation('delete', item)}
        />
      ))}
    </div>
  );

  // 检查主页状态
  useEffect(() => {
    const checkMainPage = async () => {
      const groupId = localStorage.getItem('group_id');
      if (groupId) {
        setState((prev) => ({ ...prev, mainPage: true }));
      } else {
        setState((prev) => ({ ...prev, mainPage: false }));
        await fetchCardData();
      }
    };
    checkMainPage();
  }, [location.pathname, navigate]);

  return !state.mainPage ? (
    <div>
      <div className={styles['card-top']}>
        <h3>置顶分组</h3>
        {!groupList.topUpGroupList.length && (
          <Card
            isEmpty
            isTop
            setOpen={() => setState((prev) => ({ ...prev, operation: 'add', open: true }))}
            onTop={() => setState((prev) => ({ ...prev, operation: 'add', open: true }))}
          />
        )}
        {renderGroupCards(groupList.topUpGroupList, true)}
      </div>
      <div className={styles['card-bottom']}>
        <div className={styles['bottom-section']}>
          <h3>全部分组</h3>
          <div className={styles['search-box']} style={{ width: '200px' }}>
            <Input
              className="search-txt"
              value={state.search}
              placeholder="请输入分组名"
              style={{ borderRadius: '50px' }}
              prefix={<SearchOutlined />}
              onChange={(e) => setState({ ...state, search: e.target.value })}
              onBlur={() => fetchCardData(state.search)}
              onPressEnter={() => fetchCardData(state.search)}
            />
          </div>
        </div>
        <div className={styles['bottom-cards']}>
          <Card
            isEmpty
            setOpen={() => setState((prev) => ({ ...prev, operation: 'add', open: true }))}
            onTop={() => setState((prev) => ({ ...prev, operation: 'add', open: true }))}
          />
          {renderGroupCards(groupList.defaultGroupList)}
        </div>
      </div>
      <Drawer
        width={500}
        title={state.operation === 'add' ? '新增分组' : '编辑分组'}
        placement="right"
        open={state.open}
        onClose={() => {
          setState((prev) => ({ ...prev, open: false }));
          cardRef.current?.resetFields();
        }}
        extra={
          <>
            <Button
              onClick={() => {
                setState((prev) => ({ ...prev, open: false }));
                cardRef.current?.resetFields();
              }}
              style={{ marginRight: '8px' }}
            >
              取消
            </Button>
            <Button type="primary" onClick={() => handleOperation(state.operation)}>
              确定
            </Button>
          </>
        }
      >
        <Form ref={cardRef} layout="vertical" name="basic">
          <Form.Item label="分组名" name="groupName" required rules={[groupNameRule]}>
            <Input placeholder="请输入分组名" maxLength={10} showCount />
          </Form.Item>
          <Form.Item
            label="分组描述"
            name="groupDescription"
            required
            rules={[groupDescriptionRule]}
          >
            <Input.TextArea placeholder="请输入分组描述" maxLength={50} showCount />
          </Form.Item>
        </Form>
      </Drawer>
    </div>
  ) : (
    <Outlet />
  );
};

export default GroupManage;
