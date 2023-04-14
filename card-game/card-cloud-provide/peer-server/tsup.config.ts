import { defineConfig } from 'tsup'

export default defineConfig({
  entry: ['./src/index.ts'],
  format: ['esm'],
  target: 'esnext',
  splitting: true,
  dts: false,
  tsconfig: './tsconfig.json',
  clean: true,
  minify: true,
})
