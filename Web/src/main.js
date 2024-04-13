import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import './assets/main.css'

import { createApp } from 'vue'

import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'



const app = createApp(App)

app.use(Antd)
app.use(VueCal)
app.use(createPinia())
app.use(router)

app.mount('#app')
