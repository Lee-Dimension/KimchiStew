<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <header class="head-setting">
        <p v-if="user" class="heed-text">{{ user.name }}님 환영합니다</p>
        <button @click="logout" class="heed-text">로그아웃</button>
      </header>

      <!-- 중앙 설정 -->
      <main class="main-setting">
        <!-- 로고 -->
        <img src="/doldari.png" class="w-50 h-50" />

        <div class="w-full space-y-5">
          <!-- 안전교육 링크 -->
          <router-link to="/safe" class="brown-box py-10 px-20">
            <span class="text-3xl font-bold text-[#FFEC17]">안전교육</span>
          </router-link>

          <!-- 사고조치 링크 -->
          <router-link to="/clear" class="brown-box py-10 px-20">
            <span class="text-3xl font-bold text-[#FFEC17]">사고조치</span>
          </router-link>
        </div>

        <!-- 긴급전화 버튼 -->
        <button class="emergency-button" @mousedown="startPress" @mouseup="cancelPress" @mouseleave="cancelPress" @touchstart.prevent="startPress" @touchend="cancelPress" @touchcancel="cancelPress">
          <div class="absolute top-0 left-0 h-full bg-red-700 transition-all duration-100" :style="{ width: gaugeWidth + '%' }"></div>
          <span class="relative z-10 flex items-center justify-center gap-2">
            <span class="material-symbols-outlined"> call </span>
            긴급전화
          </span>
        </button>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const user = ref(null)
const pressTimer = ref(null)
const gaugeInterval = ref(null)
const gaugeWidth = ref(0)
const pressDuration = 1500 // 1.5초 누르기

// 비로그인 사용자 로그인으로 이동
onMounted(() => {
  const storedUser = sessionStorage.getItem('loggedInUser')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
  } else {
    router.push('/')
  }
})

const logout = () => {
  sessionStorage.removeItem('loggedInUser')
  router.push('/')
}

// 긴급전화 설정
const startPress = () => {
  cancelPress()

  const updateRate = 30
  const increment = 100 / (pressDuration / updateRate)
  gaugeInterval.value = setInterval(() => {
    gaugeWidth.value += increment
  }, updateRate)

  pressTimer.value = setTimeout(() => {
    makePhoneCall()
    cancelPress()
  }, pressDuration)
}

const cancelPress = () => {
  clearTimeout(pressTimer.value)
  clearInterval(gaugeInterval.value)
  pressTimer.value = null
  gaugeInterval.value = null
  gaugeWidth.value = 0
}

// 긴급전화 전화번호
const makePhoneCall = () => {
  window.location.href = 'tel:01039407145'
}
</script>
