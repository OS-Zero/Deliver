import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

export default defineConfig({
  plugins: [react()],
  // 代理配置
  server: {
    host: 'localhost',
    port: 8080, // 端口
    proxy: {
        '/backend': {
            target: 'http://localhost:9090/',
            changeOrigin: true,
            secure: false,
        }
    }
  },
  resolve: {
    alias: {
        '@': resolve(__dirname, './src'),
        '*': resolve('')
    },
    // 删除导入后缀
    extensions: [
        ".mjs",
        ".js",
        ".ts",
        ".jsx",
        ".tsx",
        ".json",
    ],
  }
})
