import './assets/css/main.css'
import './assets/css/tailwind.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config'
import ToastService from 'primevue/toastservice'
import Aura from '@primevue/themes/aura'
import dayjs from 'dayjs'
import router from '@/router'
import App from './App.vue'
import 'primeicons/primeicons.css'
import ConfirmationService from 'primevue/confirmationservice'

const app = createApp(App)
app.use(router)
app.use(PrimeVue, {
  theme: {
    unstyled: true,
    preset: Aura
  }
})
app.use(createPinia())
app.use(dayjs)
app.use(ConfirmationService)
app.use(ToastService)
app.mount('#app')
