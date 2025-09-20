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

  // 관리자 교육 추가
  {
    path: '/adminAddEdu',
    name: 'AdminAddEdu',
    component: () => import('@/views/admin/AdminAddEdu.vue'),
  },
]

export default adminRoutes
