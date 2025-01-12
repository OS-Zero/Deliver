export const getColor = (data: number | string) => {
	const colors = ['green', 'blue', 'purple', 'cyan', 'orange', 'pink', 'red'];
	let index = 0;
	if (typeof data === 'number') {
		index = data % colors.length;
	} else if (typeof data === 'string') {
		for (let char of data) {
			index += char.charCodeAt(0);
		}
		index %= colors.length;
	}
	return colors[index];
};

export const getImg = (num: number | string) => {
	const imgPaths = [
		{ src: '', alt: '' },
		{ src: '/assets/电话.png', alt: '电话' },
		{ src: '/assets/短信.png', alt: '短信' },
		{ src: '/assets/邮件.png', alt: '邮件' },
		{ src: '/assets/钉钉.png', alt: '钉钉' },
		{ src: '/assets/企业微信.png', alt: '企业微信' },
		{ src: '/assets/飞书.png', alt: '飞书' },
	];
	return imgPaths[Number(num) % imgPaths.length];
};
