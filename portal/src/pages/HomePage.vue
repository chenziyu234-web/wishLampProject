<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
import { templates, stats } from '@/data/templates'
import gsap from 'gsap'

const router = useRouter()
const featuredTemplates = templates.slice(0, 3)

onMounted(() => {
  gsap.from('.hero-title', { y: 40, opacity: 0, duration: 0.8, ease: 'power3.out' })
  gsap.from('.hero-subtitle', { y: 30, opacity: 0, duration: 0.8, delay: 0.2, ease: 'power3.out' })
  gsap.from('.hero-cta', { y: 20, opacity: 0, duration: 0.6, delay: 0.4, ease: 'power3.out' })
  gsap.from('.stat-item', { y: 30, opacity: 0, duration: 0.5, stagger: 0.1, delay: 0.6, ease: 'power2.out' })
})
</script>

<template>
  <div class="min-h-screen">
    <Navbar />

    <!-- Hero -->
    <section class="relative min-h-[90vh] flex items-center overflow-hidden"
      style="background: linear-gradient(135deg, #1a0000 0%, #8B0000 30%, #DC143C 60%, #e6ad00 100%);">
      <div class="absolute inset-0 overflow-hidden">
        <div class="absolute top-20 left-10 text-8xl opacity-10 animate-pulse">🏮</div>
        <div class="absolute top-40 right-20 text-6xl opacity-10 animate-pulse" style="animation-delay: 1s">🏮</div>
        <div class="absolute bottom-20 left-1/3 text-7xl opacity-10 animate-pulse" style="animation-delay: 0.5s">🏮</div>
      </div>
      <div class="relative max-w-7xl mx-auto px-6 py-32">
        <h1 class="hero-title text-6xl font-bold text-white font-display leading-tight max-w-3xl">
          让每一场活动<br />
          <span class="text-gold">充满互动与惊喜</span>
        </h1>
        <p class="hero-subtitle text-xl text-white/70 mt-6 max-w-2xl leading-relaxed">
          WishLamp 互动活动平台，提供灯笼祈福、猜灯谜、抽奖、投票等丰富活动模板，
          一键开通，轻松管理，让您的活动参与度提升 300%。
        </p>
        <div class="hero-cta flex gap-4 mt-10">
          <button
            class="px-8 py-4 bg-gold text-primary-900 rounded-full text-lg font-bold hover:bg-gold-400 transition-all shadow-xl shadow-gold/30 hover:shadow-2xl hover:shadow-gold/40 hover:-translate-y-0.5"
            @click="router.push('/templates')"
          >
            浏览活动模板
          </button>
          <button
            class="px-8 py-4 bg-white/10 text-white rounded-full text-lg font-medium hover:bg-white/20 transition-all backdrop-blur-sm border border-white/20"
            @click="router.push('/contact')"
          >
            免费咨询
          </button>
        </div>
      </div>
    </section>

    <!-- Stats -->
    <section class="bg-white py-16 -mt-1">
      <div class="max-w-7xl mx-auto px-6">
        <div class="grid grid-cols-4 gap-8">
          <div v-for="(item, i) in [
            { value: stats.customers, label: '服务客户' },
            { value: stats.activities, label: '活动场次' },
            { value: stats.participants, label: '参与人数' },
            { value: stats.satisfaction, label: '客户满意度' },
          ]" :key="i" class="stat-item text-center">
            <div class="text-4xl font-bold text-primary font-display">{{ item.value }}</div>
            <div class="text-gray-500 mt-2">{{ item.label }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Featured Templates -->
    <section class="py-24 bg-gray-50">
      <div class="max-w-7xl mx-auto px-6">
        <div class="text-center mb-16">
          <h2 class="text-4xl font-bold text-gray-900 font-display">精选活动模板</h2>
          <p class="text-gray-500 mt-4 text-lg">开箱即用，快速上线，专业运营团队全程支持</p>
        </div>
        <div class="grid grid-cols-3 gap-8">
          <div
            v-for="tmpl in featuredTemplates"
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
              </div>
              <h3 class="text-xl font-bold text-gray-900 mb-2">{{ tmpl.name }}</h3>
              <p class="text-gray-500 text-sm mb-4 line-clamp-2">{{ tmpl.tagline }}</p>
              <div class="flex items-center justify-between">
                <span class="text-primary font-bold">{{ tmpl.price }}</span>
                <span class="text-sm text-gray-400 group-hover:text-primary transition-colors">了解详情 →</span>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-12">
          <button
            class="px-8 py-3 border-2 border-primary text-primary rounded-full font-medium hover:bg-primary hover:text-white transition-all"
            @click="router.push('/templates')"
          >
            查看全部模板
          </button>
        </div>
      </div>
    </section>

    <!-- How It Works -->
    <section class="py-24 bg-white">
      <div class="max-w-7xl mx-auto px-6">
        <div class="text-center mb-16">
          <h2 class="text-4xl font-bold text-gray-900 font-display">三步开启互动活动</h2>
          <p class="text-gray-500 mt-4 text-lg">简单三步，即可拥有专属互动活动</p>
        </div>
        <div class="grid grid-cols-3 gap-12">
          <div v-for="(step, i) in [
            { icon: '🎯', title: '选择模板', desc: '浏览丰富的活动模板，选择适合您的活动类型和风格' },
            { icon: '⚙️', title: '配置上线', desc: '联系我们开通账号，通过后台轻松配置活动内容和参数' },
            { icon: '🚀', title: '分享传播', desc: '生成活动链接和二维码，分享给参与者即可开始互动' },
          ]" :key="i" class="text-center">
            <div class="w-20 h-20 rounded-2xl bg-primary-50 flex items-center justify-center text-4xl mx-auto mb-6">
              {{ step.icon }}
            </div>
            <div class="flex items-center justify-center gap-2 mb-3">
              <span class="w-8 h-8 rounded-full bg-primary text-white text-sm font-bold flex items-center justify-center">{{ i + 1 }}</span>
              <h3 class="text-xl font-bold text-gray-900">{{ step.title }}</h3>
            </div>
            <p class="text-gray-500 leading-relaxed">{{ step.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA -->
    <section class="py-24" style="background: linear-gradient(135deg, #8B0000, #DC143C, #e6ad00);">
      <div class="max-w-4xl mx-auto px-6 text-center">
        <h2 class="text-4xl font-bold text-white font-display mb-6">
          准备好让您的活动更精彩了吗？
        </h2>
        <p class="text-white/70 text-lg mb-10">
          立即联系我们，获取专属活动方案和报价，前 100 名客户享受 8 折优惠
        </p>
        <div class="flex gap-4 justify-center">
          <button
            class="px-10 py-4 bg-white text-primary rounded-full text-lg font-bold hover:bg-gray-50 transition-all shadow-xl"
            @click="router.push('/contact')"
          >
            立即咨询
          </button>
          <button
            class="px-10 py-4 bg-white/10 text-white rounded-full text-lg font-medium hover:bg-white/20 transition-all border border-white/30"
            @click="router.push('/templates')"
          >
            查看模板
          </button>
        </div>
      </div>
    </section>

    <Footer />
  </div>
</template>
