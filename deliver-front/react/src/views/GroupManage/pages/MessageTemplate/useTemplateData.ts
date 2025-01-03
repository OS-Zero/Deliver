import { addMessageTemplate, deleteMessageTemplate, getTemplatePages } from '@/api/messageTemplate';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { useCallback } from 'react';
import { MessageTemplate } from './type';

const useTemplateData = () => {
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchTemplateData = async (params: { pageSize?: number; current?: number }) => {
    const { pageSize = 10, current = 1 } = params; // 提供默认值
    const res = await getTemplatePages({
      currentPage: current,
      pageSize: pageSize,
      templateName: 'String',
      usersType: 0,
      templateStatus: 0,
      startTime: 'yyyy-MM-dd HH:mm:ss',
      endTime: 'yyyy-MM-dd HH:mm:ss'
    });
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
    await addMessageTemplate(params);
  };

  /**
   * 删除数据
   * @param id
   */
  const deleteTemplateData = useCallback((ids: number[]) => {
    deleteConfirmModal({
      onConfirm: () => {
        deleteMessageTemplate({ ids });
        fetchTemplateData({});
      }
    });
  }, []);

  return {
    fetchTemplateData,
    deleteTemplateData,
    addTemplate,
  };
};

export default useTemplateData;
