<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <header class="head-setting mb-10">
        <!-- 홈으로 이동 -->
        <router-link to="/main" class="heed-text">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </router-link>

        <!-- 중앙 제목 -->
        <h1 class="text-3xl font-bold text-[#78711D]">사고조치</h1>

        <!-- 우측 여백 -->
        <p></p>
      </header>

      <!-- 중앙 선택 박스 -->
      <main class="grid grid-cols-2 gap-4">
        <router-link v-for="item in accidentTypes" :key="item.name" :to="item.link" class="flex items-center justify-center bg-[#1D1A05] rounded-3xl aspect-square cursor-pointer">
          <img :src="item.img" :alt="item.name" />
        </router-link>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const accidentTypes = ref([
  { name: '추락', img: import.meta.env.BASE_URL + 'clear/fall_from_height.png', link: '/education/fall_from_height' },
  { name: '끼임', img: import.meta.env.BASE_URL + 'clear/entrapment.png', link: '/education/entrapment' },
  { name: '감전', img: import.meta.env.BASE_URL + 'clear/electric_shock.png', link: '/education/electric_shock' },
  { name: '넘어짐', img: import.meta.env.BASE_URL + 'clear/slip_and_fall.png', link: '/education/slip_and_fall' },
  { name: '낙하물', img: import.meta.env.BASE_URL + 'clear/falling_object.png', link: '/education/falling_object' },
  { name: '열사병·탈진', img: import.meta.env.BASE_URL + 'clear/heatstroke.png', link: '/education/heatstroke' },
  { name: '화재', img: import.meta.env.BASE_URL + 'clear/fire.png', link: '/education/fire' },
  { name: '붕괴', img: import.meta.env.BASE_URL + 'clear/collapse.png', link: '/education/collapse' },
])
</script>

<style scoped>
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}
</style>
