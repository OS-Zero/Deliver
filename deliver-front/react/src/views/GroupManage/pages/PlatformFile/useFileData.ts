import { getPlatformPages } from '@/api/platform';
import { PlatformFileDetail, PlatformFileSearchParams } from './type';

const useTemplateData = () => {
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchPlatformData = async (params: PlatformFileSearchParams) => {
    const res = await getPlatformPages(params);
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
  const savePlatformData = async (params: PlatformFileDetail) => {
    console.log(params);
    await savePlatformApp(params);
  };

  /**
   * 更新模板状态
   * @param data
   */
  const changeStatus = async (appId: number, appStatus: number) => {
    await updatePlatformApp({ appId, appStatus });
  };

  return {
    fetchPlatformData,
    savePlatformData,
    changeStatus
  };
};

export default useTemplateData;
