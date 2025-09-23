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
            <input type="text" id="region" v-model="newRegionName" placeholder="서울" class="input-box" />
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
            <div v-for="region in regionList" :key="region.id ?? region.name" @click="onSelectRegion(region)" :class="['p-2 mb-2 rounded-md cursor-pointer text-center border border-[#DCD7B8] bg-[#FFFDE3]', selectedRegionId === region.id ? 'bg-[#78711D] text-[#3A9CFF] border-[#3A9CFF]' : 'bg-gray-100 text-[#1D1A05] hover:bg-[#FFFDE3]']">
              {{ region.name }}
            </div>
          </div>
          <!-- 현장 리스트 -->
          <div class="input-box rounded-lg p-4 w-35 h-50 overflow-y-auto">
            <h2 class="text-xl font-bold text-[#78711D] mb-2 text-center">현장</h2>
            <div v-for="site in filteredSites" :key="site.id ?? site.name" @click="onSelectSite(site)" :class="['p-2 mb-2 rounded-md cursor-pointer text-center border border-[#DCD7B8] bg-[#FFFDE3]', selectedSiteId === site.id ? 'bg-[#78711D] text-[#3A9CFF] border-[#3A9CFF]' : 'bg-gray-100 text-[#1D1A05] hover:bg-[#FFFDE3]']">
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
import { ref, computed, onMounted } from 'vue'

// 서버에서 받아온 전체 지역/현장 목록
const locations = ref([]) // [{ id, name, sites: [{ id, name }] }]
const loading = ref(false)
const error = ref(false)

// 선택 상태
const selectedRegion = ref(null) // region.name 저장 (입력창 동기화용)
const selectedRegionId = ref(null) // region.id 저장 (동작/삭제용)
const selectedSite = ref(null) // site.name 저장 (표시용)
const selectedSiteId = ref(null) // site.id 저장 (동작/삭제용)

// 입력 상태(기존 UI 유지)
const newRegionName = ref('')
const newSiteName = ref('')
const submittingAdd = ref(false)

// 로딩: 지역/현장 목록
onMounted(async () => {
  loading.value = true
  error.value = false
  try {
    const res = await fetch('http://localhost:8080/api/locations', {
      method: 'GET',
      headers: { Accept: 'application/json' },
      credentials: 'include',
    })
    if (!res.ok) throw new Error(`HTTP ${res.status}`)
    const data = await res.json()
    locations.value = Array.isArray(data) ? data : []
  } catch (e) {
    error.value = true
    locations.value = []
  } finally {
    loading.value = false
  }
})

// 좌측 지역 버튼 목록
const regionList = computed(() => locations.value.map((r) => ({ id: r.id, name: r.name })))

// 우측 현장 버튼 목록(선택된 지역 기준)
const filteredSites = computed(() => {
  const region = locations.value.find((r) => r.id === selectedRegionId.value)
  return region?.sites ?? []
})

// 삭제: 서버 연동 구현
const deleteSelected = async () => {
  // 1) 현장 선택(지역+현장)이면 현장만 삭제
  if (selectedRegionId.value && selectedSiteId.value) {
    const confirmMsg = '현장을 지울 시 해당되는 현장근로자와 현장 정보가 지워집니다.'
    if (!confirm(confirmMsg)) return

    try {
      const res = await fetch(`http://localhost:8080/api/site/${selectedSiteId.value}`, {
        method: 'DELETE',
        headers: { Accept: 'application/json' },
        credentials: 'include',
      })
      if (!res.ok) throw new Error(`HTTP ${res.status}`)

      // UI 반영: 해당 지역의 sites에서 제거
      const region = locations.value.find((r) => r.id === selectedRegionId.value)
      if (region && Array.isArray(region.sites)) {
        const sidx = region.sites.findIndex((s) => s.id === selectedSiteId.value)
        if (sidx !== -1) region.sites.splice(sidx, 1)
      }
      // 선택 해제(현장만)
      selectedSiteId.value = null
      selectedSite.value = null
      alert('현장이 삭제되었습니다.')
    } catch (e) {
      alert('현장 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.')
    }
    return
  }

  // 2) 지역만 선택이면 지역 삭제
  if (selectedRegionId.value && !selectedSiteId.value) {
    const confirmMsg = '지역을 지울 시 해당되는 현장과 근로자 현장 정보도 전부 지워집니다.'
    if (!confirm(confirmMsg)) return

    try {
      const res = await fetch(`http://localhost:8080/api/region/${selectedRegionId.value}`, {
        method: 'DELETE',
        headers: { Accept: 'application/json' },
        credentials: 'include',
      })
      if (!res.ok) throw new Error(`HTTP ${res.status}`)

      // UI 반영: 지역 제거
      const idx = locations.value.findIndex((r) => r.id === selectedRegionId.value)
      if (idx !== -1) locations.value.splice(idx, 1)
      // 선택 해제(지역+현장)
      selectedRegionId.value = null
      selectedRegion.value = null
      selectedSiteId.value = null
      selectedSite.value = null
      newRegionName.value = ''
      alert('지역이 삭제되었습니다.')
    } catch (e) {
      alert('지역 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.')
    }
    return
  }

  alert('삭제할 지역 또는 현장을 선택해주세요.')
}

const addLocation = async () => {
  if (submittingAdd.value) return

  // 입력 정규화(공백 제거)
  const regionInput = (newRegionName.value || '').trim()
  const siteInput = (newSiteName.value || '').trim()

  // 1) 지역 + 현장 동시 입력: 기존 지역 여부 확인 → 존재 시 지역 생성 생략, 미존재 시 지역 생성 후 현장 생성
  if (regionInput && siteInput) {
    try {
      submittingAdd.value = true
      // 1-1) 지역 존재 검사
      let baseRegion = locations.value.find((r) => r.name === regionInput)
      let createdNewRegion = false

      // 1-2) 지역 미존재 시 생성
      if (!baseRegion) {
        const resRegion = await fetch('http://localhost:8080/api/locations', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({ name: regionInput }),
        })
        if (!resRegion.ok) throw new Error('지역 추가 실패')
        const createdRegion = await resRegion.json()
        baseRegion = { id: createdRegion.id ?? Date.now(), name: createdRegion.name, sites: createdRegion.sites ?? [] }
        locations.value.push(baseRegion)
        createdNewRegion = true
      }

      // 1-3) 현장 생성 (기존 또는 새 지역 id 사용)
      const regionId = baseRegion.id
      const resSite = await fetch(`http://localhost:8080/api/locations/${regionId}/sites`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ name: siteInput }),
      })
      if (!resSite.ok) throw new Error('현장 추가 실패')
      const createdSite = await resSite.json()

      // 1-4) UI 반영: 대상 지역에 현장 추가
      const regionRef = locations.value.find((r) => r.id === regionId)
      if (regionRef) {
        regionRef.sites = regionRef.sites || []
        regionRef.sites.push({ id: createdSite.id ?? Date.now(), name: createdSite.name ?? siteInput })
      }
      selectedRegion.value = baseRegion.name
      selectedSite.value = createdSite.name ?? siteInput
      newRegionName.value = baseRegion.name
      newSiteName.value = ''
      alert(createdNewRegion ? '지역과 현장이 추가되었습니다.' : '현장이 추가되었습니다.')
    } catch (e) {
      alert('지역/현장 동시 추가에 실패했습니다. 잠시 후 다시 시도해주세요.')
    } finally {
      submittingAdd.value = false
    }
    return
  }

  // 2) 지역만 입력 → 지역 추가
  if (regionInput && !siteInput) {
    try {
      submittingAdd.value = true
      // 기존 지역 여부 확인: 미존재 시에만 생성
      const existing = locations.value.find((r) => r.name === regionInput)
      if (existing) {
        selectedRegion.value = existing.name
        newRegionName.value = existing.name
        alert('이미 존재하는 지역입니다. 해당 지역을 선택했습니다.')
        return
      }

      const res = await fetch('http://localhost:8080/api/locations', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ name: regionInput }),
      })
      if (!res.ok) throw new Error('지역 추가 실패')
      const created = await res.json()
      locations.value.push({ id: created.id ?? Date.now(), name: created.name, sites: created.sites ?? [] })
      selectedRegion.value = created.name
      newRegionName.value = created.name
      alert('지역이 추가되었습니다.')
    } catch (e) {
      alert('지역 추가에 실패했습니다. 잠시 후 다시 시도해주세요.')
    } finally {
      submittingAdd.value = false
    }
    return
  }

  // 3) 현장만 입력 → 지역 선택/생성이 없으면 막기
  if (siteInput && !selectedRegion.value && !regionInput) {
    alert('현장만 추가할 수 없습니다. 먼저 지역을 선택하거나 생성하세요.')
    return
  }

  // 현장 추가 (선택된 지역 또는 입력한 지역명 매칭)
  if (siteInput) {
    try {
      submittingAdd.value = true
      let baseRegion = null
      if (selectedRegion.value) {
        baseRegion = locations.value.find((r) => r.name === selectedRegion.value)
      }
      if (!baseRegion && regionInput) {
        baseRegion = locations.value.find((r) => r.name === regionInput)
        if (baseRegion) selectedRegion.value = baseRegion.name
      }
      if (!baseRegion) {
        alert('선택/입력한 지역을 찾을 수 없습니다. 먼저 지역을 추가하세요.')
        return
      }

      const res = await fetch(`http://localhost:8080/api/locations/${baseRegion.id}/sites`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ name: siteInput }),
      })
      if (!res.ok) throw new Error('현장 추가 실패')
      const createdSite = await res.json()
      const regionRef = locations.value.find((r) => r.id === baseRegion.id)
      if (regionRef) {
        regionRef.sites = regionRef.sites || []
        regionRef.sites.push({ id: createdSite.id ?? Date.now(), name: createdSite.name ?? siteInput })
      }
      selectedSite.value = createdSite.name ?? siteInput
      alert('현장이 추가되었습니다.')
      newSiteName.value = ''
    } catch (e) {
      alert('현장 추가에 실패했습니다. 잠시 후 다시 시도해주세요.')
    } finally {
      submittingAdd.value = false
    }
    return
  }

  // 4) 아무 것도 입력 안 함
  alert('추가할 지역 또는 현장 이름을 입력해주세요.')
}

// 지역 버튼 클릭 처리 (컴파일 오류를 피하기 위해 핸들러로 분리)
const onSelectRegion = (region) => {
  // 토글 선택: 같은 id면 해제
  if (selectedRegionId.value === region.id) {
    selectedRegionId.value = null
    selectedRegion.value = null
    newRegionName.value = ''
  } else {
    selectedRegionId.value = region.id
    selectedRegion.value = region.name
    newRegionName.value = region.name
  }
  // 지역 변경 시 현장 선택 초기화
  selectedSiteId.value = null
  selectedSite.value = null
}

const onSelectSite = (site) => {
  if (selectedSiteId.value === site.id) {
    selectedSiteId.value = null
    selectedSite.value = null
  } else {
    selectedSiteId.value = site.id
    selectedSite.value = site.name
  }
}
</script>
