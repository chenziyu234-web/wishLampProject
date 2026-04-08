<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
import { templates, categories } from '@/data/templates'

const router = useRouter()
const activeCategory = ref('全部')
const filteredTemplates = computed(() => {
  if (activeCategory.value === '全部') return templates
  return templates.filter(t => t.category === activeCategory.value)
})
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <Navbar />

    <!-- Header -->
    <section class="pt-24 pb-12 bg-gradient-to-br from-primary-900 via-primary to-gold-700">
      <div class="max-w-7xl mx-auto px-6 pt-8">
        <h1 class="text-4xl font-bold text-white font-display">活动模板</h1>
        <p class="text-white/60 mt-3 text-lg">选择一个模板，快速开启您的互动活动</p>
      </div>
    </section>

    <!-- Filters -->
    <div class="max-w-7xl mx-auto px-6 -mt-5">
      <div class="bg-white rounded-xl shadow-sm p-2 flex gap-2">
        <button
          v-for="cat in categories"
          :key="cat"
          class="px-5 py-2 rounded-lg text-sm font-medium transition-all"
          :class="activeCategory === cat
            ? 'bg-primary text-white shadow-md'
            : 'text-gray-600 hover:bg-gray-100'"
          @click="activeCategory = cat"
        >
          {{ cat }}
        </button>
      </div>
    </div>

    <!-- Grid -->
    <div class="max-w-7xl mx-auto px-6 py-12">
      <div class="grid grid-cols-3 gap-8">
        <div
          v-for="tmpl in filteredTemplates"
          :key="tmpl.id"
          class="group bg-white rounded-2xl overflow-hidden shadow-sm hover:shadow-xl transition-all duration-300 cursor-pointer hover:-translate-y-1"
          @click="router.push(`/templates/${tmpl.slug}`)"
        >
          <div class="relative h-52 overflow-hidden">
            <img :src="tmpl.coverImage" :alt="tmpl.name" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
            <div v-if="tmpl.badge" class="absolute top-4 right-4 px-3 py-1 rounded-full text-xs font-bold text-white"
              :class="tmpl.badge === '热门' ? 'bg-primary' : tmpl.badge === '新上线' ? 'bg-green-500' : 'bg-gray-500'">
              {{ tmpl.badge }}
            </div>
          </div>
          <div class="p-6">
            <div class="flex items-center gap-2 mb-2">
              <span class="text-xs px-2 py-0.5 rounded-full bg-primary-50 text-primary-700">{{ tmpl.category }}</span>
              <span class="text-xs text-gray-400">{{ tmpl.type }}</span>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-1">{{ tmpl.name }}</h3>
            <p class="text-gray-500 text-sm mb-4">{{ tmpl.tagline }}</p>
            <div class="flex flex-wrap gap-1.5 mb-4">
              <span v-for="feat in tmpl.features.slice(0, 3)" :key="feat"
                class="text-xs px-2 py-0.5 bg-gray-100 text-gray-600 rounded">
                {{ feat }}
              </span>
            </div>
            <div class="flex items-center justify-between pt-3 border-t border-gray-100">
              <span class="text-primary font-bold text-lg">{{ tmpl.price }}</span>
              <span class="text-sm text-gray-400 group-hover:text-primary transition-colors">了解详情 →</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <Footer />
  </div>
</template>
