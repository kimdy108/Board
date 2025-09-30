import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const loadPage = (path: string, view: string) => {
  return () => import(`@/${path}/${view}.vue`)
}

const loadSubPage = (path: string, dir: string, view: string) => {
  return () => import(`@/${path}/${dir}/${view}.vue`)
}

const boardChild: Array<RouteRecordRaw> = [
  { path: '/board/announce', name: 'AnnounceAdmin', component: loadSubPage('pages', 'announce', 'AnnounceAdmin'), meta: { id: 'AnnounceAdmin' }},

  { path: '/board/development', name: 'DevelopmentAdmin', component: loadSubPage('pages', 'board/development', 'DevelopmentAdmin'), meta: { id: 'DevelopmentAdmin' }},
  { path: '/board/stack', name: 'StackAdmin', component: loadSubPage('pages', 'board/stack', 'StackAdmin'), meta: { id: 'StackAdmin' }},
  { path: '/board/free', name: 'FreeAdmin', component: loadSubPage('pages', 'board/free', 'FreeAdmin'), meta: { id: 'FreeAdmin' }},
]

const routes: Array<RouteRecordRaw> = [
  { path: '/', name: 'IndexPage', component: loadPage('views', 'IndexPage'), meta: { id: 'IndexPage' } },
  { path: '/login', name: 'LoginPage', component: loadPage('views', 'LoginPage'), meta: { id: 'LoginPage' } },
  { path: '/main', name: 'MainPage', component: loadPage('views', 'MainPage'), meta: { id: 'MainPage' }, children: boardChild, redirect: '/board/announce' },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    document.getElementById('board-main')?.scrollTo(0,0)
  }
})

export default router
