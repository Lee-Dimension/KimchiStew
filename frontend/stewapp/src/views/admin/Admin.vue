<template>
  <div class="relative flex flex-col justify-center items-center h-screen bg-[#FFFEEF]">
    <div class="absolute top-5 flex w-full px-5 justify-between">
      <p></p>
      <router-link to="/adminlogin">
        <p class="font-bold text-[#78711D]">로그아웃</p>
      </router-link>
    </div>

    <div class="flex flex-col justify-left w-full px-10 gap-2 mb-10 mt-10">
      <h1 class="font-bold text-[#78711D] text-5xl">홍길동님</h1>
      <h2 class="font-bold text-[#E3D435] text-2xl">환영합니다</h2>
    </div>

    <router-link to="/adminplus">
      <div class="bg-[#D2C30F] text-white mx-20 w-70 h-30 rounded-2xl flex justify-center items-center text-3xl font-bold mb-10">
        <p>현장 등록</p>
      </div>
    </router-link>

    <router-link to="/adminAddEdu">
      <div class="bg-[#D2C30F] text-white mx-20 w-70 h-30 rounded-2xl flex justify-center items-center text-3xl font-bold mb-10">
        <p>교육등록</p>
      </div>
    </router-link>
  </div>
</template>

<script setup></script>

<style scoped></style>
