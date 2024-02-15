import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {


      // 使用 proxy 实例
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,// 将header中Origin字段，改成和target中一样的域名
        // 路径重写
        rewrite:(path)=>path.replace(/^\/api/,"")
      },

    },
  },
})
