/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: { 50: '#fff1f1', 100: '#ffe1e1', 200: '#ffc7c7', 300: '#ffa0a0', 400: '#ff6b6b', 500: '#f83b3b', 600: '#e51d1d', 700: '#c11414', 800: '#a01414', 900: '#841818', DEFAULT: '#e51d1d' },
        gold: { 50: '#fefbe8', 100: '#fff8c2', 200: '#ffed89', 300: '#ffdc45', 400: '#ffc912', 500: '#e6ad00', 600: '#c78600', 700: '#9e5f00', 800: '#824a08', 900: '#6b3d0c', DEFAULT: '#e6ad00' },
      },
      fontFamily: {
        display: ['"Noto Serif SC"', 'serif'],
        body: ['"Noto Sans SC"', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
