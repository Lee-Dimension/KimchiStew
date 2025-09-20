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
        <div class="flex justify-between items-center w-full p-5 bg-[#FFEC17] rounded-2xl cursor-pointer">
          <div>
            <p class="text-lg font-bold text-[#1D1A05]">안전 시험</p>
            <p class="text-sm font-bold mt-1">
              <span class="text-gray-500">미응시</span>
            </p>
          </div>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="#1D1A05" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
          </svg>
        </div>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

const user = ref(null)
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

  const today = new Date('2025-09-20')
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

const popupMessage = computed(() => {
  if (regularEducationStatus.value === 'expired') {
    return '재교육 대상자입니다.'
  }
  if (regularEducationStatus.value === 'none') {
    return '기초교육 대상자입니다.'
  }
  return ''
})

onMounted(() => {
  const storedUser = sessionStorage.getItem('loggedInUser')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
    if (regularEducationStatus.value === 'expired' || regularEducationStatus.value === 'none') {
      showPopup.value = true
    }
  } else {
    // router.push('/') // Or handle error
  }
})
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