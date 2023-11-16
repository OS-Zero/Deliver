import { defineConfig } from 'vite'
import eslint from 'vite-plugin-eslint' // 新增
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
// 按需导入ant design vue
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
import { prismjsPlugin } from 'vite-plugin-prismjs'
export default defineConfig({
  server: {
    host: 'localhost',
    port: 8080, // 端口
    proxy: {
      '/admin': {
        // 请求接口中要替换的标识
        target: 'http://10.90.118.84:9090', // 代理地址
        changeOrigin: true, // 是否允许跨域
        secure: true
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
    }),
    prismjsPlugin({
      languages: ['json', 'js'], //  languages: 'all',
      plugins: ['line-numbers'], // 配置显示行号插件
      theme: 'solarizedlight', // 主题名称
      css: true
    })
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'),
      '*': resolve('')
    }
  }
})
