<script lang="ts" setup></script>
<template>
  <div
    data-show="true"
    role="alert"
    style="
      height: 25px;
      padding: 0px;
      background: repeating-linear-gradient(
        35deg,
        rgb(179, 235, 255),
        rgb(179, 235, 255) 20px,
        rgb(230, 248, 255) 10px,
        rgb(230, 248, 255) 40px
      );
      position: fixed;
      width: 100%;
      z-index: 1;
    "
  >
    <div>
      <div>
        <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; text-align: center; color: black">
          <span
            >⭐️ 如果你喜欢 Deliver 消息推送平台，请给它一个 Star&nbsp;
            <a target="_blank" rel="noopener noreferrer" href="https://gitee.com/OS-Zero/deliver">Gitee</a>
            ，你的支持将是我们前行的动力，项目正在积极开发，欢迎大家提交 PR、提供建设,共建社区生态。👏🏻</span
          >
        </div>
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
span {
  text-align: center;
  font-size: 14px;
  line-height: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ico {
  width: 25px;
  height: 25px;
}
</style>
