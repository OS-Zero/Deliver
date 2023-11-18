import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import router from './router'
import { createPinia } from 'pinia'
import 'jsoneditor'

import './permisson'

const pinia = createPinia()

createApp(App).use(router).use(pinia).mount('#app')
