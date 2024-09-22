import { createRouter, createWebHistory } from 'vue-router'

const loadView = (path, view) => {
  return () => import(`@/${path}/${view}.vue`)
}

const routes = [
  {
    path: '/',
    name: 'IndexPage',
    component: loadView('views', 'IndexPage'),
    meta: { id: 'IndexPage' }
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: loadView('views', 'LoginPage'),
    meta: { id: 'LoginPage' }
  },
  {
    path: '/error',
    name: 'ErrorPage',
    component: loadView('views', 'ErrorPage'),
    meta: { id: 'ErrorPage' }
  },
  {
    path: '/board',
    name: 'MainPage',
    component: loadView('views', 'MainPage'),
    meta: { id: 'MainPage' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
