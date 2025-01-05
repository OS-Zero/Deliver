import {
  saveChannelApp,
  deleteChannelApp,
  getChannelApp,
  updateChannelApp
} from '@/api/channelApp';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { useCallback, useState } from 'react';
import { ChannelApp, SearchParams } from './type';

const useTemplateData = () => {
  const [currentParams, setCurrentParams] = useState({ currentPage: 1, pageSize: 10 });
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchChannelData = async (params: SearchParams) => {
    setCurrentParams(params);
    const res = await getChannelApp(params);
    return {
      data: res?.data?.records,
      success: true,
      total: res?.data?.total
    };
  };

  /**
   * 新增模版
   * @param data
   */
  const saveChannelData = async (params: ChannelApp) => {
    console.log(params);
    await saveChannelApp(params);
  };

  /**
   * 更新模板状态
   * @param data
   */
  const changeStatus = async (appId: number, appStatus: number) => {
    await updateChannelApp({ appId, appStatus });
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

export default useTemplateData;
