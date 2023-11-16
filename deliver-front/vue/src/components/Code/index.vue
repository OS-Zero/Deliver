<script setup lang="ts">
import { onMounted } from 'vue'
import Prism from 'prismjs'
defineProps({
  code: {
    type: String,
    default: ''
  },
  type: {
    type: String,
    default: 'html'
  }
})
onMounted(() => {
  Prism.highlightAll()
})
</script>
<template>
  <pre>
    <code :class="'language-'+ type" v-html="Prism.highlight(code, Prism.languages[type], type)"  data-prismjs-copy="复制"></code>
 </pre>
</template>
<style lang="scss" scoped>
pre {
  width: 100%;
}
pre[class*='language-']::selection,
pre[class*='language-'] ::selection,
code[class*='language-']::selection,
code[class*='language-'] ::selection {
  background: #f8f8f8;
}
</style>
