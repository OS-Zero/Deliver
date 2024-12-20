import React, { useState, useEffect } from 'react';
import { Drawer, Form, Input, Button, message } from 'antd';
import { useLocation } from 'react-router-dom';
import Card from './components/Card';
import { addGroup, getGroupData, updateGroup, deleteGroup, toTopGroup } from '@/api/group';
import { GroupCard, GroupCardList } from './type';
import styles from './index.module.scss';
import { groupDescriptionRule, groupNameRule } from './constant';

const GroupManage: React.FC = () => {
  const location = useLocation();
  const [form] = Form.useForm();

  const [state, setState] = useState({
    mainPage: true,
    open: false,
    operation: 'add' as 'add' | 'edit' | 'delete' | 'toTop',
    search: ''
  });

  const [groupList, setGroupList] = useState<GroupCardList>({
    topUpGroupList: [],
    defaultGroupList: []
  });

  const [groupFormData, setGroupFormData] = useState<GroupCard>({
    groupId: 0,
    groupName: '',
    groupDescription: ''
  });

  const fetchCardData = async (groupName: string = '') => {
    const res = await getGroupData({ groupName });
    setGroupList(res);
  };

  const handleSuccess = async (operation: 'add' | 'edit' | 'delete' | 'toTop') => {
    try {
      await form.validateFields();
      const operations = {
        add: async () => {
          const res = await addGroup(groupFormData);
          if (res) message.success('新增成功');
        },
        edit: async () => {
          const res = await updateGroup(groupFormData);
          if (res) message.success('编辑成功');
        },
        delete: async () => {
          const res = await deleteGroup(groupFormData.groupId);
          if (res) message.success('删除成功');
        },
        toTop: async () => {
          const res = await toTopGroup({ ids: [groupFormData.groupId] });
          if (res) message.success('置顶成功');
        }
      };
      await operations[operation]();
      onClose();
      fetchCardData();
    } catch (error) {
      console.error(error);
    }
  };

  const onOpen = () => setState((prev) => ({ ...prev, open: true }));
  const onClose = () => setState((prev) => ({ ...prev, open: false }));

  useEffect(() => {
    const checkMainPage = async () => {
      if (!localStorage.getItem('group_id') && location.pathname === '/groupManage') {
        setState((prev) => ({ ...prev, mainPage: true }));
        await fetchCardData();
      } else {
        setState((prev) => ({ ...prev, mainPage: false }));
      }
    };

    checkMainPage();
  }, [location.pathname]);

  useEffect(() => {
    fetchCardData();
  }, []);

  const changeOperation = (op: 'add' | 'edit' | 'delete' | 'toTop', data?: GroupCard) => {
    setState((prev) => ({ ...prev, operation: op }));
    if (data) setGroupFormData(data);
    if (op === 'add' || op === 'edit') onOpen();
    else handleSuccess(op);
  };

  return (
    <div>
      <div className={styles['card-top']}>
        <h3>置顶分组</h3>
        <div className={styles['top-cards']}>
          {groupList.topUpGroupList.map((item) => (
            <Card key={item.groupId} data={item} />
          ))}
        </div>
      </div>
      <div className={styles['card-bottom']}>
        <div className={styles['bottom-section']}>
          <h3>全部分组</h3>
          <Input.Search
            value={state.search}
            onChange={(e) => setState((prev) => ({ ...prev, search: e.target.value }))}
            placeholder="请输入分组名"
            enterButton
            style={{ width: 200 }}
            onSearch={() => fetchCardData(state.search)}
          />
        </div>
        <div className={styles['bottom-cards']}>
          <Card isEmpty onClick={() => changeOperation('add')} />
          {groupList.defaultGroupList.map((item) => (
            <Card
              key={item.groupId}
              data={item}
              showAction
              onTop={() => changeOperation('toTop', item)}
              onEdit={() => changeOperation('edit', item)}
              onDelete={() => changeOperation('delete', item)}
            />
          ))}
        </div>
      </div>
      <Drawer
        width={500}
        title={state.operation === 'add' ? '新增分组' : '编辑分组'}
        placement="right"
        open={state.open}
        onClose={onClose}
        extra={
          <>
            <Button style={{ marginRight: 8 }} onClick={onClose}>
              取消
            </Button>
            <Button type="primary" onClick={() => handleSuccess(state.operation)}>
              确定
            </Button>
          </>
        }
      >
        <Form
          form={form}
          initialValues={groupFormData}
          onValuesChange={(changedValues) =>
            setGroupFormData((prev) => ({ ...prev, ...changedValues }))
          }
          name="basic"
          labelCol={{ span: 5 }}
        >
          <Form.Item label="分组名" name="groupName" required rules={[groupNameRule]}>
            <Input placeholder="请输入分组名" maxLength={10} />
          </Form.Item>
          <Form.Item
            label="分组描述"
            name="groupDescription"
            required
            rules={[groupDescriptionRule]}
          >
            <Input.TextArea placeholder="请输入分组描述" maxLength={50} />
          </Form.Item>
        </Form>
      </Drawer>
    </div>
  );
};

export default GroupManage;
