<template>
  <div class="bg-[#FFFEEF] min-h-screen">
    <div class="w-full max-w-sm mx-auto p-8 font-sans flex flex-col h-screen">
      <header class="relative flex justify-center items-center py-4 mb-10">
        <button class="absolute left-0">
          <router-link to="/admin">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="#78711D" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
            </svg>
          </router-link>
        </button>
        <h1 class="text-4xl font-bold text-[#78711D]">명단 관리</h1>
      </header>

      <main class="flex-grow flex flex-col space-y-3">
        <div class="flex-grow w-full bg-[#1D1A05] rounded-xl p-6 flex flex-col h-40">
          <h2 class="text-xl font-bold text-[#FFEC17] mb-4 text-center">지역 선택</h2>
          <div class="grid grid-cols-2 gap-2 overflow-y-auto flex-grow">
            <div v-for="region in regions" :key="region.value" @click="selectRegion(region.value)" :class="['py-2 px-4 rounded-md cursor-pointer text-center h-auto w-full flex items-center justify-center', selectedRegion === region.value ? 'bg-[#FFFDE3] text-[#1D1A05]' : 'bg-gray-700 text-white hover:bg-gray-600']">
              {{ region.name }}
            </div>
          </div>
        </div>

        <div class="flex-grow w-full bg-[#1D1A05] rounded-3xl p-6 flex flex-col mt-4 h-40">
          <h2 class="text-xl font-bold text-[#FFEC17] mb-4 text-center">
            {{ selectedRegion ? selectedRegion + ' 현장 명단' : '현장 명단' }}
          </h2>
          <div class="overflow-y-auto flex-grow">
            <template v-if="filteredUsers.length > 0">
              <div v-for="user in filteredUsers" :key="user.id" @click="selectUser(user)" :class="['p-2 mb-2 rounded-md cursor-pointer', selectedUser && selectedUser.id === user.id ? 'bg-[#FFFDE3] text-[#1D1A05]' : 'bg-gray-700 text-white hover:bg-gray-600']">
                <p>현장: {{ user.site }}</p>
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
