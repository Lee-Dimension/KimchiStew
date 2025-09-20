<template>
  <!-- Popup -->
  <div v-if="showPopup" class="fixed inset-0 bg-[rgba(0,0,0,0.75)] flex justify-center items-center z-50">
    <div class="bg-[#FFFEEF] p-8 rounded-lg shadow-xl text-center">
      <p class="text-xl font-bold mb-4">{{ popupMessage }}</p>
      <button @click="showPopup = false" class="bg-[#1D1A05] hover:bg-black text-[#FFEA00] font-bold py-2 px-8 rounded">확인</button>
    </div>
  </div>

  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-5 font-sans">
      <header class="relative flex justify-center items-center py-4 mb-16">
        <button class="absolute left-0">
          <router-link to="/main">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
            </svg>
          </router-link>
        </button>
        <h1 class="text-4xl font-bold text-[#78711D]">안전 교육</h1>
      </header>

      <main class="space-y-5">
        <!-- Dynamic Basic Safety Training -->
        <router-link to="/education">
          <div class="flex justify-between items-center w-full p-5 bg-[#1D1A05] rounded-2xl cursor-pointer">
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

        <!-- Dynamic Regular Safety Training -->
        <router-link to="/education">
          <div class="flex justify-between items-center w-full p-5 bg-[#1D1A05] rounded-2xl mt-5">
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

      <footer class="mt-35">
        <div @click="checkTestEligibility" :class="['flex justify-between items-center w-full p-5 rounded-2xl', testStatus === 'pass' ? 'cursor-not-allowed' : 'cursor-pointer', 'bg-[#FFEC17]']">
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

const user = computed(() => { const storedUser = sessionStorage.getItem('loggedInUser'); return storedUser ? JSON.parse(storedUser) : null })
const showPopup = ref(false)

const basicEducationStatus = computed(() => {
  if (user.value && user.value.educationCompletionDate) {
    return 'valid'
  }
  return 'none'
})

const regularEducationStatus = computed(() => {
  if (!user.value || !user.value.educationCompletionDate) {
    return 'none' // Not completed
  }

  const today = new Date()
  const completionDateStr = user.value.educationCompletionDate
  const [year, month, day] = completionDateStr.split('-').map(Number)
  const completionDate = new Date(`20${year}`, month - 1, day)

  const expiryDate = new Date(completionDate)
  expiryDate.setMonth(expiryDate.getMonth() + 3)

  if (today >= expiryDate) {
    return 'expired' // Expired
  } else {
    return 'valid' // Valid
  }
})

const testStatus = computed(() => {
  if (!user.value || !user.value.test) return 'none' // 'none' for not taken
  if (user.value.test === 'pass') return 'pass'
  if (user.value.test === 'nonepass') return 'fail'
  return 'none' // Default to 'none' if unexpected value
})

const popupMessage = ref('')



onMounted(() => {
  // The user computed property will automatically get the latest.
  // We still need to check for initial popups.
  if (user.value) { // Check if a user is logged in
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
    // Already passed, do nothing (button is visually disabled)
    return
  }

  // Eligible to take/retake test
  router.push('/test')
}
</script>

<style scoped>
.font-sans {
  font-family:
    system-ui,
    -apple-system,
    BlinkMacSystemFont,
    'Segoe UI',
    Roboto,
    'Helvetica Neue',
    Arial,
    'Noto Sans',
    sans-serif,
    'Apple Color Emoji',
    'Segoe UI Emoji',
    'Segoe UI Symbol',
    'Noto Color Emoji';
}
</style>
