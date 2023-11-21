<script lang="ts" setup>
import Header from './components/Header/index.vue'
import Banner from '../components/Banner/index.vue'
import Section from './components/Section/index.vue'
</script>

<template>
	<a-layout style="min-height: 100%">
		<Banner></Banner>
		<Header></Header>
		<a-layout>
			<Section></Section>
		</a-layout>
	</a-layout>
</template>
<style scoped lang="scss"></style>
