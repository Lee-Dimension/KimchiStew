const userRoutes = [
  // 메인(로그인)
  {
    path: '/',
    name: 'Login',
    component: () => import('@/views/user/Login.vue'),
  },
  // 사용자 메인
  {
    path: '/main',
    name: 'Main',
    component: () => import('@/views/user/Main.vue'),
  },
  // 안전교육
  {
    path: '/safe',
    name: 'Safe',
    component: () => import('@/views/user/Safety.vue'),
  },
  // 안전교육 - 교육자료
  {
    path: '/education',
    name: 'Education',
    component: () => import('@/views/user/Education.vue'),
  },
  // 사고조치
  {
    path: '/clear',
    name: 'Clear',
    component: () => import('@/views/user/Clear.vue'),
  },
]

export default userRoutes
