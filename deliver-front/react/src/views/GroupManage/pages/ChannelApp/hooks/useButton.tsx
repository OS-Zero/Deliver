import { MenuOutlined, AppstoreOutlined, FilterOutlined, SearchOutlined } from '@ant-design/icons';
import { Button, Tooltip, Input } from 'antd';
import { MutableRefObject } from 'react';

interface CommonButtonsProps {
  addRef: MutableRefObject<{ addChannelDrawer: () => void } | undefined>;
  isTableView: boolean;
  filterOpen: boolean;
  setIsTableView: (value: boolean) => void;
  setFilterOpen: (value: boolean) => void;
  handleFilter: (filters: any) => void;
  handleSearch: (e: any) => void;
}

export const useButtons = ({
  addRef,
  filterOpen,
  isTableView,
  setIsTableView,
  setFilterOpen,
  handleFilter,
  handleSearch
}: CommonButtonsProps) => {
  // 渲染表格按钮
  const renderTableButtons = () => (
    <>
      <Button
        key="add"
        type="primary"
        style={isTableView ? {} : { marginRight: '5px' }}
        onClick={() => addRef?.current?.addChannelDrawer()}
      >
        新增
      </Button>
      <div style={{ marginLeft: '10px', display: 'inline-block' }}>
        <Tooltip title="表格视图">
          <Button
            icon={<MenuOutlined />}
            size="small"
            type={isTableView ? 'primary' : 'text'}
            onClick={() => setIsTableView(true)}
            style={{ marginRight: '5px' }}
          />
        </Tooltip>
        <Tooltip title="卡片视图">
          <Button
            icon={<AppstoreOutlined />}
            size="small"
            type={!isTableView ? 'primary' : 'text'}
            onClick={() => setIsTableView(false)}
          />
        </Tooltip>
      </div>
      <Button
        shape="circle"
        icon={<FilterOutlined />}
        onClick={() => {
          setFilterOpen(!filterOpen);
          if (filterOpen) {
            handleFilter({});
          }
        }}
        style={isTableView ? {} : { marginLeft: '11px' }}
      />
    </>
  );

  // 渲染卡片按钮
  const renderCardButtons = () => {
    return (
      <>
        <div style={{ marginRight: 'auto', width: '200px' }}>
          <Input
            width={300}
            placeholder="请输入应用名进行查询"
            style={{ borderRadius: '50px' }}
            prefix={<SearchOutlined />}
            onBlur={(e) => handleSearch(e)}
            onPressEnter={(e) => handleSearch(e)}
          />
        </div>
        {renderTableButtons()}
      </>
    );
  };

  return {
    renderTableButtons,
    renderCardButtons
  };
};
