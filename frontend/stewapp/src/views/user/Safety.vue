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
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 서버에서 내려오는 상태 원본
const educationCompletionDateStr = ref('none') // 'YYYY-MM-DD' 또는 'none'
const testRaw = ref('none') // 'none' | 'pass' | 'nonepass'

// 팝업
const showPopup = ref(false)
const popupMessage = ref('')

// 기본 교육 이수 여부
const basicEducationStatus = computed(() => {
  return educationCompletionDateStr.value && educationCompletionDateStr.value !== 'none' ? 'valid' : 'none'
})

// 정기교육 유효성 (이수일 + 3개월)
const regularEducationStatus = computed(() => {
  const str = educationCompletionDateStr.value
  if (!str || str === 'none') return 'none'

  const parts = str.split('-').map(Number)
  if (parts.length !== 3 || parts.some((n) => Number.isNaN(n))) return 'none'
  const [year, month, day] = parts
  const completionDate = new Date(year, month - 1, day)
  if (Number.isNaN(completionDate.getTime())) return 'none'

  const expiryDate = new Date(completionDate)
  expiryDate.setMonth(expiryDate.getMonth() + 3)

  const today = new Date()
  return today >= expiryDate ? 'expired' : 'valid'
})

// 시험 상태
const testStatus = computed(() => {
  if (testRaw.value === 'pass') return 'pass'
  if (testRaw.value === 'nonepass') return 'fail'
  return 'none'
})

// 상태 조회
onMounted(async () => {
  try {
    const res = await fetch('http://localhost:8080/api/user/status', {
      method: 'GET',
      headers: { Accept: 'application/json' },
      credentials: 'include',
    })
    if (res.ok) {
      const data = await res.json()
      educationCompletionDateStr.value = data?.educationCompletionDate ?? 'none'
      testRaw.value = data?.test ?? 'none'
    } else {
      educationCompletionDateStr.value = 'none'
      testRaw.value = 'none'
    }
  } catch (e) {
    educationCompletionDateStr.value = 'none'
    testRaw.value = 'none'
  }

  // 상태 기반 안내 팝업
  if (regularEducationStatus.value === 'expired') {
    popupMessage.value = '재교육 대상자입니다.'
    showPopup.value = true
  } else if (regularEducationStatus.value === 'none') {
    popupMessage.value = '기초교육 대상자입니다.'
    showPopup.value = true
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
