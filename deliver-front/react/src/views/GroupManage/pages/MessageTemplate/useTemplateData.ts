import {
  saveMessageTemplate,
  deleteMessageTemplate,
  getTemplatePages,
  updateMessageTemplateStatus
} from '@/api/messageTemplate';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { useCallback, useState } from 'react';
import { MessageTemplate, SearchMessage } from './type';

const useTemplateData = () => {
  const [currentParams, setCurrentParams] = useState({ currentPage: 1, pageSize: 10 });
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchTemplateData = async (params: SearchMessage) => {
    setCurrentParams(params);
    const res = await getTemplatePages(params);
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
  const addTemplate = async (params: MessageTemplate) => {
    console.log(params);
    await saveMessageTemplate(params);
  };

  /**
   * 更新模板状态
   * @param data
   */
  const changeStatus = async (templateId: number, templateStatus: number) => {
    await updateMessageTemplateStatus({ templateId, templateStatus });
    // await fetchTemplateData(currentParams);
  };

  /**
   * 删除数据
   * @param id
   */
  const deleteTemplateData = useCallback((ids: number[]) => {
    deleteConfirmModal({
      onConfirm: () => {
        deleteMessageTemplate({ ids });
        fetchTemplateData(currentParams);
      }
    });
  }, []);

  return {
    fetchTemplateData,
    deleteTemplateData,
    addTemplate,
    changeStatus
  };
};

export default useTemplateData;
