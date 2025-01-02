import { createApp } from 'vue';
import './styles/index.scss';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import './permisson.ts';
import 'jsoneditor';

const pinia = createPinia();

createApp(App).use(router).use(pinia).mount('#app');
