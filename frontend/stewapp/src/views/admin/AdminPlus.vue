<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <header class="head-setting mb-5">
        <!-- 홈으로 이동 -->
        <router-link to="/admin" class="heed-text">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </router-link>

        <!-- 중앙 제목 -->
        <h1 class="text-3xl font-bold text-[#78711D]">현장 등록</h1>

        <!-- 우측 여백 -->
        <p></p>
      </header>

      <!-- 본문 설정 -->
      <main class="flex flex-col items-center">
        <form class="flex flex-col mt-4 w-70">
          <!-- 지역 -->
          <div class="flex flex-col w-full mb-4">
            <label for="region" class="input-label">지역</label>
            <input type="text" id="region" v-model="newRegionName" placeholder="서울특별시 용산구 한남동" class="input-box" />
          </div>
          <!-- 현장 -->
          <div class="flex flex-col w-full">
            <label for="site" class="input-label">현장</label>
            <input type="text" id="site" v-model="newSiteName" placeholder="트리마제" class="input-box" />
          </div>
        </form>

        <div class="grid grid-cols-2 gap-4 mt-10 mb-5">
          <!-- 지역 리스트 -->
          <div class="input-box rounded-lg p-4 w-35 h-50 overflow-y-auto">
            <h2 class="text-xl font-bold text-[#78711D] mb-2 text-center">지역</h2>
            <div v-for="region in regions" :key="region.value" @click="selectedRegion = selectedRegion === region.value ? null : region.value" :class="['p-2 mb-2 rounded-md cursor-pointer text-center border border-[#DCD7B8] bg-[#FFFDE3]', selectedRegion === region.value ? 'bg-[#78711D] text-[#3A9CFF] border-[#3A9CFF]' : 'bg-gray-100 text-[#1D1A05] hover:bg-[#FFFDE3]']">
              {{ region.name }}
            </div>
          </div>
          <!-- 현장 리스트 -->
          <div class="input-box rounded-lg p-4 w-35 h-50 overflow-y-auto">
            <h2 class="text-xl font-bold text-[#78711D] mb-2 text-center">현장</h2>
            <div v-for="site in sites" :key="site.value" @click="selectedSite = selectedSite === site.value ? null : site.value" :class="['p-2 mb-2 rounded-md cursor-pointer text-center border border-[#DCD7B8] bg-[#FFFDE3]', selectedSite === site.value ? 'bg-[#78711D] text-[#3A9CFF] border-[#3A9CFF]' : 'bg-gray-100 text-[#1D1A05] hover:bg-[#FFFDE3]']">
              {{ site.name }}
            </div>
          </div>
        </div>
      </main>

      <footer class="flex justify-center py-4 gap-4">
        <button type="button" @click="deleteSelected" class="bg-white text-red-500 text-sm font-bold py-3 px-6 rounded-full border border-red-500">현장 삭제하기</button>
        <button type="button" @click="addLocation" class="bg-black text-[#3A9CFF] text-sm font-bold py-3 px-6 rounded-full">현장 추가하기</button>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { regions as importedRegions, sites as importedSites } from '@/js/locate.js'

const regions = ref(importedRegions)
const sites = ref(importedSites)

const selectedRegion = ref(null)
const selectedSite = ref(null)

const newRegionName = ref('')
const newSiteName = ref('')

const deleteSelected = () => {
  if (selectedRegion.value) {
    const index = regions.value.findIndex((r) => r.value === selectedRegion.value)
    if (index !== -1) {
      regions.value.splice(index, 1)
      selectedRegion.value = null
    }
  } else if (selectedSite.value) {
    const index = sites.value.findIndex((s) => s.value === selectedSite.value)
    if (index !== -1) {
      sites.value.splice(index, 1)
      selectedSite.value = null
    }
  } else {
    alert('삭제할 지역 또는 현장을 선택해주세요.')
  }
}

const addLocation = () => {
  if (newRegionName.value && newSiteName.value) {
    alert('지역과 현장을 동시에 추가할 수 없습니다. 하나만 입력해주세요.')
    return
  }

  if (newRegionName.value) {
    regions.value.push({ name: newRegionName.value, value: newRegionName.value })
    newRegionName.value = ''
  } else if (newSiteName.value) {
    sites.value.push({ name: newSiteName.value, value: newSiteName.value })
    newSiteName.value = ''
  } else {
    alert('추가할 지역 또는 현장 이름을 입력해주세요.')
  }
}
</script>
