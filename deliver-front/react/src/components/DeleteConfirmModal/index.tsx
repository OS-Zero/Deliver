import { message, Modal, ModalFuncProps } from 'antd';
import { ExclamationCircleOutlined } from '@ant-design/icons';

interface DeleteConfirmModalProps {
  title?: string;
  onConfirm: () => any;
  onCancel?: () => void;
  modalProps?: Partial<ModalFuncProps>;
}

const deleteConfirmModal = ({
  title = '确认删除吗？',
  onConfirm,
  onCancel,
  modalProps = {}
}: DeleteConfirmModalProps) => {
  Modal.confirm({
    title,
    icon: <ExclamationCircleOutlined />,
    okText: '确认',
    cancelText: '取消',
    async onOk() {
      try {
        await onConfirm();
        Modal.destroyAll();
        message.success('删除成功');
      } catch (error) {
        console.error('Delete operation failed:', error);
        throw error;
      }
    },
    onCancel,
    ...modalProps
  });
};

export default deleteConfirmModal;
