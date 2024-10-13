import { createRouter, createWebHistory } from 'vue-router'

const loadView = (path, view) => {
  return () => import(`@/${path}/${view}.vue`)
}

const loadSubView = (path, dir, view) => {
  return () => import(`@/${path}/${dir}/${view}.vue`)
}

const boardChildren = [
  {
    path: '/board/main',
    name: 'BoardMainPage',
    component: loadView('pages', 'BoardMainPage'),
    meta: { id: 'BoardMainPage' }
  },
  {
    path: '/board/notice',
    name: 'NoticePage',
    component: loadView('pages', 'NoticePage'),
    meta: { id: 'NoticePage' }
  },
  {
    path: '/board/notice/:noticeGuid',
    name: 'NoticeViewPage',
    component: loadSubView('pages', 'notice', 'NoticeViewPage'),
    meta: { id: 'NoticeViewPage' },
    props: true
  },
  {
    path: '/board/notice/create',
    name: 'NoticeCreatePage',
    component: loadSubView('pages', 'notice', 'NoticeCreatePage'),
    meta: { id: 'NoticeCreatePage' }
  },
  {
    path: '/board/notice/edit/:noticeGuid',
    name: 'NoticeEditPage',
    component: loadSubView('pages', 'notice', 'NoticeEditPage'),
    meta: { id: 'NoticeEditPage' },
    props: true
  },
  {
    path: '/board/tech',
    name: 'DevelopmentAndStackPage',
    component: loadView('pages', 'DevelopmentAndStackPage'),
    meta: { id: 'DevelopmentAndStackPageError' }
  },
  {
    path: '/board/tech/:boardGuid',
    name: 'DevAndStackViewPage',
    component: loadSubView('pages', 'developmentandstack', 'DevAndStackViewPage'),
    meta: { id: 'DevAndStackViewPage' },
    props: true
  },
  {
    path: '/board/tech/create',
    name: 'DevAndStackCreatePage',
    component: loadSubView('pages', 'developmentandstack', 'DevAndStackCreatePage'),
    meta: { id: 'DevAndStackCreatePage' }
  },
  {
    path: '/board/tech/edit/:boardGuid',
    name: 'DevAndStackEditPage',
    component: loadSubView('pages', 'developmentandstack', 'DevAndStackEditPage'),
    meta: { id: 'DevAndStackEditPage' },
    props: true
  },
  {
    path: '/board/other',
    name: 'OtherBoardPage',
    component: loadView('pages', 'OtherBoardPage'),
    meta: { id: 'OtherBoardPageError' }
  },
  {
    path: '/board/qna',
    name: 'QnAPage',
    component: loadView('pages', 'QnAPage'),
    meta: { id: 'QnAPageError' }
  },
  {
    path: '/board/my/information',
    name: 'MyPage',
    component: loadView('pages', 'MyPage'),
    meta: { id: 'MyPageError' }
  }
]

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
    path: '/reset/password',
    name: 'ResetPasswordPage',
    component: loadView('views', 'ResetPasswordPage'),
    meta: { id: 'ResetPasswordPage' }
  },
  {
    path: '/sign/up',
    name: 'SignUpPage',
    component: loadView('views', 'SignUpPage'),
    meta: { id: 'SignUpPage' }
  },
  {
    path: '/sign/out',
    name: 'SignOutPage',
    component: loadView('views', 'SignOutPage'),
    meta: { id: 'SignOutPage' }
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
    meta: { id: 'MainPage' },
    children: boardChildren,
    redirect: '/board/main'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
