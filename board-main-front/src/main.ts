import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'

import '@/assets/css/main.css'
import '@/assets/css/tailwind.css'

import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura'

import 'primeicons/primeicons.css'
import ToastService from 'primevue/toastservice';

import PerfectScrollbar from 'vue3-perfect-scrollbar'
import 'vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css'

import dayjs from 'dayjs'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(PerfectScrollbar)
app.use(PrimeVue, {
  theme: {
    preset: Aura,
    options: {
        darkModeSelector: false
    }
  }
})
app.use(ToastService)
app.config.globalProperties.$_dayjs = dayjs
app.mount('#app')

declare module 'vue' {
  interface ComponentCustomProperties {
    $_dayjs: any
  }
}