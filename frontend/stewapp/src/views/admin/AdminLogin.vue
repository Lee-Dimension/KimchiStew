<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <div class="head-setting">
        <p></p>
        <router-link to="/" class="heed-text">일반 로그인</router-link>
      </div>

      <!-- 중앙 설정 -->
      <main class="main-setting">
        <!-- 로고 -->
        <img src="/doldari.png" class="w-50 h-50" />

        <!-- 로그인 폼 -->
        <div class="flex flex-col items-center">
          <form @submit.prevent="login" class="flex flex-col mt-4 w-70">
            <!-- ID -->
            <div class="flex flex-col w-full mb-4">
              <label for="adminId" class="input-label m-3">ID</label>
              <input type="text" id="adminId" v-model="id" placeholder="user" class="input-box" />
            </div>

            <!-- PW -->
            <div class="flex flex-col w-full mb-13">
              <label for="adminPw" class="input-label m-3">PW</label>
              <input type="password" id="adminPw" v-model="password" placeholder="user" class="input-box" />
            </div>

            <p v-if="loginError" class="text-red-500 text-center mb-5">입력하신 정보가 일치하지 않습니다</p>
            <div v-else class="mb-5"></div>

            <!-- 로그인 -->
            <button type="submit" :disabled="isButtonDisabled" :class="{ 'bg-gray-300': isButtonDisabled, 'bg-[#FFEC17] hover:bg-yellow-300': !isButtonDisabled }" class="login-button">로그인</button>
          </form>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { admin } from '@/js/admin.js'

const router = useRouter()

const id = ref('')
const password = ref('')
const loginError = ref(false)

const isButtonDisabled = computed(() => {
  return !id.value || !password.value
})

const login = () => {
  if (isButtonDisabled.value) {
    return
  }

  const adminUser = id.value === admin[0].id && password.value === admin[0].password

  if (adminUser) {
    router.push('/admin')
    localStorage.setItem('loggedInAdminId', id.value)
  } else {
    loginError.value = true
  }
}
</script>

<style scoped></style>
