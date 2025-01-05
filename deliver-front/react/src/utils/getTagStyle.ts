export const getColor = (num: number) => {
  const colors = ['green', 'blue', 'purple', 'cyan', 'orange', 'pink', 'red'];
  return colors[Number(num) % colors.length];
};

export const getImg = (num: number | string) => {
  const imgPaths = [
    { src: '', alt: '' },
    { src: '/assets/电话.png', alt: '电话' },
    { src: '/assets/短信.png', alt: '短信' },
    { src: '/assets/邮件.png', alt: '邮件' },
    { src: '/assets/钉钉.png', alt: '钉钉' },
    { src: '/assets/企业微信.png', alt: '企业微信' },
    { src: '/assets/飞书.png', alt: '飞书' }
  ];
  return imgPaths[Number(num) % imgPaths.length];
};
