<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <header class="head-setting mb-10">
        <!-- 홈으로 이동 -->
        <router-link to="/admin" class="heed-text">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </router-link>

        <!-- 중앙 제목 -->
        <h1 class="text-3xl font-bold text-[#78711D]">명단 관리</h1>

        <!-- 우측 여백 -->
        <p></p>
      </header>

      <main class="flex-grow flex flex-col space-y-3">
        <div class="brown-box flex-grow flex-col p-2 h-40">
          <h2 class="text-xl font-bold text-[#FFEC17] pt-2 mb-4 text-center">지역 선택</h2>
          <div class="grid grid-cols-3 gap-2 overflow-y-auto flex-grow">
            <div v-for="region in regions" :key="region.value" @click="selectRegion(region.value)" :class="['p-2 rounded-md cursor-pointer text-center h-15 w-20 flex items-center justify-center border', selectedRegion === region.value ? 'bg-black text-[#3A9CFF] border-[#3A9CFF]' : 'bg-gray-100 text-[#1D1A05] hover:bg-[#FFFDE3] border-[#DCD7B8]']">
              {{ region.name }}
            </div>
          </div>
        </div>

        <!-- 현장 명단 -->
        <div class="brown-box flex-grow flex-col p-6 h-60">
          <h2 class="text-xl font-bold text-[#FFEC17] mb-4 text-center">
            {{ selectedRegion ? selectedRegion + ' 현장 명단' : '현장 명단' }}
          </h2>
          <div class="overflow-y-auto flex-grow">
            <template v-if="filteredUsers.length > 0">
              <div v-for="user in filteredUsers" :key="user.id" @click="selectUser(user)" :class="['button-click', selectedUser && selectedUser.id === user.id ? 'button-click-before' : 'button-click-after']">
                <p class="mr-10">현장: {{ user.site }}</p>
                <p>이름: {{ user.name }}</p>
              </div>
            </template>
            <p v-else class="text-white text-center">선택된 지역의 명단이 없습니다.</p>
          </div>
        </div>
      </main>

      <footer class="flex justify-center py-4">
        <button type="button" @click="deleteWorker" :class="['bg-black text-blue-500 font-bold py-3 px-6 rounded-xl w-full', !selectedUser ? 'opacity-50 cursor-not-allowed' : '']" :disabled="!selectedUser">작업자 삭제</button>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { users as importedUsers } from '@/js/data.js'
import { regions as importedRegions } from '@/js/locate.js'

const usersData = ref(importedUsers)
const regions = ref(importedRegions)
const selectedRegion = ref(null)
const selectedUser = ref(null)

const selectRegion = (region) => {
  selectedRegion.value = region
  selectedUser.value = null // Clear selected user when region changes
}

const selectUser = (user) => {
  selectedUser.value = user
}

const filteredUsers = computed(() => {
  if (!selectedRegion.value) {
    return []
  }
  return usersData.value.filter((user) => user.region === selectedRegion.value)
})

const deleteWorker = () => {
  if (!selectedUser.value) {
    alert('삭제할 작업자를 선택해주세요.')
    return
  }

  const index = usersData.value.findIndex((user) => user.id === selectedUser.value.id)
  if (index !== -1) {
    usersData.value.splice(index, 1)
    selectedUser.value = null
  }
}
</script>

<style scoped>
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 10px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
