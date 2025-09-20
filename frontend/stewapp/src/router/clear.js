const clearRoutes = [
  {
    path: '/falling',
    name: 'Falling',
    component: () => import('@/views/education/Falling.vue'),
  },
  {
    path: '/collapse',
    name: 'Collapse',
    component: () => import('@/views/education/Collapse.vue'),
  },
  {
    path: '/electrocution',
    name: 'Electrocution',
    component: () => import('@/views/education/Electrocution.vue'),
  },
  {
    path: '/fall',
    name: 'Fall',
    component: () => import('@/views/education/Fall.vue'),
  },
  {
    path: '/hit',
    name: 'Hit',
    component: () => import('@/views/education/Hit.vue'),
  },
  {
    path: '/sun',
    name: 'Sun',
    component: () => import('@/views/education/Sun.vue'),
  },
  {
    path: '/fire',
    name: 'Fire',
    component: () => import('@/views/education/Fire.vue'),
  },
  {
    path: '/break',
    name: 'Break',
    component: () => import('@/views/education/Break.vue'),
  },
]

export default clearRoutes
