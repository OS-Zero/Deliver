module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  extends: ['standard-with-typescript', 'plugin:vue/vue3-essential', 'prettier'],
  overrides: [
    {
      env: {
        node: true
      },
      files: ['.eslintrc.{js,cjs}'],
      parserOptions: {
        sourceType: 'script'
      }
    }
  ],
  parser: 'vue-eslint-parser', // 新增
  parserOptions: {
    tsconfigRootDir: __dirname,
    parser: '@typescript-eslint/parser', // 新增
    project: ['tsconfig.json'],
    extraFileExtensions: ['.vue'],
    ecmaVersion: 'latest',
    sourceType: 'module'
  },
  plugins: ['vue', 'prettier'],
  rules: {
    'prettier/prettier': 'error',
    'vue/multi-word-component-names': 'off'
  }
}
