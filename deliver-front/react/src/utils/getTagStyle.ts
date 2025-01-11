export const getColor = (num: number) => {
  const colors = ['green', 'blue', 'purple', 'cyan', 'orange', 'pink', 'red'];
  return colors[Number(num) % colors.length];
};
