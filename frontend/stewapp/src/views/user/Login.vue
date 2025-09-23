<template>
  <!-- 전체 배경 설정 -->
  <div class="background-out">
    <div class="background-in">
      <!-- 상단 내비게이션 설정 -->
      <div class="head-setting">
        <p></p>
        <router-link to="/adminlogin" class="heed-text">관리자 로그인</router-link>
      </div>

      <!-- 중앙 설정 -->
      <main class="main-setting">
        <!-- 로고 -->
        <img src="/doldari.png" class="w-50 h-50" />

        <!-- 지역/현장 -->
        <div class="flex justify-center gap-4 w-full px-5 mb-3">
          <!-- 지역 -->
          <div class="flex flex-col items-center flex-1">
            <label for="region" class="mb-2 font-bold text-[#767676] w-25 text-left">지역</label>
            <select id="region" v-model="region" class="p-1 border-b w-25 text-[#767676]">
              <option disabled value="">지역 선택</option>
              <option v-for="r in locations" :key="r.id ?? r.name" :value="r.name">{{ r.name }}</option>
            </select>
            <small v-if="locationsLoading" class="text-gray-400 mt-1">지역 불러오는 중...</small>
            <small v-else-if="locationsError" class="text-red-500 mt-1">지역 목록을 불러오지 못했습니다.</small>
          </div>

          <!-- 현장 -->
          <div class="flex flex-col items-center flex-1">
            <label for="site" class="mb-2 font-bold text-[#767676] w-25 text-left">현장</label>
            <select id="site" v-model="site" class="p-1 border-b w-25 text-[#767676]" :disabled="!region">
              <option disabled value="">현장 선택</option>
              <option v-for="s in filteredSites" :key="s.id ?? s.name" :value="s.name">{{ s.name }}</option>
            </select>
          </div>
        </div>

        <!-- 이름/전화번호 -->
        <div class="flex flex-col items-center">
          <form @submit.prevent="login" class="flex flex-col mt-4 w-70">
            <!-- 이름 입력 -->
            <div class="flex flex-col w-full mb-4">
              <label for="name" class="input-label">이름</label>
              <input v-model="name" type="text" id="name" placeholder="홍길동" class="input-box" />
            </div>

            <!-- 전화번호 입력 -->
            <div class="flex flex-col w-full mb-4">
              <label for="phone" class="input-label">전화번호</label>
              <input v-model="phone" type="tel" id="phone" placeholder="01012341234" class="input-box" />
            </div>
            <p v-if="loginError" class="text-red-500 text-center mb-5">입력하신 정보가 일치하지 않습니다</p>
            <div v-else class="mb-5"></div>

            <!-- 로그인 -->
            <button type="submit" :disabled="isButtonDisabled" :class="{ 'bg-gray-300': isButtonDisabled, 'bg-[#FFEC17] hover:bg-yellow-300': !isButtonDisabled }" class="login-button">로그인</button>
          </form>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 선택 값
const region = ref('')
const site = ref('')
const name = ref('')
const phone = ref('')
const loginError = ref(false)

// 지역/현장 목록 상태
const locations = ref([])
const locationsLoading = ref(false)
const locationsError = ref(false)

onMounted(async () => {
  locationsLoading.value = true
  locationsError.value = false
  try {
    const res = await fetch('http://localhost:8080/api/locations', {
      method: 'GET',
      headers: { Accept: 'application/json' },
    })
    if (!res.ok) throw new Error(`HTTP ${res.status}`)
    const data = await res.json()
    if (Array.isArray(data) && data.length > 0) {
      locations.value = data
    } else {
      locations.value = []
      locationsError.value = true
    }
  } catch (e) {
    locations.value = []
    locationsError.value = true
  } finally {
    locationsLoading.value = false
  }
})

// 지역 선택에 따른 현장 필터링
const filteredSites = computed(() => {
  const selected = locations.value.find((r) => r.name === region.value)
  return selected?.sites ?? []
})

// 지역 변경되면 현장 초기화
watch(region, () => {
  site.value = ''
})

const isButtonDisabled = computed(() => {
  return !region.value || !site.value || !name.value || !phone.value
})

const login = async () => {
  if (isButtonDisabled.value) return

  loginError.value = false
  try {
    const res = await fetch('http://localhost:8080/api/user/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({
        region: region.value,
        site: site.value,
        name: name.value,
        phone: phone.value,
      }),
    })

    if (!res.ok) {
      // 401/404 등 실패 처리
      loginError.value = true
      return
    }

    const data = await res.json().catch(() => ({}))
    // 성공 시 서버에서 내려주는 사용자(또는 토큰) 정보를 세션에 저장
    sessionStorage.setItem(
      'loggedInUser',
      JSON.stringify(
        data || {
          region: region.value,
          site: site.value,
          name: name.value,
          phone: phone.value,
        },
      ),
    )
    router.push('/main')
  } catch (e) {
    loginError.value = true
  }
}
</script>
