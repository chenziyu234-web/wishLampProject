<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const scrolled = ref(false)

function onScroll() {
  scrolled.value = window.scrollY > 20
}

onMounted(() => window.addEventListener('scroll', onScroll))
onUnmounted(() => window.removeEventListener('scroll', onScroll))
</script>

<template>
  <nav
    class="fixed top-0 left-0 right-0 z-50 transition-all duration-300"
    :class="scrolled ? 'bg-white/95 backdrop-blur-md shadow-md' : 'bg-transparent'"
  >
    <div class="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
      <div class="flex items-center gap-2 cursor-pointer" @click="router.push('/')">
        <span class="text-2xl">🏮</span>
        <span class="text-xl font-bold font-display" :class="scrolled ? 'text-primary' : 'text-white'">
          WishLamp
        </span>
      </div>
      <div class="flex items-center gap-8">
        <router-link
          v-for="item in [
            { to: '/', label: '首页' },
            { to: '/templates', label: '活动模板' },
            { to: '/contact', label: '联系我们' },
          ]"
          :key="item.to"
          :to="item.to"
          class="text-sm font-medium transition-colors"
          :class="[
            route.path === item.to
              ? (scrolled ? 'text-primary' : 'text-gold')
              : (scrolled ? 'text-gray-600 hover:text-primary' : 'text-white/80 hover:text-white')
          ]"
        >
          {{ item.label }}
        </router-link>
        <button
          class="px-5 py-2 rounded-full text-sm font-medium transition-all"
          :class="scrolled
            ? 'bg-primary text-white hover:bg-primary-700 shadow-lg shadow-primary/20'
            : 'bg-white/20 text-white hover:bg-white/30 backdrop-blur-sm border border-white/30'"
          @click="router.push('/contact')"
        >
          免费咨询
        </button>
      </div>
    </div>
  </nav>
</template>
