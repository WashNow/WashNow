import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://deti-tqs-11.ua.pt:8080',
        changeOrigin: true,
        secure: false
      }
    }
  }
})