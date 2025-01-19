// 提取处理字段值的通用函数
export const handleFieldValue = (
  value: string,
  optionsList: any,
  nameProp: string,
  keyProp: string
): number | string => {
  if (typeof value === 'string') {
    return optionsList.find((item: any) => item[nameProp] === value)?.[keyProp] || value;
  }
  return value;
};

// 处理重置的表单变化
export const handleValueChange = (key, ref) => {
  if (!key?.channelType) {
    ref?.current?.resetFields(['channelProviderType', 'platformFileType', 'appId']);
  } else if (!key?.channelProviderType) {
    ref?.current?.resetFields(['platformFileType', 'appId']);
  }
};
