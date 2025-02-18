import {
  saveChannelApp,
  deleteChannelApp,
  getChannelApp,
  updateChannelApp,
  updateChannelAppStatus
} from '@/api/channelApp';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { MutableRefObject, useCallback } from 'react';
import { ChannelApp, SearchParams } from './type';
import { transformParams } from '@/utils/omitProperty';
import { ActionType } from '@ant-design/pro-components';

const useChannelData = (props: { proTableRef: MutableRefObject<ActionType | undefined> }) => {
  const { proTableRef } = props;
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchChannelData = async (filter: SearchParams) => {
    const params = transformParams(filter);
    const res = await getChannelApp(params);
    return {
      data: res?.records,
      success: true,
      total: res?.total
    };
  };

  /**
   * 新增
   * @param data
   */
  const saveChannelData = async (params: ChannelApp) =>
    params?.appId ? await updateChannelApp(params) : await saveChannelApp(params);

  /**
   * 更新状态
   * @param data
   */
  const changeStatus = async (appId: number, appStatus: number) => {
    await updateChannelAppStatus({ appId, appStatus });
  };

  /**
   * 删除数据
   * @param id
   */
  const resetProTable = () => {
    (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
  };

  const deleteChannelData = useCallback((ids: number[], isBatchDelete: boolean = false) => {
    const confirmContent = isBatchDelete ? '确认批量删除应用?' : '确认删除该应用?';
    deleteConfirmModal({
      title: confirmContent,
      onConfirm: () => {
        deleteChannelApp({ ids });
        resetProTable();
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
