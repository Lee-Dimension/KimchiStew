<template>
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

      <main class="flex-grow my-4 flex flex-col">
        <div class="w-full h-100 bg-[#1D1A05] rounded-3xl p-5 text-white overflow-y-auto">
          <p v-if="currentPageData.type === 'text'" v-html="currentPageData.content" class="text-left"></p>
          <img v-if="currentPageData.type === 'image'" :src="currentPageData.content" alt="Education Content" class="max-w-full h-full object-contain cursor-pointer" @click="openFullscreen(currentPageData.content)" />

          <div v-if="isLastPage" class="my-4 text-center flex-shrink-0 mt-30">
            <button v-if="educationStatus !== 'valid'" @click="completeEducation" class="w-full bg-black hover:bg-zinc-900 border border-[#3A9CFF] text-[#3A9CFF] font-bold py-3 px-4 rounded-lg">교육 이수 완료</button>
          </div>
        </div>
      </main>

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
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe1.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe2.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe3.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe4.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe5.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe6.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'text',
    content: `<p class='text-lg text-center text-blue-500'>교육자료 출처<br>산업안전보건공단</p><br>
      <p class='text-lg text-center text-red-500'>[ 안전 10계명 ]</p><br>
      <p><span class='text-blue-500'>1. 안전모, 안전화 등 보호구 착용:</span>  <br>현장에서는 반드시 지정된 개인 보호구를 착용해야 합니다.</p><br>
      <p><span class='text-blue-500'>2. 작업 전 안전 점검:</span>  <br>작업을 시작하기 전에 장비와 주변 환경을 점검하여 위험 요인을 확인합니다.</p><br>
      <p><span class='text-blue-500'>3. 위험 지역 접근 금지:</span>  <br>출입이 통제된 구역이나 위험 표지가 있는 곳은 절대 들어가지 않습니다.</p><br>
      <p><span class='text-blue-500'>4. 안전 통로 이용:</span>  <br>작업장 내 정해진 안전 통로로만 이동합니다.</p><br>
      <p><span class='text-blue-500'>5. 고소 작업 시 안전벨트 착용:</span>  <br>높은 곳에서 작업할 때는 추락 방지를 위해 안전벨트를 착용하고 안전대를 걸어야 합니다.</p><br>
      <p><span class='text-blue-500'>6. 전기 장비 주의:</span>  <br>전기 관련 작업이나 장비 사용 시에는 누전이나 감전 위험에 항상 주의합니다.</p><br>
      <p><span class='text-blue-500'>7. 정해진 작업 순서 준수:</span>  <br>임의로 작업 순서를 바꾸지 않고 정해진 절차대로 작업합니다.</p><br>
      <p><span class='text-blue-500'>8. 안전 교육 이수:</span>  <br>주기적으로 실시하는 안전 교육에 빠짐없이 참여하여 안전 지식을 습득합니다.</p><br>
      <p><span class='text-blue-500'>9. 음주 작업 금지:</span>  <br>음주 상태에서는 절대 작업하지 않습니다.</p><br>
      <p><span class='text-blue-500'>10. 안전 수칙 준수:</span>  <br>어떤 상황에서도 안전 수칙을 최우선으로 생각하고 준수합니다.</p>`,
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe7.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'image',
    content: `${import.meta.env.BASE_URL}safe/safe8.jpg`,
    style: 'height: auto; max-height: 100%; width: auto; max-width: 100%; object-fit: contain;',
  },
  {
    type: 'text',
    content: "<p class=' text-center'>모든 교육 내용을 숙지하셨습니다.</p> <br><br><br><br> <p class='text-lg text-center text-blue-500'>아래 버튼을 눌러 <br>교육을 완료하세요.</p>",
  },
]

// --- User and Education Status Logic ---
const router = useRouter()
const user = computed(() => { const storedUser = sessionStorage.getItem('loggedInUser'); return storedUser ? JSON.parse(storedUser) : null })

const educationStatus = computed(() => {
  if (!user.value || !user.value.educationCompletionDate) return 'none'
  const today = new Date()
  const [year, month, day] = user.value.educationCompletionDate.split('-').map(Number)
  const completionDate = new Date(`20${year}`, month - 1, day)
  const expiryDate = new Date(completionDate)
  expiryDate.setMonth(expiryDate.getMonth() + 3)
  return today >= expiryDate ? 'expired' : 'valid'
})

const completeEducation = () => {
  if (user.value) {
    const today = new Date()
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

/* 스크롤바 디자인을 위한 커스텀 스타일 */
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}
</style>
