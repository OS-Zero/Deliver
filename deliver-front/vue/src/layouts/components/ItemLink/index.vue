<script lang="ts" setup>
defineProps(['itemKey', 'info', 'to'])
</script>
<template>
  <div class="item-link">
    <RouterLink :to="to">
      <KeepAlive
        ><a-menu-item :key="itemKey">{{ info }} </a-menu-item>
      </KeepAlive>
    </RouterLink>
  </div>
</template>
<style lang="scss" scoped></style>
