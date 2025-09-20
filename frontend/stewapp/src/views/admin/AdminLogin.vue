<template>
  <div class="relative flex flex-col justify-center items-center h-screen bg-[#FFFEEF]">
    <div class="absolute top-5 flex w-full px-5 justify-between">
      <p></p>
      <router-link to="/" class="font-bold text-[#78711D]">일반 로그인</router-link>
    </div>

    <router-link to="/admin">
      <img src="/doldari.png" class="w-50 h-50" />
    </router-link>

    <form action="" class="flex flex-col w-70">
      <!-- ID -->
      <div class="flex items-center w-full mb-4">
        <label for="name" class="w-10 font-bold text-right pr-5 text-[#767676]">ID</label>
        <input type="text" id="name" class="text-[#999999] flex-1 p-2 border rounded-full text-left bg-[#FFFDE3]" />
      </div>

      <!-- PW -->
      <div class="flex items-center w-full mb-25">
        <label for="phone" class="w-10 font-bold text-right pr-5 text-[#767676]">PW</label>
        <input type="tel" id="phone" class="text-[#999999] flex-1 p-2 border rounded-full text-left bg-[#FFFDE3]" />
      </div>

      <!-- 로그인 -->
      <button type="submit" class="w-full bg-[#FFEC17] hover:bg-yellow-300 text-black text-lg mt-15 font-bold py-1 rounded-2xl border border-[#78711D]">로그인</button>
    </form>
  </div>
</template>

<script setup></script>

<style scoped></style>
