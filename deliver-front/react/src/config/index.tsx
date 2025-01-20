import { Space, Input } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { ProTableProps } from '@ant-design/pro-components';

interface ProTableConfigProps {
  filterOpen?: boolean;
  deleteData?: (ids: number[]) => void;
  name?: string;
  onSearch?: (value: any) => void;
}

const nameMap = {
  taskName: '任务名',
  appName: '应用名',
  templateName: '模版名',
  platformFileName: '文件名',
  peopleGroupName: '人群名'
};

const findKeyByValue = (name: string): string | undefined => {
  return Object.keys(nameMap).find((key) => nameMap[key as keyof typeof nameMap] === name);
};

export const proTableConfig = ({
  filterOpen = false,
  deleteData,
  name = '名称',
  onSearch
}: ProTableConfigProps): Partial<ProTableProps<any, any>> => {

  const handleSearch = (e: any) => {
    onSearch?.((prev: any) => ({
      ...prev,
      [findKeyByValue(name) as string]: e?.target?.value
    }));
  };

  return {
    style: {
      width: filterOpen ? 'calc(100% - 300px)' : '100%',
      transition: 'width 0.3s ease-in-out'
    },
    tableAlertRender: ({ selectedRowKeys, onCleanSelected }) => (
      <Space size={24}>
        <span>
          已选 {selectedRowKeys.length} 项
          <a style={{ marginInlineStart: 8 }} onClick={onCleanSelected}>
            取消选择
          </a>
        </span>
      </Space>
    ),
    tableAlertOptionRender: ({ selectedRowKeys }) => (
      <Space size={16}>
        <a onClick={() => deleteData?.(selectedRowKeys as number[])}>批量删除</a>
      </Space>
    ),
    headerTitle: (
      <div style={{ width: '200px' }}>
        <Input
          placeholder={`请输入${name}进行查询`}
          style={{ borderRadius: '50px' }}
          prefix={<SearchOutlined />}
          onBlur={handleSearch}
          onPressEnter={handleSearch}
        />
      </div>
    ),
    options: false,
    search: false,
    pagination: {
      defaultCurrent: 1,
      defaultPageSize: 10,
      showSizeChanger: true,
      showTotal: (total: number) => `共 ${total} 条`,
      size: 'default'
    }
  };
};
