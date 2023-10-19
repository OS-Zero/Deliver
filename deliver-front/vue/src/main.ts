import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import '@/style/global.less';
import i18n from '@/locales/useI18n'
import 'virtual:svg-icons-register';
import setupDefaultSetting from '@/utils/setupDefaultSetting'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Antd from 'ant-design-vue';
// 用来切换主题
import 'ant-design-vue/dist/antd.variable.min.css'
import "@/utils/default-passive-events"

const app = createApp(App)
app.use(Antd)
app.use(router)
app.use(store)
app.use(i18n)
app.use(ElementPlus)
app.mount('#app');

setupDefaultSetting()

window.env = import.meta.env.MODE
