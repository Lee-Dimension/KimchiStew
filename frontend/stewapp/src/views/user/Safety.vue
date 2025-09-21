<template>
  <!-- 교육 여부 팝업창 -->
  <div v-if="showPopup" class="fixed inset-0 bg-[rgba(0,0,0,0.75)] flex justify-center items-center z-50">
    <div class="bg-[#FFFEEF] p-8 rounded-lg shadow-xl text-center">
      <p class="text-xl font-bold mb-4">{{ popupMessage }}</p>
      <button @click="showPopup = false" class="bg-[#1D1A05] hover:bg-black text-[#FFEA00] font-bold py-2 px-8 rounded">확인</button>
    </div>
  </div>

  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <header class="head-setting mb-16">
        <!-- 홈으로 이동 -->
        <router-link to="/main" class="heed-text">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </router-link>

        <!-- 중앙 제목 -->
        <h1 class="text-3xl font-bold text-[#78711D]">안전 교육</h1>

        <!-- 우측 여백 -->
        <p></p>
      </header>

      <!-- 중앙 선택 박스 -->
      <main class="space-y-5">
        <!-- 기초안전교육 -->
        <router-link to="/education">
          <div class="small-box bg-[#1D1A05]">
            <div>
              <p class="text-lg font-bold text-[#FFEC17]">건설업 기초안전보건교육</p>
              <p v-if="basicEducationStatus === 'valid'" class="text-sm font-bold text-[#3A9CFF] mt-1">이수</p>
              <p v-else class="text-sm font-bold text-[#FF0000] mt-1">미이수</p>
            </div>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="white" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
            </svg>
          </div>
        </router-link>

        <!-- 정기안전교육 -->
        <router-link to="/education">
          <div class="small-box bg-[#1D1A05] mt-5">
            <div>
              <p class="text-lg font-bold text-[#FFEC17]">정기 안전보건교육</p>
              <p v-if="regularEducationStatus === 'valid'" class="text-sm font-bold text-[#3A9CFF] mt-1">이수</p>
              <p v-else class="text-sm font-bold text-[#FF0000] mt-1">미이수</p>
            </div>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="white" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
            </svg>
          </div>
        </router-link>
      </main>

      <!-- 안전 시험 -->
      <footer class="mt-35">
        <div @click="checkTestEligibility" :class="['small-box', testStatus === 'pass' ? 'cursor-not-allowed' : 'cursor-pointer', 'bg-[#FFEC17]']">
          <div>
            <p class="text-lg font-bold text-[#1D1A05]">안전 시험</p>
            <p class="text-sm font-bold mt-1">
              <span v-if="testStatus === 'none'" class="text-gray-500">미응시</span>
              <span v-else-if="testStatus === 'pass'" class="text-blue-500">합격</span>
              <span v-else-if="testStatus === 'fail'" class="text-red-500">불합격</span>
            </p>
          </div>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" :stroke="'#1D1A05'" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
          </svg>
        </div>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, onActivated } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const user = computed(() => {
  const storedUser = sessionStorage.getItem('loggedInUser')
  return storedUser ? JSON.parse(storedUser) : null
})
const showPopup = ref(false)

const basicEducationStatus = computed(() => {
  if (user.value && user.value.educationCompletionDate) {
    return 'valid'
  }
  return 'none'
})

const regularEducationStatus = computed(() => {
  if (!user.value || !user.value.educationCompletionDate) {
    return 'none'
  }

  const today = new Date()
  const completionDateStr = user.value.educationCompletionDate
  const [year, month, day] = completionDateStr.split('-').map(Number)
  const completionDate = new Date(`20${year}`, month - 1, day)

  const expiryDate = new Date(completionDate)
  expiryDate.setMonth(expiryDate.getMonth() + 3)

  if (today >= expiryDate) {
    return 'expired'
  } else {
    return 'valid'
  }
})

const testStatus = computed(() => {
  if (!user.value || !user.value.test) return 'none'
  if (user.value.test === 'pass') return 'pass'
  if (user.value.test === 'nonepass') return 'fail'
  return 'none'
})

const popupMessage = ref('')

onMounted(() => {
  if (user.value) {
    if (regularEducationStatus.value === 'expired') {
      popupMessage.value = '재교육 대상자입니다.'
      showPopup.value = true
    } else if (regularEducationStatus.value === 'none') {
      popupMessage.value = '기초교육 대상자입니다.'
      showPopup.value = true
    }
  }
})

const checkTestEligibility = () => {
  if (basicEducationStatus.value === 'none') {
    popupMessage.value = '기초교육을 완수해야합니다.'
    showPopup.value = true
    return
  }

  if (testStatus.value === 'pass') {
    return
  }

  router.push('/test')
}
</script>
