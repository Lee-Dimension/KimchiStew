<template>
  <!-- Fullscreen Image Viewer -->
  <div v-if="isFullscreen" @click="closeFullscreen" class="fixed inset-0 bg-[rgba(0,0,0,0.85)] flex justify-center items-center z-50 p-4">
    <img :src="fullscreenContent" class="max-w-full max-h-full object-contain" />
    <button class="absolute top-4 right-4 text-white text-4xl font-bold">&times;</button>
  </div>

  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-5 font-sans flex flex-col h-screen">
      <header class="relative flex justify-center items-center py-4 flex-shrink-0">
        <button class="absolute left-0">
          <router-link to="/safe">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
            </svg>
          </router-link>
        </button>
        <h1 class="text-3xl font-bold text-[#78711D]">정기 안전보건교육</h1>
      </header>

      <!-- Content Viewer -->
      <main class="flex-grow my-4 flex flex-col">
        <div class="w-full h-full bg-[#1D1A05] rounded-3xl flex items-center justify-center p-5 text-white text-lg text-center">
          <p v-if="currentPageData.type === 'text'">{{ currentPageData.content }}</p>
          <img v-if="currentPageData.type === 'image'" :src="currentPageData.content" alt="Education Content" class="max-w-full max-h-full object-contain cursor-pointer" @click="openFullscreen(currentPageData.content)" />
        </div>
      </main>

      <!-- Completion Button (Last Page Only) -->
      <div v-if="isLastPage" class="my-4 text-center flex-shrink-0">
        <button v-if="educationStatus !== 'valid'" @click="completeEducation" class="w-full bg-green-500 hover:bg-green-700 text-white font-bold py-3 px-4 rounded-lg">교육 이수 완료</button>
      </div>

      <!-- Navigation Footer -->
      <footer class="flex justify-between items-center w-full py-4 flex-shrink-0">
        <button @click="prevPage" :disabled="isFirstPage" class="bg-black text-white w-14 h-14 rounded-full flex items-center justify-center disabled:opacity-50">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </button>

        <p class="text-lg font-bold text-[#78711D]">{{ currentPageIndex + 1 }} / {{ courseData.length }}</p>

        <button @click="nextPage" :disabled="isLastPage" class="bg-black text-white w-14 h-14 rounded-full flex items-center justify-center disabled:opacity-50">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
          </svg>
        </button>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

// --- Course Data (Moved from external file) ---
const courseData = [
  {
    type: 'text',
    content: '1. 안전의 중요성: 모든 작업에 앞서 안전을 최우선으로 생각해야 합니다. 작은 부주의가 큰 사고로 이어질 수 있습니다.',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}doldari.png`,
  },
  {
    type: 'text',
    content: '3. 개인 보호 장비 (PPE): 헬멧, 안전화, 장갑 등 지정된 개인 보호 장비는 항상 올바르게 착용해야 합니다.',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}favicon.png`,
  },
  {
    type: 'text',
    content: '5. 모든 교육 내용을 숙지하셨습니다. 아래 버튼을 눌러 교육을 완료하세요.',
  },
]

// --- User and Education Status Logic ---
const router = useRouter()
const user = ref(null)

const educationStatus = computed(() => {
  if (!user.value || !user.value.educationCompletionDate) return 'none'
  const today = new Date('2025-09-20')
  const [year, month, day] = user.value.educationCompletionDate.split('-').map(Number)
  const completionDate = new Date(`20${year}`, month - 1, day)
  const expiryDate = new Date(completionDate)
  expiryDate.setMonth(expiryDate.getMonth() + 3)
  return today >= expiryDate ? 'expired' : 'valid'
})

const completeEducation = () => {
  if (user.value) {
    const today = new Date('2025-09-20')
    const year = String(today.getFullYear()).slice(-2)
    const month = String(today.getMonth() + 1).padStart(2, '0')
    const day = String(today.getDate()).padStart(2, '0')
    user.value.educationCompletionDate = `${year}-${month}-${day}`
    sessionStorage.setItem('loggedInUser', JSON.stringify(user.value))
    router.push('/safe')
  }
}

// --- Page Viewer Logic ---
const currentPageIndex = ref(0)
const isFullscreen = ref(false)
const fullscreenContent = ref('')

const currentPageData = computed(() => courseData[currentPageIndex.value])
const isFirstPage = computed(() => currentPageIndex.value === 0)
const isLastPage = computed(() => currentPageIndex.value === courseData.length - 1)

const nextPage = () => {
  if (!isLastPage.value) currentPageIndex.value++
}

const prevPage = () => {
  if (!isFirstPage.value) currentPageIndex.value--
}

const openFullscreen = (imageUrl) => {
  fullscreenContent.value = imageUrl
  isFullscreen.value = true
}

const closeFullscreen = () => {
  isFullscreen.value = false
}

// --- Lifecycle Hook ---
onMounted(() => {
  const storedUser = sessionStorage.getItem('loggedInUser')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
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
