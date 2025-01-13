import { getPlatformPages } from '@/api/platform';
import { PlatformFileSearchParams, PlatformFileUploadParams } from './type';

const useTemplateData = () => {
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchPlatformData = async (params: PlatformFileSearchParams) => {
    const res = await getPlatformPages(params);
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
  const savePlatformData = async (params: PlatformFileUploadParams) => {
    console.log(params);
    await savePlatformData(params);
  };

  return {
    fetchPlatformData,
    savePlatformData
  };
};

export default useTemplateData;
