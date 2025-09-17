<template>
  <div class="flex flex-col justify-center items-center h-screen bg-[#FFFEEF]">
    <img src="/doldari.png" class="w-50 h-50" />
    <div class="flex space-x-15 my-4">
      <div class="flex flex-col items-start">
        <label for="region" class="mb-2 font-bold text-[#767676]">지역</label>
        <select id="region" class="p-1 border-b w-25 text-[#767676]">
          <option selected disabled></option>
          <option value="seoul">서울</option>
          <option value="busan">부산</option>
          <option value="incheon">인천</option>
        </select>
      </div>
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

    <form action="" class="flex flex-col mt-4 w-70">
      <div class="flex items-center w-full mb-4">
        <label for="name" class="w-24 font-bold text-right pr-5 text-[#767676]">이름</label>
        <input type="text" id="name" placeholder="홍길동" class="text-[#999999] flex-1 p-2 border rounded-full text-left bg-[#FFFDE3]" />
      </div>

      <div class="flex items-center w-full mb-6">
        <label for="phone" class="w-24 font-bold text-right pr-5 text-[#767676]">전화번호</label>
        <input type="tel" id="phone" placeholder="01012341234" class="text-[#999999] flex-1 p-2 border rounded-full text-left bg-[#FFFDE3]" />
      </div>

      <button type="submit" class="w-full bg-[#FFEC17] hover:bg-yellow-300 text-black text-lg my-5 py-2 px-4 rounded-xl border border-[#78711D]">로그인</button>
    </form>
  </div>
</template>

<script setup></script>

<style scoped></style>
