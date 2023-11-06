import { defineConfig } from 'vite'
import eslint from 'vite-plugin-eslint' // 新增
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
// 按需导入ant design vue
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
export default defineConfig({
  server: {
    host: 'localhost',
    port: 8080, // 端口
    proxy: {
      '/api': {
        // 请求接口中要替换的标识
        target: '10.90.2.179', // 代理地址
        changeOrigin: true, // 是否允许跨域
        secure: true,
        rewrite: path => path.replace(/^\/api/, '') // api标志替换为''
      }
    }
  },
  plugins: [
    vue(),
    eslint(),
    Components({
      resolvers: [
        AntDesignVueResolver({
          importStyle: false // css in js
        })
      ]
    })
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'),
      '*': resolve('')
    }
  }
})
