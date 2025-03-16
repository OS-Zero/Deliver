import { createApp } from 'vue';
import './styles/index.scss';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
dayjs.locale('zh-cn');
import 'highlight.js/styles/stackoverflow-light.css';
import hljs from 'highlight.js/lib/core';
import hljsVuePlugin from '@highlightjs/vue-plugin';
import json from 'highlight.js/lib/languages/json';
if (import.meta.env.MODE === 'test') {
	import('./mock/index.ts');
}
hljs.registerLanguage('json', json);
const pinia = createPinia();

createApp(App).use(router).use(pinia).use(hljsVuePlugin).mount('#app');
