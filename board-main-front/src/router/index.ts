import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const loadPage = (path: string, view: string) => {
  return () => import(`@/${path}/${view}.vue`)
}

const loadSubPage = (path: string, dir: string, view: string) => {
  return () => import(`@/${path}/${dir}/${view}.vue`)
}

const announceChild: Array<RouteRecordRaw> = [
  { path: '/board/announce/main', name: 'AnnounceMain', component: loadSubPage('pages', 'announce', 'AnnounceMain'), meta: { id: 'AnnounceMain' } },
  { path: '/board/announce/regist', name: 'AnnounceRegist', component: loadSubPage('pages', 'announce', 'AnnounceRegist'), meta: { id: 'AnnounceRegist' } },
  { path: '/board/announce/update', name: 'AnnounceUpdate', component: loadSubPage('pages', 'announce', 'AnnounceUpdate'), meta: { id: 'AnnounceUpdate' } },
  { path: '/board/announce/info', name: 'AnnounceInfo', component: loadSubPage('pages', 'announce', 'AnnounceInfo'), meta: { id: 'AnnounceInfo' } }
]

const developmentChild: Array<RouteRecordRaw> = [
  { path: '/board/development/main', name: 'DevelopmentMain', component: loadSubPage('pages', 'board/development', 'DevelopmentMain'), meta: { id: 'DevelopmentMain' } }
]

const stackChild: Array<RouteRecordRaw> = [
  { path: '/board/stack/main', name: 'StackMain', component: loadSubPage('pages', 'board/stack', 'StackMain'), meta: { id: 'StackMain' } }
]

const freeChild: Array<RouteRecordRaw> = [
  { path: '/board/free/main', name: 'FreeMain', component: loadSubPage('pages', 'board/free', 'FreeMain'), meta: { id: 'FreeMain' } }
]

const qnaChild: Array<RouteRecordRaw> = [
  { path: '/board/qna/main', name: 'QnAMain', component: loadSubPage('pages', 'qna', 'QnAMain'), meta: { id: 'QnAMain' } }
]

const boardChild: Array<RouteRecordRaw> = [
  { path: '/board/announce', name: 'AnnounceAdmin', children: announceChild, redirect: '/board/announce/main' },

  { path: '/board/development', name: 'DevelopmentAdmin', children: developmentChild, redirect: '/board/development/main' },
  { path: '/board/stack', name: 'StackAdmin', children: stackChild, redirect: '/board/stack/main' },
  { path: '/board/free', name: 'FreeAdmin', children: freeChild, redirect: '/board/free/main' },

  { path: '/board/qna', name: 'QnAAdmin', children: qnaChild, redirect: '/board/qna/main' }
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
