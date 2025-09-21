<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <div class="head-setting">
        <p></p>
        <router-link to="/adminlogin" class="heed-text">관리자 로그인</router-link>
      </div>

      <!-- 중앙 설정 -->
      <main class="main-setting">
        <!-- 로고 -->
        <img src="/doldari.png" class="w-50 h-50" />

        <!-- 지역/현장 -->
        <div class="flex justify-center gap-4 w-full px-5 mb-3">
          <!-- 지역 -->
          <div class="flex flex-col items-center flex-1">
            <label for="region" class="mb-2 font-bold text-[#767676] w-25 text-left">지역</label>
            <select id="region" v-model="region" class="p-1 border-b w-25 text-[#767676]">
              <option value="서울">서울</option>
              <option value="부산">부산</option>
              <option value="인천">인천</option>
            </select>
          </div>

          <!-- 현장 -->
          <div class="flex flex-col items-center flex-1">
            <label for="site" class="mb-2 font-bold text-[#767676] w-25 text-left">현장</label>
            <select id="site" v-model="site" class="p-1 border-b w-25 text-[#767676]">
              <option value="성수동">성수동</option>
              <option value="수영동">수영동</option>
              <option value="대곡동">대곡동</option>
            </select>
          </div>
        </div>

        <!-- 이름/전화번호 -->
        <div class="flex flex-col items-center">
          <form @submit.prevent="login" class="flex flex-col mt-4 w-70">
            <!-- 이름 입력 -->
            <div class="flex flex-col w-full mb-4">
              <label for="name" class="input-label">이름</label>
              <input v-model="name" type="text" id="name" placeholder="홍길동" class="input-box" />
            </div>

            <!-- 전화번호 입력 -->
            <div class="flex flex-col w-full mb-4">
              <label for="phone" class="input-label">전화번호</label>
              <input v-model="phone" type="tel" id="phone" placeholder="01012341234" class="input-box" />
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
import { users } from '@/js/data.js'

const router = useRouter()

const region = ref('')
const site = ref('')
const name = ref('')
const phone = ref('')
const loginError = ref(false)

const isButtonDisabled = computed(() => {
  return !region.value || !site.value || !name.value || !phone.value
})

const login = () => {
  if (isButtonDisabled.value) {
    return
  }

  const foundUser = users.find((user) => user.region === region.value && user.site === site.value && user.name === name.value && user.phone === phone.value)

  if (foundUser) {
    loginError.value = false
    sessionStorage.setItem('loggedInUser', JSON.stringify(foundUser))
    router.push('/main')
  } else {
    loginError.value = true
  }
}
</script>
