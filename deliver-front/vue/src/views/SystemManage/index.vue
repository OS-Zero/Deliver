<script lang="ts" setup>
import { useRouter } from 'vue-router';

const router = useRouter()

router.push("systemManage/myAccount")
</script>

<template>
	<RouterView></RouterView>
</template>

<style lang="scss" scoped></style>
