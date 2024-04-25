import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import './assets/main.css'
import App from './App.vue'
import router from './router'


import { createApp } from 'vue'
import { createPinia } from 'pinia'
const pinia = createPinia()



const app = createApp(App)
app.use(Antd)
app.use(VueCal)
app.use(pinia)
app.use(router)

app.mount('#app')
