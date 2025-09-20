<template>
  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-5 font-sans">
      <header class="flex justify-between items-center w-full pt-4">
        <p class="font-bold text-[#78711D]">홍길동님 환영합니다</p>
        <router-link to="/"><button class="font-bold text-[#78711D] text-sm">로그아웃</button> </router-link>
      </header>

      <main class="flex flex-col items-center">
        <img src="/doldari.png" class="w-50 h-50" />

        <div class="w-full space-y-5">
          <router-link to="/safe" class="flex justify-center items-center w-full py-10 px-20 bg-[#1D1A05] rounded-2xl cursor-pointer">
            <span class="text-3xl font-bold text-[#FFEC17]">안전교육</span>
          </router-link>

          <router-link to="/clear" class="flex justify-center items-center w-full py-10 px-20 bg-[#1D1A05] rounded-2xl cursor-pointer">
            <span class="text-3xl font-bold text-[#FFEC17]">사고조치</span>
          </router-link>
        </div>

        <button class="flex items-center justify-center gap-2 w-full bg-red-500 text-white text-lg font-bold py-4 mt-10 rounded-2xl">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 6.75c0 8.284 6.716 15 15 15h2.25a2.25 2.25 0 0 0 2.25-2.25v-1.372c0-.516-.351-.966-.852-1.091l-4.423-1.106c-.44-.11-.902.055-1.173.417l-.97 1.293c-.282.376-.769.542-1.21.38a12.035 12.035 0 0 1-7.143-7.143c-.162-.441.004-.928.38-1.21l1.293-.97c.363-.271.527-.734.417-1.173L6.963 3.102a1.125 1.125 0 0 0-1.091-.852H4.5A2.25 2.25 0 0 0 2.25 4.5v2.25Z" />
          </svg>
          긴급전화
        </button>
      </main>
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
