import { getPlatformPages, savePlatformData } from '@/api/platform';
import { PlatformFileSearchParams, PlatformFileUploadParams } from './type';
import { transformParams } from '@/utils/omitProperty';

const useTemplateData = () => {
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchPlatformData = async (filter: PlatformFileSearchParams) => {
    const params = transformParams(filter);
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
  const savePlatform = async (params: PlatformFileUploadParams) => {
    await savePlatformData(params);
  };

  return {
    fetchPlatformData,
    savePlatform
  };
};

export default useTemplateData;
