import { defineConfig } from 'vite'
import eslint from 'vite-plugin-eslint' // 新增
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
// 按需导入ant design vue
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
export default defineConfig({
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
