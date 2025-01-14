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
        (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
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
