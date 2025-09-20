<template>
  <div class="relative flex flex-col items-center justify-center min-h-screen bg-[#FFFEEF] p-5">
    <!-- 상단 버튼 -->
    <div class="absolute top-5 right-5">
      <router-link to="/adminlogin" class="font-bold text-[#78711D]">관리자 로그인</router-link>
    </div>

    <!-- 로고 -->
    <router-link to="/main">
      <img src="/doldari.png" class="w-50 h-50" />
    </router-link>

    <!-- 지역/현장 -->
    <div class="flex justify-between w-full px-5 mb-3">
      <!-- 지역 -->
      <div class="flex flex-col items-start">
        <label for="region" class="mb-2 font-bold text-[#767676]">지역</label>
        <select id="region" class="p-1 border-b w-25 text-[#767676]">
          <option selected disabled></option>
          <option value="seoul">서울</option>
          <option value="busan">부산</option>
          <option value="incheon">인천</option>
        </select>
      </div>

      <!-- 현장 -->
      <div class="flex flex-col items-start">
        <label for="site" class="mb-2 font-bold text-[#767676]">현장</label>
        <select id="site" class="p-1 border-b w-25 text-[#767676]">
          <option selected disabled></option>
          <option value="siteA">현장 A</option>
          <option value="siteB">현장 B</option>
          <option value="siteC">현장 C</option>
        </select>
      </div>
    </div>

    <!-- 이름/전화번호 -->
    <form action="" class="flex flex-col mt-4 w-70">
      <!-- 이름 -->
      <div class="flex items-center w-full mb-4">
        <label for="name" class="w-24 font-bold text-right pr-5 text-[#767676]">이름</label>
        <input type="text" id="name" placeholder="홍길동" class="text-[#999999] flex-1 p-2 border rounded-full text-left bg-[#FFFDE3]" />
      </div>

      <!-- 전화번호 -->
      <div class="flex items-center w-full mb-20">
        <label for="phone" class="w-24 font-bold text-right pr-5 text-[#767676]">전화번호</label>
        <input type="tel" id="phone" placeholder="01012341234" class="text-[#999999] flex-1 p-2 border rounded-full text-left bg-[#FFFDE3]" />
      </div>

      <!-- 로그인 -->
      <button type="submit" class="w-full bg-[#FFEC17] hover:bg-yellow-300 text-black text-lg font-bold py-1 rounded-2xl border border-[#78711D]">로그인</button>
    </form>
  </div>
</template>

<script setup></script>

<style scoped></style>
