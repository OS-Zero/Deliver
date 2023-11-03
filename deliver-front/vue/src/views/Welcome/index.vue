<script lang="ts" setup>
import { ref } from 'vue'
interface Card {
  title: string
  desc: string
}
const cards = ref<Card[]>([
  {
    title: '入门文档',
    desc: '入门文档'
  },
  {
    title: '绑定现有账户系统',
    desc: '快速导入账户，并复用现有的登录方式。'
  },
  {
    title: '集成身份提供商',
    desc: '集成身份提供商，快速纳入三方平台登录。'
  },
  {
    title: '实现应用单点登录',
    desc: '创建配置应用，实现应用单点登录。'
  },
  {
    title: '系统配置',
    desc: '进行系统基础配置，如文件存储、地理位置、邮件短信服务。'
  },
  {
    title: '安全配置',
    desc: '进行系统安全配置，如密码策略、会话策略，完善系统安全。'
  }
])
</script>
<template>
  <div id="welcome-container">
    <h1>欢迎使用Deliver 企业消息推送平台</h1>
    <p>
      Lorem ipsum dolor sit amet consectetur adipisicing elit. Consequatur obcaecati dicta illo, dolor natus nulla sunt
      sapiente cumque distinctio fuga officiis harum rerum unde modi nisi deserunt dignissimos aliquid odio, recusandae,
      iusto itaque aperiam voluptatem. Culpa suscipit pariatur ut, dolores unde aperiam accusantium nulla cum nostrum,
      debitis reiciendis, sequi labore.
    </p>
    <a-row :gutter="[16, 16]">
      <a-col class="gutter-row" :span="8" v-for="item in cards" :key="item">
        <a-card style="height: 100%">
          <a-card-meta :title="item.title" :description="item.desc">
            <template #avatar>
              <a-avatar
                src="data:image/jpeg;base64,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"
              />
            </template>
          </a-card-meta>
          <template #actions>
            <a-button type="link">了解更多</a-button>
          </template>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>
<style lang="scss" scoped>
#welcome-container {
  border-radius: 6px;
  background-image: linear-gradient(to bottom, #eff3ff, #f0f5ff);
  padding: 24px;
  h1 {
    font-size: 20px;
  }
  & > p {
    width: 65%;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
    line-height: 22px;
    margin-top: 16px;
    margin-bottom: 32px;
  }
}
</style>
