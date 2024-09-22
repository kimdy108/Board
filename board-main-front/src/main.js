import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config'
import Aura from '@primevue/themes/aura'
import dayjs from './plugins/dayjs'
import router from '@/router'
import App from './App.vue'

const app = createApp(App)
app.mount('#app')
app.use(router)
app.use(PrimeVue, {
  theme: {
    preset: Aura
  }
})
app.use(createPinia())
app.use(dayjs)
