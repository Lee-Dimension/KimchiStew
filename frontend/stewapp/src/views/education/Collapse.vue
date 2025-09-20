<template>
  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-5 font-sans">
      <header class="relative flex justify-center items-center py-4 mb-10">
        <button class="absolute left-0">
          <router-link to="/clear">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
            </svg>
          </router-link>
        </button>
        <h1 class="text-4xl font-bold text-[#78711D]">끼임</h1>
      </header>

      <main class="flex justify-center mt-10">
        <div class="relative w-full bg-[#1D1A05] rounded-3xl p-6 shadow-lg h-110 overflow-y-auto">
          <p class="text-lg font-bold text-[#FF0000] text-center mb-5">신고는 기본입니다</p>
          <div class="space-y-4 text-base text-white">
            <p>1. 기계를 멈춰주세요.</p>
            <p>2. 가능하다면 환자를 안전한 장소로 옮겨 추가 부상을 막아주세요. 강제로 꺼내는 것은 금지입니다.</p>
            <p>3. 의식, 호흡, 출혈 등 환자의 기본적인 상태를 파악해주세요.</p>
          </div>
        </div>
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

/* 스크롤바 디자인을 위한 커스텀 스타일 (선택 사항) */
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}
</style>
