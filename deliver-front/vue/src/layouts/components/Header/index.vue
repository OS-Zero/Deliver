<script lang="ts" setup>
import { UserOutlined } from '@ant-design/icons-vue'
</script>
<template>
  <a-layout-header class="header">
    <i class="ico"></i>
    <h1>Deliver 企业消息推送平台</h1>
    <div class="user">
      <a-avatar :size="40" src="tx.jpg" :style="{ marginRight: '3px' }">
        <template #icon><UserOutlined /></template>
      </a-avatar>
      admin
    </div>
    <svg
      :style="{ marginRight: '20px' }"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      viewBox="64 64 896 896"
      width="24"
      height="24"
      style="border-color: rgba(187, 187, 187, 1); border-width: 0px; border-style: solid"
      filter="none"
    >
      <g>
        <path
          d="M511.6 76.3C264.3 76.2 64 276.4 64 523.5 64 718.9 189.3 885 363.8 946c23.5 5.9 19.9-10.8 19.9-22.2v-77.5c-135.7 15.9-141.2-73.9-150.3-88.9C215 726 171.5 718 184.5 703c30.9-15.9 62.4 4 98.9 57.9 26.4 39.1 77.9 32.5 104 26 5.7-23.5 17.9-44.5 34.7-60.8-140.6-25.2-199.2-111-199.2-213 0-49.5 16.3-95 48.3-131.7-20.4-60.5 1.9-112.3 4.9-120 58.1-5.2 118.5 41.6 123.2 45.3 33-8.9 70.7-13.6 112.9-13.6 42.4 0 80.2 4.9 113.5 13.9 11.3-8.6 67.3-48.8 121.3-43.9 2.9 7.7 24.7 58.3 5.5 118 32.4 36.8 48.9 82.7 48.9 132.3 0 102.2-59 188.1-200 212.9a127.5 127.5 0 0 1 38.1 91v112.5c.8 9 0 17.9 15 17.9 177.1-59.7 304.6-227 304.6-424.1 0-247.2-200.4-447.3-447.5-447.3z"
          fill="rgba(108.11999999999999,108.11999999999999,108.11999999999999,1)"
        ></path>
      </g>
    </svg>
    <svg
      :style="{ marginRight: '10px' }"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      viewBox="0 0 32 32"
      width="24"
      height="24"
      style="border-color: rgba(187, 187, 187, 1); border-width: 0px; border-style: solid"
      filter="none"
    >
      <g>
        <path
          d="M16 1.778c7.854 0 14.222 6.368 14.222 14.222s-6.368 14.222-14.222 14.222c-7.854 0-14.222-6.368-14.222-14.222s6.368-14.222 14.222-14.222zM15.873 20.318c-0.409 0-0.743 0.117-0.996 0.391-0.295 0.252-0.432 0.587-0.432 0.996 0 0.391 0.137 0.724 0.432 0.997 0.252 0.272 0.587 0.409 0.996 0.409 0.393 0 0.743-0.137 1.036-0.389 0.272-0.276 0.409-0.606 0.409-1.017 0-0.409-0.137-0.743-0.409-0.996-0.274-0.274-0.626-0.391-1.036-0.391zM16.207 8.889c-1.428 0-2.56 0.409-3.38 1.25-0.841 0.821-1.25 1.934-1.25 3.362v0.032c0 0.574 0.462 1.038 1.036 1.038 0.572 0 1.036-0.464 1.036-1.038 0-0.012-0.004-0.032-0.004-0.032 0-0.862 0.174-1.543 0.526-2.012 0.391-0.548 1.035-0.821 1.916-0.821 0.702 0 1.25 0.196 1.639 0.587 0.372 0.391 0.569 0.919 0.569 1.602 0 0.508-0.178 0.976-0.53 1.426l-0.332 0.372c-1.209 1.074-1.931 1.888-2.228 2.421-0.443 0.798-0.363 1.536-0.363 1.536 0 0.572 0.466 1.036 1.038 1.036 0.574 0 0.953-0.395 1.036-1.036 0 0 0.107-0.597 0.322-0.988 0.176-0.35 0.45-0.684 0.8-0.996 0.94-0.802 1.486-1.31 1.66-1.525 0.469-0.624 0.722-1.426 0.722-2.382 0-1.172-0.389-2.11-1.15-2.795-0.782-0.702-1.797-1.035-3.067-1.035z"
          fill="rgba(108.11999999999999,108.11999999999999,108.11999999999999,1)"
        ></path>
      </g>
    </svg>
    <svg
      :style="{ marginRight: '10px' }"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      viewBox="0 0 32 32"
      width="24"
      height="24"
      style="border-color: rgba(187, 187, 187, 1); border-width: 0px; border-style: solid"
      filter="none"
    >
      <g>
        <path
          d="M16 2c-7.8 0-14 6.2-14 14s6.2 14 14 14 14-6.2 14-14-6.2-14-14-14zM17 24h-2v-2h2v2zM17 20h-2v-12h2v12z"
          fill="rgba(108.11999999999999,108.11999999999999,108.11999999999999,1)"
        ></path>
      </g>
    </svg>
  </a-layout-header>
</template>
<style lang="scss" scoped>
.header {
  background: #ffffff;
  border-bottom: 1px solid #dddddd;
  border: none;
  padding: 0;
  height: 60px;
}
.ico {
  background-image: url(./static/头部-logo.ico);
  width: 56px;
  height: 59px;
  display: inline;
  margin-left: 50px;
  float: left;
}
h1 {
  font-family: 'Noto Sans SC';
  font-weight: 700;
  font-size: 26px;
  float: left;
}
.user {
  margin-right: 25px;
  border-left-style: solid;
  border-width: 1px;
  border-color: #c0c0c0;
  line-height: 60px;
  padding-left: 10px;
  width: 100px;
  height: 60px;
  font-weight: 500;
  float: right;
}
svg {
  float: right;
  line-height: 60px;
  height: 60px;
}
</style>
