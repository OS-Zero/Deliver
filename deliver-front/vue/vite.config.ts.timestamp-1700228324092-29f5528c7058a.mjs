// vite.config.ts
import { defineConfig } from "file:///D:/OneDrive/%E6%A1%8C%E9%9D%A2/%E5%BE%85%E5%AE%8C%E6%88%90%E7%9A%84%E9%A1%B9%E7%9B%AE/%E6%B6%88%E6%81%AF%E6%8E%A8%E9%80%81%E5%B9%B3%E5%8F%B0/deliver/deliver-front/vue/node_modules/vite/dist/node/index.js";
import eslint from "file:///D:/OneDrive/%E6%A1%8C%E9%9D%A2/%E5%BE%85%E5%AE%8C%E6%88%90%E7%9A%84%E9%A1%B9%E7%9B%AE/%E6%B6%88%E6%81%AF%E6%8E%A8%E9%80%81%E5%B9%B3%E5%8F%B0/deliver/deliver-front/vue/node_modules/vite-plugin-eslint/dist/index.mjs";
import vue from "file:///D:/OneDrive/%E6%A1%8C%E9%9D%A2/%E5%BE%85%E5%AE%8C%E6%88%90%E7%9A%84%E9%A1%B9%E7%9B%AE/%E6%B6%88%E6%81%AF%E6%8E%A8%E9%80%81%E5%B9%B3%E5%8F%B0/deliver/deliver-front/vue/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import { resolve } from "path";
import Components from "file:///D:/OneDrive/%E6%A1%8C%E9%9D%A2/%E5%BE%85%E5%AE%8C%E6%88%90%E7%9A%84%E9%A1%B9%E7%9B%AE/%E6%B6%88%E6%81%AF%E6%8E%A8%E9%80%81%E5%B9%B3%E5%8F%B0/deliver/deliver-front/vue/node_modules/unplugin-vue-components/dist/vite.mjs";
import { AntDesignVueResolver } from "file:///D:/OneDrive/%E6%A1%8C%E9%9D%A2/%E5%BE%85%E5%AE%8C%E6%88%90%E7%9A%84%E9%A1%B9%E7%9B%AE/%E6%B6%88%E6%81%AF%E6%8E%A8%E9%80%81%E5%B9%B3%E5%8F%B0/deliver/deliver-front/vue/node_modules/unplugin-vue-components/dist/resolvers.mjs";
import { prismjsPlugin } from "file:///D:/OneDrive/%E6%A1%8C%E9%9D%A2/%E5%BE%85%E5%AE%8C%E6%88%90%E7%9A%84%E9%A1%B9%E7%9B%AE/%E6%B6%88%E6%81%AF%E6%8E%A8%E9%80%81%E5%B9%B3%E5%8F%B0/deliver/deliver-front/vue/node_modules/vite-plugin-prismjs/dist/index.js";
var __vite_injected_original_dirname = "D:\\OneDrive\\\u684C\u9762\\\u5F85\u5B8C\u6210\u7684\u9879\u76EE\\\u6D88\u606F\u63A8\u9001\u5E73\u53F0\\deliver\\deliver-front\\vue";
var vite_config_default = defineConfig({
  server: {
    host: "localhost",
    port: 8080,
    // 端口
    proxy: {
      "/admin": {
        // 请求接口中要替换的标识
        target: "http://10.90.118.84:9090",
        // 代理地址
        changeOrigin: true,
        // 是否允许跨域
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
          importStyle: false
          // css in js
        })
      ]
    }),
    prismjsPlugin({
      languages: ["json", "js"],
      plugins: ["line-numbers", "copy-to-clipboard"],
      theme: "solarizedlight",
      css: true
    })
  ],
  resolve: {
    alias: {
      "@": resolve(__vite_injected_original_dirname, "./src"),
      "*": resolve("")
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxPbmVEcml2ZVxcXFxcdTY4NENcdTk3NjJcXFxcXHU1Rjg1XHU1QjhDXHU2MjEwXHU3Njg0XHU5ODc5XHU3NkVFXFxcXFx1NkQ4OFx1NjA2Rlx1NjNBOFx1OTAwMVx1NUU3M1x1NTNGMFxcXFxkZWxpdmVyXFxcXGRlbGl2ZXItZnJvbnRcXFxcdnVlXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxPbmVEcml2ZVxcXFxcdTY4NENcdTk3NjJcXFxcXHU1Rjg1XHU1QjhDXHU2MjEwXHU3Njg0XHU5ODc5XHU3NkVFXFxcXFx1NkQ4OFx1NjA2Rlx1NjNBOFx1OTAwMVx1NUU3M1x1NTNGMFxcXFxkZWxpdmVyXFxcXGRlbGl2ZXItZnJvbnRcXFxcdnVlXFxcXHZpdGUuY29uZmlnLnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9EOi9PbmVEcml2ZS8lRTYlQTElOEMlRTklOUQlQTIvJUU1JUJFJTg1JUU1JUFFJThDJUU2JTg4JTkwJUU3JTlBJTg0JUU5JUExJUI5JUU3JTlCJUFFLyVFNiVCNiU4OCVFNiU4MSVBRiVFNiU4RSVBOCVFOSU4MCU4MSVFNSVCOSVCMyVFNSU4RiVCMC9kZWxpdmVyL2RlbGl2ZXItZnJvbnQvdnVlL3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcclxuaW1wb3J0IGVzbGludCBmcm9tICd2aXRlLXBsdWdpbi1lc2xpbnQnIC8vIFx1NjVCMFx1NTg5RVxyXG5pbXBvcnQgdnVlIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZSdcclxuaW1wb3J0IHsgcmVzb2x2ZSB9IGZyb20gJ3BhdGgnXHJcbi8vIFx1NjMwOVx1OTcwMFx1NUJGQ1x1NTE2NWFudCBkZXNpZ24gdnVlXHJcbmltcG9ydCBDb21wb25lbnRzIGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3ZpdGUnXHJcbmltcG9ydCB7IEFudERlc2lnblZ1ZVJlc29sdmVyIH0gZnJvbSAndW5wbHVnaW4tdnVlLWNvbXBvbmVudHMvcmVzb2x2ZXJzJ1xyXG5pbXBvcnQgeyBwcmlzbWpzUGx1Z2luIH0gZnJvbSAndml0ZS1wbHVnaW4tcHJpc21qcydcclxuZXhwb3J0IGRlZmF1bHQgZGVmaW5lQ29uZmlnKHtcclxuICBzZXJ2ZXI6IHtcclxuICAgIGhvc3Q6ICdsb2NhbGhvc3QnLFxyXG4gICAgcG9ydDogODA4MCwgLy8gXHU3QUVGXHU1M0UzXHJcbiAgICBwcm94eToge1xyXG4gICAgICAnL2FkbWluJzoge1xyXG4gICAgICAgIC8vIFx1OEJGN1x1NkM0Mlx1NjNBNVx1NTNFM1x1NEUyRFx1ODk4MVx1NjZGRlx1NjM2Mlx1NzY4NFx1NjgwN1x1OEJDNlxyXG4gICAgICAgIHRhcmdldDogJ2h0dHA6Ly8xMC45MC4xMTguODQ6OTA5MCcsIC8vIFx1NEVFM1x1NzQwNlx1NTczMFx1NTc0MFxyXG4gICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSwgLy8gXHU2NjJGXHU1NDI2XHU1MTQxXHU4QkI4XHU4REU4XHU1N0RGXHJcbiAgICAgICAgc2VjdXJlOiB0cnVlXHJcbiAgICAgIH1cclxuICAgIH1cclxuICB9LFxyXG4gIHBsdWdpbnM6IFtcclxuICAgIHZ1ZSgpLFxyXG4gICAgZXNsaW50KCksXHJcbiAgICBDb21wb25lbnRzKHtcclxuICAgICAgcmVzb2x2ZXJzOiBbXHJcbiAgICAgICAgQW50RGVzaWduVnVlUmVzb2x2ZXIoe1xyXG4gICAgICAgICAgaW1wb3J0U3R5bGU6IGZhbHNlIC8vIGNzcyBpbiBqc1xyXG4gICAgICAgIH0pXHJcbiAgICAgIF1cclxuICAgIH0pLFxyXG4gICAgcHJpc21qc1BsdWdpbih7XHJcbiAgICAgIGxhbmd1YWdlczogWydqc29uJywgJ2pzJ10sXHJcbiAgICAgIHBsdWdpbnM6IFsnbGluZS1udW1iZXJzJywgJ2NvcHktdG8tY2xpcGJvYXJkJ10sXHJcbiAgICAgIHRoZW1lOiAnc29sYXJpemVkbGlnaHQnLFxyXG4gICAgICBjc3M6IHRydWVcclxuICAgIH0pXHJcbiAgXSxcclxuICByZXNvbHZlOiB7XHJcbiAgICBhbGlhczoge1xyXG4gICAgICAnQCc6IHJlc29sdmUoX19kaXJuYW1lLCAnLi9zcmMnKSxcclxuICAgICAgJyonOiByZXNvbHZlKCcnKVxyXG4gICAgfVxyXG4gIH1cclxufSlcclxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUFvZCxTQUFTLG9CQUFvQjtBQUNqZixPQUFPLFlBQVk7QUFDbkIsT0FBTyxTQUFTO0FBQ2hCLFNBQVMsZUFBZTtBQUV4QixPQUFPLGdCQUFnQjtBQUN2QixTQUFTLDRCQUE0QjtBQUNyQyxTQUFTLHFCQUFxQjtBQVA5QixJQUFNLG1DQUFtQztBQVF6QyxJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixRQUFRO0FBQUEsSUFDTixNQUFNO0FBQUEsSUFDTixNQUFNO0FBQUE7QUFBQSxJQUNOLE9BQU87QUFBQSxNQUNMLFVBQVU7QUFBQTtBQUFBLFFBRVIsUUFBUTtBQUFBO0FBQUEsUUFDUixjQUFjO0FBQUE7QUFBQSxRQUNkLFFBQVE7QUFBQSxNQUNWO0FBQUEsSUFDRjtBQUFBLEVBQ0Y7QUFBQSxFQUNBLFNBQVM7QUFBQSxJQUNQLElBQUk7QUFBQSxJQUNKLE9BQU87QUFBQSxJQUNQLFdBQVc7QUFBQSxNQUNULFdBQVc7QUFBQSxRQUNULHFCQUFxQjtBQUFBLFVBQ25CLGFBQWE7QUFBQTtBQUFBLFFBQ2YsQ0FBQztBQUFBLE1BQ0g7QUFBQSxJQUNGLENBQUM7QUFBQSxJQUNELGNBQWM7QUFBQSxNQUNaLFdBQVcsQ0FBQyxRQUFRLElBQUk7QUFBQSxNQUN4QixTQUFTLENBQUMsZ0JBQWdCLG1CQUFtQjtBQUFBLE1BQzdDLE9BQU87QUFBQSxNQUNQLEtBQUs7QUFBQSxJQUNQLENBQUM7QUFBQSxFQUNIO0FBQUEsRUFDQSxTQUFTO0FBQUEsSUFDUCxPQUFPO0FBQUEsTUFDTCxLQUFLLFFBQVEsa0NBQVcsT0FBTztBQUFBLE1BQy9CLEtBQUssUUFBUSxFQUFFO0FBQUEsSUFDakI7QUFBQSxFQUNGO0FBQ0YsQ0FBQzsiLAogICJuYW1lcyI6IFtdCn0K
