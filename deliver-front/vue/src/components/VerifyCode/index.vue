<script lang="ts" setup>
const props = withDefaults(defineProps<{
  value: string | number,
  disabled?: boolean,
  submit?: () => void
}>(), {
  disabled: false
})
const emit = defineEmits(['update:value', 'submit'])

import { reactive } from 'vue';
const CLOCK = 5;
const state = reactive({
  verifyContent: '获取验证码',
  clock: CLOCK,
  loading: false,
});
const handleVarify = () => {
  props.submit && props.submit() || emit('submit')
  state.loading = true;
  state.verifyContent = `${state.clock}s 后获取`;
  const t = setInterval(() => {
    if (state.clock > 0) {
      state.verifyContent = `${state.clock--}s 后获取`;
    } else {
      state.clock = CLOCK;
      state.verifyContent = '获取验证码';
      state.loading = false;
      clearInterval(t);
    }
  }, 1000);
};

</script>

<template>
  <div class="verify">
    <a-input :value="value" @change="(e) => emit('update:value', e.target.value)" placeholder="请输入验证码" :maxlength="6" />
    <a-button class="verify_btn" :disabled="disabled || state.loading" @click="handleVarify">
      {{ state.verifyContent }}
    </a-button>
  </div>
</template>

<style lang="scss" scoped>
.verify {
  display: flex;

  .verify_btn {
    margin-left: var(--spacing-sm)
  }
}
</style>