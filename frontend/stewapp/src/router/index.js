import { createRouter, createWebHistory } from 'vue-router'
import clearRoutes from './clear.js'
import userRoutes from './user.js'
import adminRoutes from './admin.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 사용자 라우터 / user.js
    ...userRoutes,

    // 사용자 교육 상세 페이지 / clear.js
    ...clearRoutes,

    // 관리자 라우터 / admin.js
    ...adminRoutes,
  ],
})

export default router
