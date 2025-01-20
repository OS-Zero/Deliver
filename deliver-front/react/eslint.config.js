import globals from "globals";
import pluginJs from "@eslint/js";
import tseslint from "typescript-eslint";
import pluginReact from "eslint-plugin-react";
import eslintConfigPrettier from 'eslint-config-prettier' // 新增

/** @type {import('eslint').Linter.Config[]} */
export default [
  {
    settings: {
      react: {
        version: "detect"
      }
    },
  },
  {files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"]},
  {languageOptions: { globals: {...globals.browser, ...globals.node} }},
  pluginJs.configs.recommended,
  ...tseslint.configs.recommended,
  pluginReact.configs.flat.recommended,
  eslintConfigPrettier,
  {
    rules: {
      "react/react-in-jsx-scope": "off",
      "react/jsx-uses-react": "off",
      "no-multi-spaces": "error", // 检测其他的多余空格
      "no-trailing-spaces": "error", // 检测行尾的多余空格
      "@typescript-eslint/no-explicit-any": ["off"], // 隐藏any报错
      "react/prop-types": "off", // 隐藏prop-types报错
    }
  },
];