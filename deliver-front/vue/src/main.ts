import { createApp } from 'vue';
import './styles/index.scss';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import './permisson.ts';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
dayjs.locale('zh-cn');

const pinia = createPinia();

createApp(App).use(router).use(pinia).mount('#app');
