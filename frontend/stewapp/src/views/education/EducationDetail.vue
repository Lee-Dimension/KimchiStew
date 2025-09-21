<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <header class="head-setting mb-10">
        <!-- 홈으로 이동 -->
        <router-link to="/clear" class="heed-text">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </router-link>

        <!-- 중앙 제목 -->
        <h1 class="text-3xl font-bold text-[#78711D]">{{ pageData.title }}</h1>

        <!-- 우측 여백 -->
        <p></p>
      </header>

      <main class="flex justify-center mt-10">
        <div class="relative w-full bg-[#1D1A05] rounded-3xl p-6 shadow-lg h-110 overflow-y-auto">
          <p class="text-lg font-bold text-[#FF0000] text-center mb-5">{{ pageData.subtitle }}</p>
          <div class="space-y-4 text-base text-white">
            <p v-for="(line, index) in pageData.content" :key="index">{{ line }}</p>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { educationData } from '@/js/educationData.js'

const route = useRoute()

const pageData = computed(() => {
  const type = route.params.type
  return educationData[type] || { title: 'Error', subtitle: '내용을 찾을 수 없습니다.', content: [] }
})
</script>

<style scoped>
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}
</style>
