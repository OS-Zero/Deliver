import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, Select, message } from 'antd';
import { JsonEditor } from 'jsoneditor-react';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useFormOptions } from '@/hooks/useFormOptions';
import { useDebounce } from '@/hooks/useDebounce';
interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
  reFresh?: () => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit, reFresh } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const { options, handleTemplateSearch, handlePeopleGroupSearch } = useFormOptions({
    myRef: formRef,
    key: 'task'
  });

  const debouncedHandleTemplateSearch = useDebounce(handleTemplateSearch, 300);
  const debouncedHandlePeopleGroupSearch = useDebounce(handlePeopleGroupSearch, 300);

  const rule = (label: string) => {
    return {
      formItemProps: {
        rules: [
          {
            required: true,
            message: `${label}不可为空`
          }
        ]
      }
    };
  };

  const columns: ProFormColumnsType[] = [
    {
      title: '任务名',
      dataIndex: 'taskName',
      ...rule('任务名'),
      width: '100%'
    },
    {
      title: '任务描述',
      dataIndex: 'taskDescription',
      valueType: 'textarea',
      ...rule('任务描述'),
      width: '100%'
    },
    {
      title: '任务类型',
      dataIndex: 'taskType',
      valueType: 'radio',
      width: '100%',
      initialValue: 1,
      fieldProps: {
        options: [
          {
            value: 1,
            label: '实时任务'
          },
          {
            value: 2,
            label: '定时循环任务'
          },
          {
            value: 3,
            label: '定时单次任务'
          }
        ]
      }
    },
    {
      title: '关联模版',
      dataIndex: 'templateId',
      ...rule('关联模版'),
      width: '100%',
      renderFormItem: () => {
        return (
          <Select
            showSearch
            placeholder="请输入关键字搜索模版"
            filterOption={false}
            onSearch={(value) => debouncedHandleTemplateSearch(value)}
            onChange={(value) => {
              formRef?.current?.setFieldsValue({ templateId: value });
            }}
            options={options.templateOptions?.map((item: any) => ({
              label: item.templateName,
              value: item.templateId
            }))}
          />
        );
      }
    },
    {
      title: '关联人群',
      dataIndex: 'peopleGroupId',
      ...rule('关联人群'),
      width: '100%',
      renderFormItem: () => {
        return (
          <Select
            showSearch
            placeholder="请输入关键字搜索人群"
            filterOption={false}
            onSearch={(value) => debouncedHandlePeopleGroupSearch(value)}
            onChange={(value) => {
              formRef?.current?.setFieldsValue({ peopleGroupId: value });
            }}
            options={options.peopleGroupOptions.map((d) => ({
              value: d.peopleGroupId,
              label: d.peopleGroupName
            }))}
          />
        );
      }
    },
    {
      title: '任务消息参数',
      key: 'taskMessageParam',
      dataIndex: 'taskMessageParam',
      initialValue: {
        title: 'oszero每天起床',
        content: '兄弟们该起床啦，6点啦，太阳晒屁股了',
        htmlFlag: true
      },
      renderFormItem: () => {
        return (
          <JsonEditor
            key={jsonEditorKey}
            mode="code"
            onChange={(e: object) => formRef.current?.setFieldsValue({ taskMessageParam: e })}
          />
        );
      }
    }
  ];

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      values.taskMessageParam = JSON.stringify(values.taskMessageParam);
      onSubmit?.(values);
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
  };

  useImperativeHandle(ref, () => ({
    addTaskDrawer: () => {
      setOpen(true);
    },
    editTaskModal: async (values: any) => {
      setOpen(true);
      await debouncedHandleTemplateSearch(values.templateId)
      formRef?.current?.setFieldsValue({
        ...values,
        taskMessageParam: JSON.parse(values.taskMessageParam)
      });
      setJsonEditorKey(jsonEditorKey + 1);
    }
  }));

  return (
    <Drawer
      title={formRef?.current?.getFieldValue('taskId') ? '编辑任务' : '新增任务'}
      open={open}
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
        width="100%"
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

AddChannelDrawer.displayName = 'AddChannelDrawer';

export default AddChannelDrawer;
