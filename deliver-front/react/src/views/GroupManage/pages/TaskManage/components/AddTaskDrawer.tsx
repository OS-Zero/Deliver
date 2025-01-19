import { forwardRef, useImperativeHandle, useRef, useState } from 'react';
import { Button, Drawer, Space, FormInstance, Select, message } from 'antd';
import { JsonEditor } from 'jsoneditor-react';
import dayjs from 'dayjs';
import local from 'antd/lib/date-picker/locale/zh_CN.js';
import { BetaSchemaForm, ProFormColumnsType } from '@ant-design/pro-components';
import { useFormOptions } from '@/hooks/useFormOptions';
import { useDebounce } from '@/hooks/useDebounce';
import { isValidCron } from 'cron-validator';

interface AddChannelDrawerProps {
  onSubmit?: (values: any) => void;
  reFresh?: () => void;
}

const AddChannelDrawer = forwardRef((props: AddChannelDrawerProps, ref) => {
  const { onSubmit, reFresh } = props;
  const [open, setOpen] = useState(false);
  const formRef = useRef<FormInstance>(null);
  const [jsonEditorKey, setJsonEditorKey] = useState(0);

  const { options, handleTemplateSearch, handlePeopleGroupSearch, handleTemplateChange } =
    useFormOptions({
      myRef: formRef,
      key: 'task',
      setJsonEditorKey
    });

  const debouncedHandleTemplateSearch = useDebounce(handleTemplateSearch, 300);
  const debouncedHandlePeopleGroupSearch = useDebounce(handlePeopleGroupSearch, 300);

  const rule = (label: string) => ({
    formItemProps: {
      rules: [{ required: true, message: `${label}不可为空` }]
    }
  });

  const columns: ProFormColumnsType[] = [
    {
      title: '任务ID',
      dataIndex: 'taskId',
      valueType: 'text',
      formItemProps: {
        hidden: true
      }
    },
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
          { value: 1, label: '实时任务' },
          { value: 2, label: '定时循环任务' },
          { value: 3, label: '定时单次任务' }
        ],
        onChange: () => {
          formRef?.current?.resetFields(['taskTimeExpression']);
        }
      }
    },
    {
      title: '任务时间表达式',
      dataIndex: 'taskTimeExpression',
      valueType: 'text',
      width: '100%',
      dependencies: ['taskType'],
      formItemProps(form) {
        if (form.getFieldValue('taskType') === 2) {
          return {
            hidden: false,
            rules: [
              {
                required: true,
                validator: (_, value) => {
                  if (!value) {
                    return Promise.reject(new Error('Cron 表达式不可为空'));
                  }
                  const isValidate = isValidCron(value, {
                    seconds: true,
                    alias: true,
                    allowBlankDay: true,
                    allowSevenAsSunday: true
                  });
                  if (!isValidate) {
                    return Promise.reject(new Error('请输入有效的 Cron 表达式'));
                  }
                  return Promise.resolve();
                }
              }
            ],
            extra:
              'Cron 表达式格式: 秒(0-59) 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(0-6或SUN-SAT) 年(可选,1970-2099). 例如: 20 3 * * * ? 表示每天 3:20 执行任务; 20 3 8 * * ? 表示每月 8 号 3:20 执行任务; * 表示任意值; ? 用于表示不指定某个字段的值.'
          };
        }
        return {
          hidden: true
        };
      }
    },
    {
      title: '任务时间表达式',
      dataIndex: 'taskTimeExpression',
      valueType: 'dateTime',
      width: '100%',
      dependencies: ['taskType'],
      formItemProps(form) {
        if (form.getFieldValue('taskType') === 3) {
          return {
            hidden: false,
            rules: [{ required: true, message: '执行时间不可为空' }]
          };
        }
        return {
          hidden: true
        };
      },
      fieldProps: {
        showTime: true,
        format: 'YYYY-MM-DD HH:mm:ss',
        placeholder: '请选择执行时间',
        locale: local
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
              handleTemplateChange(value);
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
      renderFormItem: () => <JsonEditor key={jsonEditorKey} mode="code" />
    }
  ];

  const handleSubmit = async () => {
    try {
      const values = await formRef?.current?.validateFields();
      values.taskMessageParam = JSON.stringify(values.taskMessageParam);
      if (values.taskType === 3 && values.taskTimeExpression) {
        values.taskTimeExpression = dayjs(values.taskTimeExpression).format('YYYY-MM-DD HH:mm:ss');
      }
      await onSubmit?.(values);
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
    addTaskDrawer: async () => {
      setOpen(true);
      formRef?.current?.setFieldsValue({
        taskMessageParam: {
          title: 'oszero每天起床',
          content: '兄弟们该起床啦，6点啦，太阳晒屁股了',
          htmlFlag: true
        }
      });
      setJsonEditorKey(jsonEditorKey + 1);
    },
    editTaskModal: async (values: any) => {
      setOpen(true);
      await debouncedHandleTemplateSearch(values.templateName);
      await debouncedHandlePeopleGroupSearch(values.peopleGroupName);
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
        width="100%"
        shouldUpdate={false}
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
