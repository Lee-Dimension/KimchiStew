<template>
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
        <router-link to="/education">
          <div class="flex justify-between items-center w-full p-5 bg-[#1D1A05] rounded-2xl cursor-pointer">
            <div>
              <p class="text-lg font-bold text-[#FFEC17]">건설업 기초안전보건교육</p>
              <p class="text-sm font-bold text-[#3A9CFF] mt-1">이수</p>
            </div>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="white" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
            </svg>
          </div>
        </router-link>

        <router-link to="/education">
          <div class="flex justify-between items-center w-full p-5 bg-[#1D1A05] rounded-2xl cursor-pointer mt-5">
            <div>
              <p class="text-lg font-bold text-[#FFEC17]">정기 안전보건교육</p>
              <p class="text-sm font-bold text-[#FF0000] mt-1">미이수</p>
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
              <span class="text-[#3A9CFF] ml-1">합격</span>
              <span class="text-[#FF0000] ml-1">불합격</span>
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

<script setup></script>

<style scoped>
/* 기본 폰트 설정을 위해 추가 (필요 시 프로젝트 전역 폰트로 대체) */
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
