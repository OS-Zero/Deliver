import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

export default defineConfig({
  plugins: [react()],
  // 代理配置
  server: {
	host: 'localhost',
	port: 9090, // 端口
	proxy: {
		'/backend': {
			// 请求接口中要替换的标识
			target: 'http://localhost:6060', // 代理地址
			changeOrigin: true, // 是否允许跨域
			secure: false
		}
	}
  },
  resolve: {
	alias: {
		'@': resolve(__dirname, './src'),
		'*': resolve('')
	}
  }
})
