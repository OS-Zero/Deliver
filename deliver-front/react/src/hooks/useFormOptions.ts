import { getAppConfig, getChannelType, getFileType, getChannelProviderType } from '@/api/system';
import { Channel, ChannelProvider, PlatFormFile } from '@/types';
import { useEffect, useState } from 'react';

interface UseFormOptionsProps {
  myRef: any;
  setJsonEditorKey?: any;
  key: string;
}

export const useFormOptions = (props: UseFormOptionsProps) => {
  const { myRef, key, setJsonEditorKey } = props;
  const [options, setOptions] = useState<{
    channelTypeOptions: Channel[];
    channelProvidersOptions: ChannelProvider[];
    fileTypeOptions: PlatFormFile[];
  }>({
    channelTypeOptions: [],
    channelProvidersOptions: [],
    fileTypeOptions: []
  });

  /**
   * 应用配置模块
   */

  // 渠道类型变更处理
  const handleChannelTypeChange = async (value: number) => {
    switch (key) {
      case 'channel':
        myRef?.current?.resetFields(['channelProviderType', 'appConfig']);
        try {
          if (value) {
            const response = await getChannelProviderType({ channelType: value });
            setOptions((prev) => ({
              ...prev,
              channelProvidersOptions: response || []
            }));
          } else {
            setOptions((prev) => ({
              ...prev,
              channelProvidersOptions: []
            }));
          }
        } catch (error) {
          console.error('获取渠道供应商失败:', error);
        }
        break;
      case 'file':
        myRef?.current?.resetFields(['channelProviderType']);
        try {
          const res1 = await getChannelProviderType({ channelType: value });
          const res2: PlatFormFile[] = await getFileType({ channelType: value });
          setOptions((prev) => ({
            ...prev,
            channelProvidersOptions: res1 || [],
            fileTypeOptions: res2 || []
          }));
        } catch (error) {
          console.error('获取渠道供应商失败:', error);
        }
        break;
      default:
        break;
    }
  };

  // 渠道供应商类型变更处理
  const handleChannelProviderTypeChange = async (value: number) => {
    const channelType = myRef?.current?.getFieldValue('channelType');
    if (channelType && value) {
      try {
        const response = await getAppConfig({ channelType, channelProviderType: value });
        myRef?.current?.setFieldValue('appConfig', JSON.parse(response));
        setJsonEditorKey?.((prev: number) => prev + 1); // 渲染视图，处理变更
      } catch (error) {
        console.error('获取应用配置失败:', error);
      }
    }
  };

  // 获取渠道类型数据
  useEffect(() => {
    if (!options.channelTypeOptions.length) {
      getChannelType({ usersType: -1 }).then((res: Channel[]) => {
        setOptions((prev) => ({
          ...prev,
          channelTypeOptions: res
        }));
      });
    }
  }, []);

  return {
    options,
    handleChannelTypeChange,
    handleChannelProviderTypeChange
  };
};
