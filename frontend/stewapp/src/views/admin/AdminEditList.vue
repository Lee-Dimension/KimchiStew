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
            <div v-for="region in regions" :key="region.id ?? region.value" @click="selectRegion(region)" :class="['p-2 rounded-md cursor-pointer text-center h-15 w-20 flex items-center justify-center border', selectedRegionId === (region.id ?? region.value) ? 'bg-black text-[#3A9CFF] border-[#3A9CFF]' : 'bg-gray-100 text-[#1D1A05] hover:bg-[#FFFDE3] border-[#DCD7B8]']">
              {{ region.name }}
            </div>
          </div>
        </div>

        <!-- 현장 명단 -->
        <div class="brown-box flex-grow flex-col p-6 h-60">
          <h2 class="text-xl font-bold text-[#FFEC17] mb-4 text-center">
            {{ selectedRegionName ? selectedRegionName + ' 현장 명단' : '현장 명단' }}
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
import { ref, computed, onMounted } from 'vue'

// 상태
const regions = ref([]) // [{ id, name }]
const usersData = ref([]) // [{ id, name, regionId, siteId, site }]
const loadingRegions = ref(false)
const loadingUsers = ref(false)
const selectedRegionId = ref(null)
const selectedRegionName = ref(null)
const selectedUser = ref(null)

// 초기 로드: 지역 목록
const loadRegions = async () => {
  loadingRegions.value = true
  try {
    const res = await fetch('http://localhost:8080/api/locations', {
      method: 'GET',
      headers: { Accept: 'application/json' },
      credentials: 'include',
    })
    if (!res.ok) throw new Error(`HTTP ${res.status}`)
    const data = await res.json()
    // 허용 포맷: [{ id, name }] 또는 [{ id, regionName }] 또는 ['서울', '부산']
    regions.value = Array.isArray(data)
      ? data.map((r) => {
          if (typeof r === 'string') return { id: r, name: r }
          return { id: r.id ?? r.value ?? r.regionId ?? r.region_id ?? r.regionName ?? r.name, name: r.name ?? r.regionName ?? r.value ?? '' }
        })
      : []
  } finally {
    loadingRegions.value = false
  }
}

// 지역 기준 사용자 로드
const loadUsers = async (regionId, regionName) => {
  if (!regionId && !regionName) {
    usersData.value = []
    return
  }
  loadingUsers.value = true
  try {
    // regionId가 숫자 또는 유효한 ID일 경우 regionId 사용, 아니면 regionName 사용
    let query = ''
    if (regionId && typeof regionId === 'number') {
      query = `regionId=${encodeURIComponent(regionId)}`
    } else if (regionId && typeof regionId === 'string' && !regionName) {
      // id로 이름이 들어온 경우 이름으로 검색
      query = `regionName=${encodeURIComponent(regionId)}`
    } else if (regionName) {
      query = `regionName=${encodeURIComponent(regionName)}`
    }

    const res = await fetch(`http://localhost:8080/api/users?${query}`, {
      method: 'GET',
      headers: { Accept: 'application/json' },
      credentials: 'include',
    })
    if (!res.ok) throw new Error(`HTTP ${res.status}`)
    const data = await res.json()
    // 기대 포맷(예시): [{ id, name, siteName, regionName }]
    usersData.value = Array.isArray(data)
      ? data.map((u) => ({
          id: u.id,
          name: u.name,
          regionId: u.regionId ?? u.region_id ?? null,
          regionName: u.regionName ?? u.region ?? u.region_name ?? null,
          siteId: u.siteId ?? u.site_id ?? null,
          site: u.siteName ?? u.site ?? u.site_name ?? '-',
        }))
      : []
  } catch (e) {
    usersData.value = []
  } finally {
    loadingUsers.value = false
  }
}

onMounted(async () => {
  await loadRegions()
})

// 선택 핸들러
const selectRegion = async (region) => {
  const id = region.id ?? region.value
  const name = region.name ?? region.value ?? region.regionName ?? ''
  selectedRegionId.value = id
  selectedRegionName.value = name
  selectedUser.value = null
  await loadUsers(id, name)
}

const selectUser = (user) => {
  selectedUser.value = user
}

// 화면 표시용: 현재는 서버 데이터 그대로 사용
const filteredUsers = computed(() => usersData.value)

// 작업자 삭제: region/site를 null로 업데이트
const deleteWorker = async () => {
  if (!selectedUser.value) {
    alert('삭제할 작업자를 선택해주세요.')
    return
  }

  try {
    const res = await fetch(`http://localhost:8080/api/users/${selectedUser.value.id}`, {
      method: 'PATCH', // 또는 'PUT'
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({ region_id: null, site_id: null }),
    })
    if (!res.ok) throw new Error(`HTTP ${res.status}`)

    // UI 동기화: 현재 지역 목록에서 제거
    usersData.value = usersData.value.filter((u) => u.id !== selectedUser.value.id)
    selectedUser.value = null
    alert('작업자의 작업 위치 정보가 삭제되었습니다.')
  } catch (e) {
    alert('작업자 삭제 처리에 실패했습니다. 잠시 후 다시 시도해주세요.')
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
