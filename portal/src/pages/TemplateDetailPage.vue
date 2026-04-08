<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
import { templates } from '@/data/templates'

const route = useRoute()
const router = useRouter()
const template = computed(() => templates.find(t => t.slug === route.params.slug))
</script>

<template>
  <div class="min-h-screen bg-white" v-if="template">
    <Navbar />

    <!-- Hero -->
    <section class="pt-24 bg-gradient-to-br from-primary-900 via-primary to-gold-700">
      <div class="max-w-7xl mx-auto px-6 py-16 flex gap-16 items-center">
        <div class="flex-1">
          <div class="flex items-center gap-3 mb-4">
            <span class="text-xs px-3 py-1 rounded-full bg-white/20 text-white">{{ template.category }}</span>
            <span v-if="template.badge" class="text-xs px-3 py-1 rounded-full bg-gold text-primary-900 font-bold">{{ template.badge }}</span>
          </div>
          <h1 class="text-5xl font-bold text-white font-display mb-4">{{ template.name }}</h1>
          <p class="text-xl text-white/70 mb-8">{{ template.tagline }}</p>
          <div class="flex gap-4">
            <button class="px-8 py-3 bg-gold text-primary-900 rounded-full font-bold hover:bg-gold-400 transition-all shadow-xl"
              @click="router.push('/contact')">
              立即咨询
            </button>
            <a v-if="template.demoUrl"
              class="px-8 py-3 bg-white/10 text-white rounded-full font-medium hover:bg-white/20 border border-white/30 inline-flex items-center"
              :href="template.demoUrl" target="_blank">
              查看演示
            </a>
          </div>
        </div>
        <div class="w-96 h-64 rounded-2xl overflow-hidden shadow-2xl flex-shrink-0">
          <img :src="template.coverImage" :alt="template.name" class="w-full h-full object-cover" />
        </div>
      </div>
    </section>

    <div class="max-w-7xl mx-auto px-6 py-16">
      <div class="grid grid-cols-3 gap-12">
        <!-- Main Content -->
        <div class="col-span-2">
          <h2 class="text-2xl font-bold text-gray-900 mb-4">活动介绍</h2>
          <p class="text-gray-600 leading-relaxed text-lg mb-10">{{ template.description }}</p>

          <h2 class="text-2xl font-bold text-gray-900 mb-6">功能特色</h2>
          <div class="grid grid-cols-2 gap-4 mb-10">
            <div v-for="feat in template.features" :key="feat"
              class="flex items-center gap-3 p-4 bg-gray-50 rounded-xl">
              <span class="w-8 h-8 rounded-full bg-primary-100 text-primary flex items-center justify-center text-sm">✓</span>
              <span class="text-gray-700">{{ feat }}</span>
            </div>
          </div>

          <h2 class="text-2xl font-bold text-gray-900 mb-6">适用场景</h2>
          <div class="flex flex-wrap gap-3">
            <span v-for="scene in template.scenes" :key="scene"
              class="px-4 py-2 bg-gold-50 text-gold-800 rounded-full text-sm font-medium border border-gold-200">
              {{ scene }}
            </span>
          </div>
        </div>

        <!-- Sidebar -->
        <div>
          <div class="sticky top-24 bg-gray-50 rounded-2xl p-8">
            <div class="text-3xl font-bold text-primary mb-1">{{ template.price }}</div>
            <p class="text-gray-500 text-sm mb-6">含一年使用权 + 技术支持</p>
            <button class="w-full py-3 bg-primary text-white rounded-xl font-bold hover:bg-primary-700 transition-all mb-3"
              @click="router.push('/contact')">
              立即咨询
            </button>
            <button class="w-full py-3 bg-white text-gray-700 rounded-xl font-medium border border-gray-200 hover:border-primary hover:text-primary transition-all">
              预约演示
            </button>
            <div class="mt-6 pt-6 border-t border-gray-200 space-y-3 text-sm text-gray-500">
              <div class="flex items-center gap-2"><span>✅</span> 独立管理后台</div>
              <div class="flex items-center gap-2"><span>✅</span> 数据统计分析</div>
              <div class="flex items-center gap-2"><span>✅</span> 专属客服支持</div>
              <div class="flex items-center gap-2"><span>✅</span> 自定义品牌形象</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <Footer />
  </div>
</template>
