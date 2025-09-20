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
        <h1 class="text-4xl font-bold text-[#78711D]">감전</h1>
      </header>

      <main class="flex justify-center mt-10">
        <div class="relative w-full bg-[#1D1A05] rounded-3xl p-6 shadow-lg h-110 overflow-y-auto">
          <p class="text-lg font-bold text-[#FF0000] text-center mb-5">신고는 기본입니다</p>
          <div class="space-y-4 text-base text-white">
            <p>1. 절대 환자를 직접 만지지 마세요.</p>
            <p>2. 전원 스위치를 차단하여 전기를 단절해 주세요.</p>
            <p>3. 환자를 전원에서 안전하게 분리해주세요.</p>
            <p>4. 의식, 호흡 등 환자 상태를 살펴주세요.</p>
            <p>5. 호흡이 없다면 CPR (심폐소생술)을 실시하세요.</p>
            <p>6. 감전 사고는 병원 정밀 검진이 필수입니다. 반드시 병원에 내진하세요.</p>
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
