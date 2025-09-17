<template>
  <div class="flex flex-col justify-center items-center h-screen bg-[#FFFEEF]">
    <div class="flex w-full px-5 justify-between">
      <p>홍길동님 환영합니다</p>
      <p>로그아웃</p>
    </div>
    <img src="/doldari.png" class="w-50 h-50" />
    <div class="flex flex-col items-center gap-5">
      <router-link to="/safety" class="bg-black text-yellow-300 text-3xl py-10 px-20 font-bold rounded-xl">안전교육</router-link>
      <router-link to="/clear" class="bg-black text-yellow-300 text-3xl py-10 px-20 font-bold rounded-xl">사고조치</router-link>
      <button class="bg-red-500 text-white text-3xl py-3 px-20 rounded-xl my-5">긴급전화</button>
    </div>
  </div>
</template>

<script setup></script>
