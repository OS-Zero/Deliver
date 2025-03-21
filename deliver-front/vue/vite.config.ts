import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { resolve } from 'path';
// 按需导入ant design vue
import Components from 'unplugin-vue-components/vite';
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers';
import { viteCommonjs } from '@originjs/vite-plugin-commonjs';
export default defineConfig({
	server: {
		host: 'localhost',
		port: 8080, // 端口
		proxy: {
			'/backend': {
				// 请求接口中要替换的标识
				target: 'http://localhost:9090/', // 代理地址
				changeOrigin: true, // 是否允许跨域
				secure: true,
			},
		},
	},
	plugins: [
		vue(),
		Components({
			resolvers: [
				AntDesignVueResolver({
					importStyle: false, // css in js
				}),
			],
		}),
		viteCommonjs(),
	],
	resolve: {
		alias: {
			'@': resolve(__dirname, './src'),
		},
	},
	css: {
		preprocessorOptions: {
			scss: {
				additionalData: `
				@import '@/styles/variable.scss';
				@import "@/styles/mixin.scss";`,
			},
		},
	},
});
