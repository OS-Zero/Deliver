import React from 'react';
import type { ProColumns } from '@ant-design/pro-components';
import { ProTable } from '@ant-design/pro-components';
import { Button, DatePicker, Space, Table, Input, Tag, Pagination } from 'antd';
import { FilterOutlined } from '@ant-design/icons';
import { DingDing } from '../../../../../public/assets/钉钉.png';

const Template: React.FC = () => {
  const { RangePicker } = DatePicker;
  const { Search } = Input;

  const valueEnum = {
    0: 'close',
    1: 'running',
    2: 'online',
    3: 'error'
  };

  const ProcessMap = {
    close: 'normal',
    running: 'active',
    online: 'success',
    error: 'exception'
  } as const;

  type TableListItem = {
    key: number;
    name: string;
    progress: number;
    containers: number;
    callNumber: number;
    creator: string;
    status: string;
    createdAt: number;
    memo: string;
  };
  const tableListDataSource: TableListItem[] = [];

  const creators = ['付小小', '曲丽丽', '林东东', '陈帅帅', '兼某某'];

  for (let i = 0; i < 50; i += 1) {
    tableListDataSource.push({
      key: i,
      name: 'AppName-' + i,
      containers: Math.floor(Math.random() * 20),
      callNumber: Math.floor(Math.random() * 2000),
      progress: Math.ceil(Math.random() * 100) + 1,
      creator: creators[Math.floor(Math.random() * creators.length)],
      status: valueEnum[((Math.floor(Math.random() * 10) % 4) + '') as '0'],
      createdAt: Date.now() - Math.floor(Math.random() * 100000),
      memo: i % 2 === 1 ? '很长很长很长很长很长很长很长的文字要展示但是要留下尾巴' : '简短备注文案'
    });
  }

  const columns: ProColumns<TableListItem>[] = [
    {
      title: '应用名称',
      width: 120,
      dataIndex: 'name',
      fixed: 'left',
      render: (_) => <a>{_}</a>
    },
    {
      title: '容器数量',
      width: 120,
      dataIndex: 'containers',
      align: 'right',
      search: false
    },
    {
      title: '调用次数',
      width: 120,
      align: 'right',
      dataIndex: 'callNumber'
    },
    {
      title: '执行进度',
      dataIndex: 'progress',
      valueType: (item) => ({
        type: 'progress',
        status: ProcessMap[item.status as 'close']
      })
    },
    {
      title: '创建者',
      width: 120,
      dataIndex: 'creator',
      valueType: 'select',
      valueEnum: {
        all: { text: '全部' },
        付小小: { text: '付小小' },
        曲丽丽: { text: '曲丽丽' },
        林东东: { text: '林东东' },
        陈帅帅: { text: '陈帅帅' },
        兼某某: { text: '兼某某' }
      }
    },
    {
      title: '创建时间',
      width: 140,
      key: 'since',
      dataIndex: 'createdAt',
      valueType: 'date',
      renderFormItem: () => {
        return <RangePicker />;
      }
    },
    {
      title: '备注',
      dataIndex: 'memo',
      ellipsis: true,
      copyable: true,
      search: false
    },
    {
      title: 'tag',
      dataIndex: 'tag',
      key: 'tag',
      render: (_, record) => <Tag color="magenta">magenta</Tag>
    },
    {
      title: '图片',
      dataIndex: 'image',
      key: 'image',
      valueType: 'image',
      render: (_, record) => (
        <img
          src={'../../../../../public/assets/飞书.png'}
          alt={record.key === 1 ? '飞书' : '钉钉'}
          style={{ width: 30, height: 30 }}
        />
      )
    },
    {
      title: '操作',
      width: 80,
      key: 'option',
      valueType: 'option',
      fixed: 'right',
      render: () => [<a key="link">链路</a>]
    }
  ];

  return (
    <div>
      <ProTable<TableListItem>
        columns={columns}
        rowSelection={{
          // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
          // 注释该行则默认不显示下拉选项
          // selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
          defaultSelectedRowKeys: [1]
        }}
        tableAlertRender={({ selectedRowKeys, selectedRows, onCleanSelected }) => {
          console.log(selectedRowKeys, selectedRows);
          return (
            <Space size={24}>
              <span>
                已选 {selectedRowKeys.length} 项
                <a style={{ marginInlineStart: 8 }} onClick={onCleanSelected}>
                  取消选择
                </a>
              </span>
            </Space>
          );
        }}
        tableAlertOptionRender={() => {
          return (
            <Space size={16}>
              <a>批量删除</a>
              <a>导出数据</a>
            </Space>
          );
        }}
        dataSource={tableListDataSource}
        scroll={{ x: 1300 }}
        options={false}
        search={false}
        pagination={{
          current: 1,
          defaultCurrent: 1,
          defaultPageSize: 10,
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: () => `Total items`,
          size: 'default'
        }}
        rowKey="key"
        headerTitle={<Search placeholder="请输入" enterButton />}
        toolBarRender={() => [
          <>
            <Button shape="circle" icon={<FilterOutlined />} style={{ marginRight: '10px' }} />
            <Button key="add" type="primary">
              新增模版
            </Button>
          </>
        ]}
      />
    </div>
  );
};

export default Template;
