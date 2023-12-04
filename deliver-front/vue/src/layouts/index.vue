<script lang="ts" setup></script>

<template>
	<a-layout style="min-height: 100%">
		<div style="position: fixed; z-index: 999; width: 100%; border-bottom: 1px solid #ececec">
			<Banner></Banner>
			<Header></Header>
		</div>
		<a-layout>
			<Section></Section>
		</a-layout>
	</a-layout>
</template>
<style scoped lang="scss"></style>
