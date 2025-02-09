import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, message, Upload } from 'antd';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useGlobalContext } from '@/context/GlobalContext';
import { InboxOutlined } from '@ant-design/icons';
import { analysisExcelTemplateFile } from '@/api/peopleGroup';
import { omitProperties } from '@/utils/omitProperty';
import { AddDrawerProps } from '@/types';

const { Dragger } = Upload;

const AddPeopleDrawer = forwardRef((props: AddDrawerProps, ref) => {
  const { onSubmit, reFresh } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const { userTypeParams } = useGlobalContext();

  const [userListError, setUserListError] = useState('');
  const [inputMethod, setInputMethod] = useState('upload');
  const [userCount, setUserCount] = useState(0);

  const [editValues, setEditValues] = useState<any>(null);

  const columns: ProFormColumnsType[] = [
    {
      title: '人群ID',
      dataIndex: 'peopleGroupId',
      valueType: 'text',
      formItemProps: {
        hidden: true
      }
    },
    {
      title: '人群名称',
      dataIndex: 'peopleGroupName',
      width: '100%',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入人群名称'
          }
        ]
      }
    },
    {
      title: '人群描述',
      dataIndex: 'peopleGroupDescription',
      width: '100%',
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请输入人群描述'
          }
        ]
      }
    },
    {
      title: '用户类型',
      dataIndex: 'usersType',
      valueType: 'select',
      fieldProps: {
        options: userTypeParams.map((d) => ({
          value: d.usersType,
          label: d.usersTypeName
        }))
      },
      formItemProps: {
        rules: [
          {
            required: true,
            message: '请选择用户类型'
          }
        ]
      }
    },
    {
      title: '',
      dataIndex: 'inputMethod',
      valueType: 'radio',
      fieldProps: {
        options: [
          { label: '上传文件解析人群列表', value: 'upload' },
          { label: '手动输入人群列表', value: 'manual' }
        ],
        onChange: (e: any) => setInputMethod(e.target.value)
      },
      initialValue: 'upload'
    },
    {
      title: '人群列表',
      dataIndex: 'peopleGroupList',
      valueType: 'textarea',
      fieldProps: {
        onChange: (e: any) => handleUserListChange(e.target.value)
      },
      formItemProps: {
        validateStatus: userListError ? 'error' : undefined,
        help: (
          <>
            {userListError && <div style={{ color: '#ff4d4f' }}>{userListError}</div>}
            <div style={{ color: '#8C8C8C' }}>
              使用英文逗号分割，最多可输入100个，共输入了
              <span style={{ color: '#ff4d4f' }}> {userCount}</span> 个号码
            </div>
          </>
        ),
        rules: [
          {
            required: true,
            message: '请输入人群列表'
          }
        ],
        hidden: inputMethod === 'upload'
      }
    },
    {
      title: '人群文件',
      dataIndex: 'peopleFile',
      formItemProps: {
        hidden: inputMethod === 'manual'
      },
      renderFormItem: () => (
        <Dragger
          maxCount={1}
          accept=".csv,.xlsx"
          beforeUpload={(file: File) => {
            handleFileUpload(file);
            return false;
          }}
        >
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">
            解析人群文件
            <p style={{ fontSize: '12px', color: '#999' }}>
              单次人群最多支持上传100条，请上传csv、xlsx格式文件，大小10MB以内
            </p>
          </p>
        </Dragger>
      )
    }
  ];

  const handleUserListChange = (value: string) => {
    const users = value.split(',').filter((user) => user.trim() !== '');
    setUserCount(users.length);

    // 验证逻辑
    const regExp = /^([^,\s]{1,30})(,[^,\s]{1,30})*$/;
    if (!value) {
      setUserListError('请输入人群列表');
    } else if (!regExp.test(value)) {
      setUserListError('人群列表格式错误');
    } else if (value.includes('，')) {
      setUserListError('请使用英文逗号分隔用户');
    } else {
      setUserListError('');
    }
  };

  const handleFileUpload = async (file: File) => {
    try {
      const result = await analysisExcelTemplateFile({ file });
      formRef.current?.setFieldsValue({ peopleGroupList: result });
      handleUserListChange(result);
      message.success('文件解析成功');
    } catch {
      message.error('文件解析失败');
    }
  };

  const handleSubmit = async () => {
    try {
      if (!formRef?.current?.getFieldValue('peopleGroupList')) {
        await handleUserListChange('');
      }
      const values = await formRef?.current?.validateFields();
      await onSubmit?.(omitProperties(values, ['inputMethod', 'peopleFile']));
      onClose();
      message.success('保存成功');
      reFresh?.();
    } catch (error) {
      console.error('保存失败:', error);
    }
  };

  const onClose = () => {
    setOpen(false);
    formRef?.current?.resetFields();
    setUserListError('');
    setInputMethod('upload');
    setUserCount(0);
  };

  useEffect(() => {
    if (open && editValues) {
      formRef.current?.setFieldsValue(editValues);
      formRef.current?.setFieldValue('inputMethod', 'manual');
      setInputMethod('manual');
      handleUserListChange(editValues.peopleGroupList);
      setEditValues(null);
    }
  }, [open, editValues]);

  useImperativeHandle(ref, () => ({
    addPeopleDrawer: () => {
      setOpen(true);
    },
    editPeopleDrawer: (values: any) => {
      setEditValues(values);
      setOpen(true);
    }
  }));

  return (
    <Drawer
      title={formRef?.current?.getFieldValue('peopleGroupId') ? '编辑人群' : '新增人群'}
      open={open}
      forceRender
      onClose={onClose}
      width={500}
      extra={
        <Space>
          <Button onClick={onClose}>取消</Button>
          <Button type="primary" onClick={handleSubmit}>
            确定
          </Button>
        </Space>
      }
    >
      <BetaSchemaForm
        shouldUpdate={true}
        layout="vertical"
        wrapperCol={{ span: 24 }}
        submitter={false}
        formRef={formRef}
        columns={columns}
      />
    </Drawer>
  );
});

AddPeopleDrawer.displayName = 'AddPeopleDrawer';

export default AddPeopleDrawer;
