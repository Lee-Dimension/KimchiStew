const adminRoutes = [
  // 관리자 로그인 페이지
  {
    path: '/adminlogin',
    name: 'AdminLogin',
    component: () => import('@/views/admin/AdminLogin.vue'),
  },

  // 관리자 메인 페이지
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/admin/Admin.vue'),
  },

  // 관리자 현장 추가
  {
    path: '/adminplus',
    name: 'AdminPlus',
    component: () => import('@/views/admin/AdminPlus.vue'),
  },

  // 관리자 명단 관리
  {
    path: '/adminedit',
    name: 'AdminEditList',
    component: () => import('@/views/admin/AdminEditList.vue'),
  },
]

export default adminRoutes
