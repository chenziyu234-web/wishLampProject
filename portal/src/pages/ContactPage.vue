<script setup lang="ts">
import { ref } from 'vue'
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'

const form = ref({ name: '', phone: '', company: '', message: '' })
const submitted = ref(false)

function handleSubmit() {
  if (!form.value.name || !form.value.phone) {
    alert('请填写姓名和手机号')
    return
  }
  submitted.value = true
}
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <Navbar />

    <section class="pt-24 pb-12 bg-gradient-to-br from-primary-900 via-primary to-gold-700">
      <div class="max-w-7xl mx-auto px-6 pt-8">
        <h1 class="text-4xl font-bold text-white font-display">联系我们</h1>
        <p class="text-white/60 mt-3 text-lg">我们的团队将在 24 小时内与您联系</p>
      </div>
    </section>

    <div class="max-w-7xl mx-auto px-6 py-16">
      <div class="grid grid-cols-2 gap-16">
        <!-- Form -->
        <div class="bg-white rounded-2xl p-10 shadow-sm">
          <div v-if="!submitted">
            <h2 class="text-2xl font-bold text-gray-900 mb-6">留下您的信息</h2>
            <div class="space-y-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1.5">姓名 *</label>
                <input v-model="form.name" class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary" placeholder="您的姓名" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1.5">手机号 *</label>
                <input v-model="form.phone" class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary" placeholder="您的手机号" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1.5">公司名称</label>
                <input v-model="form.company" class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary" placeholder="您的公司" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1.5">需求描述</label>
                <textarea v-model="form.message" rows="4" class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary resize-none" placeholder="请简要描述您的活动需求" />
              </div>
              <button class="w-full py-3.5 bg-primary text-white rounded-xl font-bold hover:bg-primary-700 transition-all text-lg" @click="handleSubmit">
                提交咨询
              </button>
            </div>
          </div>
          <div v-else class="text-center py-16">
            <div class="text-6xl mb-6">🎉</div>
            <h3 class="text-2xl font-bold text-gray-900 mb-3">提交成功！</h3>
            <p class="text-gray-500">我们的团队将在 24 小时内与您联系，请保持手机畅通。</p>
          </div>
        </div>

        <!-- Contact Info -->
        <div class="space-y-8">
          <div>
            <h2 class="text-2xl font-bold text-gray-900 mb-6">其他联系方式</h2>
            <div class="space-y-6">
              <div class="flex items-start gap-4 p-5 bg-white rounded-xl shadow-sm">
                <span class="text-3xl">📞</span>
                <div>
                  <h3 class="font-bold text-gray-900">电话咨询</h3>
                  <p class="text-gray-500 mt-1">400-888-0000（工作日 9:00-18:00）</p>
                </div>
              </div>
              <div class="flex items-start gap-4 p-5 bg-white rounded-xl shadow-sm">
                <span class="text-3xl">💬</span>
                <div>
                  <h3 class="font-bold text-gray-900">微信咨询</h3>
                  <p class="text-gray-500 mt-1">添加企业微信 WishLamp-Service</p>
                </div>
              </div>
              <div class="flex items-start gap-4 p-5 bg-white rounded-xl shadow-sm">
                <span class="text-3xl">📧</span>
                <div>
                  <h3 class="font-bold text-gray-900">邮件咨询</h3>
                  <p class="text-gray-500 mt-1">contact@wishlamp.com</p>
                </div>
              </div>
            </div>
          </div>
          <div class="bg-primary-50 rounded-2xl p-8 border border-primary-100">
            <h3 class="text-lg font-bold text-primary-900 mb-3">🎁 限时优惠</h3>
            <p class="text-primary-700">现在咨询，前 100 名客户可享受全场 8 折优惠，还有免费体验名额！</p>
          </div>
        </div>
      </div>
    </div>

    <Footer />
  </div>
</template>
