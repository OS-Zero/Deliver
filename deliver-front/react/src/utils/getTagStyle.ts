export const getColor = (data: number | string) => {
  const colors = ['green', 'blue', 'purple', 'cyan', 'orange', 'pink', 'red'];
  let index = 0;
  if (typeof data === 'number') {
    index = data % colors.length;
  } else if (typeof data === 'string') {
    for (const char of data) {
      index += char.charCodeAt(0);
    }
    index %= colors.length;
  }
  return colors[index];
};
