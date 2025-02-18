import {
  saveMessageTemplate,
  deleteMessageTemplate,
  getTemplatePages,
  updateMessageTemplateStatus,
  updateMessageTemplate
} from '@/api/messageTemplate';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { MutableRefObject, useCallback } from 'react';
import { MessageTemplate, SearchMessage } from './type';
import { transformParams } from '@/utils/omitProperty';
import { ActionType } from '@ant-design/pro-components';

const useTemplateData = (props: { proTableRef: MutableRefObject<ActionType | undefined> }) => {
  const { proTableRef } = props;

  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchTemplateData = async (filter: SearchMessage) => {
    const params = transformParams(filter);
    const res = await getTemplatePages(params);
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
  const saveTemplate = async (params: MessageTemplate) =>
    params?.templateId ? await updateMessageTemplate(params) : await saveMessageTemplate(params);

  /**
   * 更新状态
   * @param data
   */
  const changeStatus = async (templateId: number, templateStatus: number) => {
    await updateMessageTemplateStatus({ templateId, templateStatus });
  };

  /**
   * 删除数据
   * @param id
   */
  const resetProTable = () => {
    (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
  };

  const deleteTemplateData = useCallback((ids: number[], isBatchDelete: boolean = false) => {
    const confirmContent = isBatchDelete ? '确认批量删除模版?' : '确认删除该模版?';
    deleteConfirmModal({
      title: confirmContent,
      onConfirm: () => {
        deleteMessageTemplate({ ids });
        resetProTable();
      }
    });
  }, []);

  return {
    fetchTemplateData,
    deleteTemplateData,
    saveTemplate,
    changeStatus
  };
};

export default useTemplateData;
