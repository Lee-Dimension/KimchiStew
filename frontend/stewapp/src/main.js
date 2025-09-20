import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import '@/main.css'

const app = createApp(App)

app.use(router)

router.beforeEach((to, from, next) => {
  const requiresAuth = ['Admin', 'AdminPlus', 'AdminEditList'].includes(to.name)
  const loggedInAdminId = localStorage.getItem('loggedInAdminId')

  if (requiresAuth && !loggedInAdminId) {
    next('/adminlogin')
  } else {
    next()
  }
})

app.mount('#app')
