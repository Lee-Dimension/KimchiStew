<template>
  <div class="flex flex-col justify-center items-center h-screen bg-[#FFFEEF]">
    <router-link to="/main" class="absolute top-10 left-5">
      <img src="/clear/left.png" alt="logo" class="w-10 h-10" />
    </router-link>

    <div class="flex w-full px-5 justify-center my-10 pt-10">
      <p class="text-5xl text-[#78711D] font-bold">사고조치</p>
    </div>

    <div class="grid grid-cols-2 mb-5 gap-5">
      <!-- 추락 -->
      <router-link to="#">
        <img src="/clear/falling.png" alt="추락" class="w-30" />
      </router-link>

      <!-- 끼임 -->
      <router-link to="#">
        <img src="/clear/collapse.png" alt="끼임" class="w-30" />
      </router-link>

      <!-- 감전 -->
      <router-link to="#">
        <img src="/clear/elect.png" alt="감전" class="w-30" />
      </router-link>

      <!-- 넘어짐 -->
      <router-link to="#">
        <img src="/clear/fall.png" alt="넘어짐" class="w-30" />
      </router-link>

      <!-- 낙하물 -->
      <router-link to="#">
        <img src="/clear/hit.png" alt="낙하물" class="w-30" />
      </router-link>

      <!-- 열사병탈진 -->
      <router-link to="#">
        <img src="/clear/sun.png" alt="열사병탈진" class="w-33" />
      </router-link>

      <!-- 화재 -->
      <router-link to="#">
        <img src="/clear/fire.png" alt="화재" class="w-30" />
      </router-link>

      <!-- 붕괴 -->
      <router-link to="#">
        <img src="/clear/break.png" alt="붕괴" class="w-30" />
      </router-link>
    </div>
  </div>
</template>

<script setup></script>
