<script lang="ts" setup>
defineProps(['itemKey', 'info', 'to'])
</script>
<template>
  <div class="item-link">
    <a-menu-item :key="itemKey">
      <RouterLink :to="to">{{ info }}</RouterLink>
    </a-menu-item>
  </div>
</template>
<style lang="scss" scoped></style>
