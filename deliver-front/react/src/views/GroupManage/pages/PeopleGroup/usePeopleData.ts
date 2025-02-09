import {
  getPeopleGroup,
  savePeopleGroup,
  updatePeopleGroup,
  deletePeopleGroup
} from '@/api/peopleGroup';
import deleteConfirmModal from '@/components/DeleteConfirmModal';
import { MutableRefObject, useCallback } from 'react';
import { transformParams } from '@/utils/omitProperty';
import { ActionType } from '@ant-design/pro-components';
import { SearchParams, PeopleGroupForm } from './type';

const usePeopleData = (props: { proTableRef: MutableRefObject<ActionType | undefined> }) => {
  const { proTableRef } = props;
  /**
   * 搜索请求表单数据
   * @param data
   */
  const fetchPeopleData = async (filter: SearchParams) => {
    const params = transformParams(filter);
    const res = await getPeopleGroup(params);
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
  const savePeopleData = async (params: PeopleGroupForm) =>
    params?.peopleGroupId ? await updatePeopleGroup(params) : await savePeopleGroup(params);

  /**
   * 删除
   * @param id
   */
  const resetProTable = () => {
    (proTableRef as MutableRefObject<ActionType>)?.current?.reset?.();
  };

  const deletePeopleData = useCallback((ids: number[], isBatchDelete: boolean = false) => {
    const confirmContent = isBatchDelete ? '确认批量删除人群?' : '确认删除该人群?';
    deleteConfirmModal({
      title: confirmContent,
      onConfirm: () => {
        deletePeopleGroup({ ids });
        resetProTable();
      }
    });
  }, []);

  return {
    fetchPeopleData,
    deletePeopleData,
    savePeopleData
  };
};

export default usePeopleData;
