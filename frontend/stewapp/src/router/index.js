import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
    },
    {
      path: '/main',
      name: 'Main',
      component: () => import('@/views/Main.vue'),
    },
    {
      path: '/clear',
      name: 'Clear',
      component: () => import('@/views/Clear.vue'),
    },
    {
      path: '/safety',
      name: 'Safety',
      component: () => import('@/views/Safety.vue'),
    },
  ],
})

export default router
