import { getMessageParamByMessageType } from '@/api/messageTemplate';
import {
  getAppConfig,
  getChannelType,
  getFileType,
  getChannelProviderType,
  getMessageType,
  getApp,
  getTemplate,
  getPeopleGroup
} from '@/api/system';
import { Channel, ChannelProvider, Message, PlatFormFile, App, Template, People } from '@/types';
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
    messageTypeOptions: Message[];
    appOptions: App[];
    templateOptions: Template[];
    peopleGroupOptions: People[];
  }>({
    channelTypeOptions: [],
    channelProvidersOptions: [],
    fileTypeOptions: [],
    messageTypeOptions: [],
    appOptions: [],
    templateOptions: [],
    peopleGroupOptions: []
  });

  // 用户类型变更处理
  const handleUsersTypeChange = async (value: number) => {
    if (value) {
      try {
        const res = await getChannelType({ usersType: value });
        setOptions((prev) => ({
          ...prev,
          channelTypeOptions: res || []
        }));
      } catch (error) {
        console.error('获取用户类型失败:', error);
      }
    }
  };

  // 渠道类型变更处理
  const handleChannelTypeChange = async (value: number) => {
    switch (key) {
      case 'file':
        myRef?.current?.resetFields(['channelProviderType', 'platformFileType', 'appId']);
        try {
          if (value) {
            const res1 = await getChannelProviderType({ channelType: value });
            const res2: PlatFormFile[] = await getFileType({ channelType: value });
            setOptions((prev) => ({
              ...prev,
              channelProvidersOptions: res1 || [],
              fileTypeOptions: res2 || []
            }));
          } else {
            setOptions((prev) => ({
              ...prev,
              channelProvidersOptions: [],
              fileTypeOptions: []
            }));
          }
        } catch (error) {
          console.error('获取渠道供应商失败:', error);
        }
        break;
      default:
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
    }
  };

  // 渠道供应商类型变更处理
  const handleChannelProviderTypeChange = async (value: number, editValue?: any) => {
    switch (key) {
      case 'channel': {
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
        break;
      }
      case 'template':
      case 'file': {
        const params = {
          channelType: myRef?.current?.getFieldValue('channelType'),
          channelProviderType: value
        };
        if (params.channelType) {
          try {
            if (params.channelProviderType) {
              const messageResponse = await getMessageType(params);
              const appResponse = await getApp(params);
              setOptions((prev) => ({
                ...prev,
                messageTypeOptions: messageResponse || [],
                appOptions: appResponse || []
              }));
            } else {
              setOptions((prev) => ({
                ...prev,
                messageTypeOptions: [],
                appOptions: []
              }));
            }
          } catch (error) {
            console.error('获取消息类型和应用失败:', error);
          }
        }
        // if (editValue?.channelProviderType) {
        //   try {
        //     const messageResponse = await getMessageType(editValue);
        //     const appResponse = await getApp(editValue);
        //     setOptions((prev) => ({
        //       ...prev,
        //       messageTypeOptions: messageResponse || [],
        //       appOptions: appResponse || []
        //     }));
        //   } catch (error) {
        //     console.error('获取消息类型和应用失败:', error);
        //   }
        // }
        break;
      }
    }
  };

  // 关联模版搜索
  const handleTemplateSearch = async (value: string) => {
    if (value) {
      try {
        const response = await getTemplate({ templateName: value });
        setOptions((prev) => ({
          ...prev,
          templateOptions: response || []
        }));
      } catch (error) {
        console.error('获取模版失败:', error);
      }
    }
  };

  // 关联模版参数
  const handleTemplateChange = async (value: number) => {
    if (value) {
      try {
        const response = await getMessageParamByMessageType({ templateId: value });
        myRef?.current?.setFieldValue('taskMessageParam', JSON.parse(response));
        setJsonEditorKey?.((prev: number) => prev + 1);
      } catch (error) {
        console.error('获取模版失败:', error);
      }
    }
  };

  // 关联人群搜索
  const handlePeopleGroupSearch = async (value: string) => {
    if (value) {
      try {
        const response = await getPeopleGroup({ peopleGroupName: value });
        setOptions((prev) => ({
          ...prev,
          peopleGroupOptions: response || []
        }));
      } catch (error) {
        console.error('获取人群失败:', error);
      }
    }
  };

  // 获取渠道类型数据
  useEffect(() => {
    const usersType = key === 'file' ? -2 : -1;
    if (!options.channelTypeOptions.length) {
      getChannelType({ usersType }).then((res: Channel[]) => {
        setOptions((prev) => ({
          ...prev,
          channelTypeOptions: res
        }));
      });
    }
  }, []);

  return {
    options,
    handleUsersTypeChange,
    handleChannelTypeChange,
    handleChannelProviderTypeChange,
    handleTemplateSearch,
    handlePeopleGroupSearch,
    handleTemplateChange
  };
};
