import { message } from 'ant-design-vue';
import { ref } from 'vue';

export function useClipboard() {
	const text = ref(''); // 存储剪贴板内容
	const isSupported = 'clipboard' in navigator; // 检查浏览器是否支持 Clipboard API

	// 复制文本到剪贴板
	async function copy(value: string) {
		if (isSupported && value) {
			try {
				await navigator.clipboard.writeText(value);
				text.value = value;
				message.success('复制成功');
				return true;
			} catch (error) {
				message.error('复制失败');
				return false;
			}
		}
		return false;
	}

	// 从剪贴板读取文本
	async function read() {
		if (isSupported) {
			try {
				const clipboardText = await navigator.clipboard.readText();
				text.value = clipboardText;
				return clipboardText;
			} catch (error) {
				message.error('读取失败');
				return '';
			}
		}
		return '';
	}

	return { text, isSupported, copy, read };
}
