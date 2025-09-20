<template>
  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-5 font-sans">
      <header class="relative flex justify-center items-center py-4 mb-10">
        <router-link to="/main" class="absolute left-0">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </router-link>
        <h1 class="text-4xl font-bold text-[#78711D]">사고조치</h1>
      </header>

      <main class="grid grid-cols-2 gap-4">
        <router-link v-for="item in accidentTypes" :key="item.name" :to="item.link" class="relative flex items-center justify-center bg-[#1D1A05] rounded-3xl aspect-square cursor-pointer">
          <img :src="item.img" :alt="item.name" />
        </router-link>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const accidentTypes = ref([
  { name: '추락', img: import.meta.env.BASE_URL + 'clear/falling.png', link: '/falling' },
  { name: '끼임', img: import.meta.env.BASE_URL + 'clear/collapse.png', link: '/collapse' },
  { name: '감전', img: import.meta.env.BASE_URL + 'clear/elect.png', link: '/electrocution' },
  { name: '넘어짐', img: import.meta.env.BASE_URL + 'clear/fall.png', link: '/fall' },
  { name: '낙하물', img: import.meta.env.BASE_URL + 'clear/hit.png', link: '/hit' },
  { name: '열사병·탈진', img: import.meta.env.BASE_URL + 'clear/sun.png', link: '/sun' },
  { name: '화재', img: import.meta.env.BASE_URL + 'clear/fire.png', link: '/fire' },
  { name: '붕괴', img: import.meta.env.BASE_URL + 'clear/break.png', link: '/break' },
])
</script>

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
