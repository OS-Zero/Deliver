import {
  saveChannelApp,
  deleteChannelApp,
  getChannelApp,
  updateChannelApp,
  updateChannelAppStatus
} from '@/api/channelApp';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { useCallback, useState } from 'react';
import { ChannelApp, SearchParams } from './type';

const useChannelData = () => {
  const [currentParams, setCurrentParams] = useState({ currentPage: 1, pageSize: 10 });
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchChannelData = async ({ current, ...restParams }: SearchParams & { current: number }) => {
    const params: SearchParams & { currentPage: number } = {
      ...restParams,
      currentPage: current || 1
    };
    setCurrentParams(params);
    const res = await getChannelApp(params);
    return {
      data: res?.records,
      success: true,
      total: res?.total
    };
  };

  /**
   * 新增模版
   * @param data
   */
  const saveChannelData = async (params: ChannelApp) => params?.appId ? await updateChannelApp(params) : await saveChannelApp(params);

  /**
   * 更新模板状态
   * @param data
   */
  const changeStatus = async (appId: number, appStatus: number) => {
    await updateChannelAppStatus({ appId, appStatus });
  };

  /**
   * 删除数据
   * @param id
   */
  const deleteChannelData = useCallback((ids: number[]) => {
    deleteConfirmModal({
      onConfirm: () => {
        deleteChannelApp({ ids });
        fetchChannelData(currentParams);
      }
    });
  }, []);

  return {
    fetchChannelData,
    deleteChannelData,
    saveChannelData,
    changeStatus
  };
};

export default useChannelData;
