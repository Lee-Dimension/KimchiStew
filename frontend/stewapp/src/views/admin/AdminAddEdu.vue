<template>
  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-8 font-sans flex flex-col h-screen">
      <header class="relative flex justify-center items-center py-4 mb-16">
        <button class="absolute left-0">
          <router-link to="/admin">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
            </svg>
          </router-link>
        </button>
        <h1 class="text-4xl font-bold text-[#78711D]">교육 등록</h1>
      </header>

      <main class="flex-grow flex flex-col space-y-3">
        <div class="flex items-center w-full">
          <label for="title" class="w-16 font-bold text-[#767676]">제목</label>
          <input type="text" id="title" placeholder="기초안전교육" class="flex-1 py-2 px-3 border border-[#DCD7B8] rounded-full bg-[#FFFDE3] placeholder:text-[#999999] focus:outline-none focus:ring-1 focus:ring-[#78711D]" />
        </div>

        <div class="flex items-center justify-between w-full">
          <button class="bg-black text-white w-10 h-10 rounded-full flex items-center justify-center text-2xl font-bold">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 12h-15" />
            </svg>
          </button>
          <button type="submit" class="bg-black text-[#FFEC17] font-bold py-2 px-10 rounded-full">교육 등록하기</button>
          <button class="bg-black text-white w-10 h-10 rounded-full flex items-center justify-center text-2xl font-bold">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
            </svg>
          </button>
        </div>

        <div class="relative flex-grow w-full bg-[#1D1A05] rounded-3xl p-6 flex flex-col justify-between">
          <textarea placeholder="교육 내용을 입력하세요" class="w-full h-full bg-transparent text-white placeholder:text-[#C7C7C7] text-sm focus:outline-none resize-none"></textarea>
        </div>
      </main>

      <footer class="flex justify-between items-center w-full flex-shrink-0">
        <footer class="flex justify-between items-center w-full py-4 flex-shrink-0">
          <button class="bg-black text-white w-10 h-10 rounded-full flex items-center justify-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
            </svg>
          </button>

          <p class="text-lg font-bold text-[#78711D]">1페이지</p>

          <button class="bg-black text-white w-10 h-10 rounded-full flex items-center justify-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
            </svg>
          </button>
        </footer>
      </footer>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
/* 기본 폰트 설정을 위해 추가 */
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
