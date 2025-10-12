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
  { path: '/board/development/main', name: 'DevelopmentMain', component: loadSubPage('pages', 'post/development', 'DevelopmentMain'), meta: { id: 'DevelopmentMain' } },
  { path: '/board/development/regist', name: 'DevelopmentRegist', component: loadSubPage('pages', 'post/development', 'DevelopmentRegist'), meta: { id: 'DevelopmentRegist' } },
  { path: '/board/development/update', name: 'DevelopmentUpdate', component: loadSubPage('pages', 'post/development', 'DevelopmentUpdate'), meta: { id: 'DevelopmentUpdate' } },
  { path: '/board/development/info', name: 'DevelopmentInfo', component: loadSubPage('pages', 'post/development', 'DevelopmentInfo'), meta: { id: 'DevelopmentInfo' } }
]

const stackChild: Array<RouteRecordRaw> = [
  { path: '/board/stack/main', name: 'StackMain', component: loadSubPage('pages', 'post/stack', 'StackMain'), meta: { id: 'StackMain' } },
  { path: '/board/stack/regist', name: 'StackRegist', component: loadSubPage('pages', 'post/stack', 'StackRegist'), meta: { id: 'StackRegist' } },
  { path: '/board/stack/update', name: 'StackUpdate', component: loadSubPage('pages', 'post/stack', 'StackUpdate'), meta: { id: 'StackUpdate' } },
  { path: '/board/stack/info', name: 'StackInfo', component: loadSubPage('pages', 'post/stack', 'StackInfo'), meta: { id: 'StackInfo' } }
]

const freeChild: Array<RouteRecordRaw> = [
  { path: '/board/free/main', name: 'FreeMain', component: loadSubPage('pages', 'post/free', 'FreeMain'), meta: { id: 'FreeMain' } },
  { path: '/board/free/regist', name: 'FreeRegist', component: loadSubPage('pages', 'post/free', 'FreeRegist'), meta: { id: 'FreeRegist' } },
  { path: '/board/free/update', name: 'FreeUpdate', component: loadSubPage('pages', 'post/free', 'FreeUpdate'), meta: { id: 'FreeUpdate' } },
  { path: '/board/free/info', name: 'FreeInfo', component: loadSubPage('pages', 'post/free', 'FreeInfo'), meta: { id: 'FreeInfo' } }
]

const qnaChild: Array<RouteRecordRaw> = [
  { path: '/board/qna/main', name: 'QnAMain', component: loadSubPage('pages', 'qna', 'QnAMain'), meta: { id: 'QnAMain' } },
  { path: '/board/qna/regist', name: 'QnARegist', component: loadSubPage('pages', 'qna', 'QnARegist'), meta: { id: 'QnARegist' } },
  { path: '/board/qna/update', name: 'QnAUpdate', component: loadSubPage('pages', 'qna', 'QnAUpdate'), meta: { id: 'QnAUpdate' } },
  { path: '/board/qna/info', name: 'QnAInfo', component: loadSubPage('pages', 'qna', 'QnAInfo'), meta: { id: 'QnAInfo' } },
]

const userChild: Array<RouteRecordRaw> = [
  { path: '/board/user/main', name: 'UserMain', component: loadSubPage('pages', 'user', 'UserMain'), meta: { id: 'UserMain' } },
]

const systemChild: Array<RouteRecordRaw> = [
  { path: '/board/system/member', name: 'MemberConfig', component: loadSubPage('pages', 'system', 'MemberConfig'), meta: { id: 'MemberConfig' } },
]

const boardChild: Array<RouteRecordRaw> = [
  { path: '/board/announce', name: 'AnnounceAdmin', children: announceChild, redirect: '/board/announce/main' },

  { path: '/board/development', name: 'DevelopmentAdmin', children: developmentChild, redirect: '/board/development/main' },
  { path: '/board/stack', name: 'StackAdmin', children: stackChild, redirect: '/board/stack/main' },
  { path: '/board/free', name: 'FreeAdmin', children: freeChild, redirect: '/board/free/main' },

  { path: '/board/qna', name: 'QnAAdmin', children: qnaChild, redirect: '/board/qna/main' },

  { path: '/board/user', name: 'UserAdmin', children: userChild, redirect: 'board/user/main' },

  { path: '/board/system', name: 'SystemAdmin', children: systemChild, redirect: 'board/system/admin' }
]

const routes: Array<RouteRecordRaw> = [
  { path: '/', name: 'IndexPage', component: loadPage('views', 'IndexPage'), meta: { id: 'IndexPage' } },
  { path: '/login', name: 'LoginPage', component: loadPage('views', 'LoginPage'), meta: { id: 'LoginPage' } },
  { path: '/signup', name: 'SignUpPage', component: loadPage('views', 'SignUpPage'), meta: { id: 'SignUpPage' } },
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
