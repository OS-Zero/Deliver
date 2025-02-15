import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

export default defineConfig({
  plugins: [react()],
  // 代理配置
  server: {
	host: 'localhost',
	port: 8080, // 端口
  },
  resolve: {
	alias: {
		'@': resolve(__dirname, './src'),
		'*': resolve('')
	}
  }
})
