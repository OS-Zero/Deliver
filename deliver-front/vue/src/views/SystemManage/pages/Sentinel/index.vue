<script lang="ts" setup>
import { ref } from 'vue';

const iframeSrc = ref('https://oszero.cn/');
</script>

<template>
  <div class="iframe-container">
    <a-input class="iframe-input" placeholder="请输入网址" v-model:value="iframeSrc"></a-input>
    <iframe :src="iframeSrc" frameborder="0" width="100%" height="100%"></iframe>
  </div>
</template>

<style lang="scss" scoped>
.iframe-container {
  position: relative;
}

.iframe-input {
  width: 300px;
  position: absolute;
  right: 0;
  top: -36px;
}
</style>