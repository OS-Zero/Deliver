import {
  saveMessageTemplate,
  deleteMessageTemplate,
  getTemplatePages,
  updateMessageTemplateStatus,
  updateMessageTemplate
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
  const fetchTemplateData = async ({
    current,
    ...restParams
  }: SearchMessage & { current: number }) => {
    const params: SearchMessage & { currentPage: number } = {
      ...restParams,
      currentPage: current || 1
    };
    setCurrentParams(params);
    const res = await getTemplatePages(params);
    // debugger;
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
  const addTemplate = async (params: MessageTemplate) =>
    params?.templateId ? await updateMessageTemplate(params) : await saveMessageTemplate(params);

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
