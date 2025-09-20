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
        <h1 class="text-4xl font-bold text-[#78711D]">현장 등록</h1>
      </header>

      <main class="flex-grow">
        <form class="space-y-5">
          <div class="flex items-center w-full">
            <label for="region" class="w-16 font-bold text-[#767676]">지역</label>
            <input type="text" id="region" placeholder="서울특별시 용산구 한남동" class="flex-1 py-2 px-3 border border-[#DCD7B8] rounded-full bg-[#FFFDE3] placeholder:text-[#999999] focus:outline-none focus:ring-1 focus:ring-[#78711D]" />
          </div>
          <div class="flex items-center w-full">
            <label for="site" class="w-16 font-bold text-[#767676]">현장</label>
            <input type="text" id="site" placeholder="트리마제" class="flex-1 py-2 px-3 border border-[#DCD7B8] rounded-full bg-[#FFFDE3] placeholder:text-[#999999] focus:outline-none focus:ring-1 focus:ring-[#78711D]" />
          </div>
        </form>

        <div class="flex items-center w-full mt-40">
          <label for="code" class="w-16 font-bold text-[#767676]">코드</label>
          <input type="text" id="code" placeholder="A92UIQ" class="flex-1 py-2 px-3 border border-[#DCD7B8] rounded-full bg-[#FFFDE3] placeholder:text-[#999999] focus:outline-none focus:ring-1 focus:ring-[#78711D]" />
        </div>
      </main>

      <footer class="flex justify-center py-4">
        <button type="submit" class="bg-black text-[#3A9CFF] font-bold py-3 px-6 rounded-full">코드 생성하기</button>
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
