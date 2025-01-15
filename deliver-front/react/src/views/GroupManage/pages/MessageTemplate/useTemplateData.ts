import {
  saveMessageTemplate,
  deleteMessageTemplate,
  getTemplatePages,
  updateMessageTemplateStatus,
  updateMessageTemplate
} from '@/api/messageTemplate';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { MutableRefObject, useCallback, useEffect } from 'react';
import { MessageTemplate, SearchMessage } from './type';
import { transformParams } from '@/utils/omitProperty';
import { ActionType } from '@ant-design/pro-components';
import { useNavigate } from 'react-router-dom';

const useTemplateData = (props: { proTableRef: MutableRefObject<ActionType | undefined> }) => {
  const { proTableRef } = props;
  const navigator = useNavigate();

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
   * 新增模版
   * @param data
   */
  const saveTemplate = async (params: MessageTemplate) =>
    params?.templateId ? await updateMessageTemplate(params) : await saveMessageTemplate(params);

  /**
   * 更新模板状态
   * @param data
   */
  const changeStatus = async (templateId: number, templateStatus: number) => {
    await updateMessageTemplateStatus({ templateId, templateStatus });
  };

  /**
   * 删除数据
   * @param id
   */
  const deleteTemplateData = useCallback((ids: number[]) => {
    deleteConfirmModal({
      onConfirm: () => {
        deleteMessageTemplate({ ids });
        (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
      }
    });
  }, []);

  useEffect(() => {
    if (!localStorage.getItem('group_id')) {
      navigator('/groupManage');
    }
  }, []);

  return {
    fetchTemplateData,
    deleteTemplateData,
    saveTemplate,
    changeStatus
  };
};

export default useTemplateData;
