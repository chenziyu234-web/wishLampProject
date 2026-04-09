<script setup lang="ts">
import { ref, computed, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
import { submitWishEntry } from '@/api/wish'

const route = useRoute()
const instanceId = computed(() => Number(route.params.instanceId))

const form = ref({
  participantName: '',
  toName: '',
  message: '',
  cardStyle: 'lake',
})

const cardStyles = [
  {
    id: 'lake',
    name: '碧波龙舟',
    from: '#064e3b',
    to: '#065f46',
    accent: '#fbbf24',
    pattern: '🐉',
  },
  {
    id: 'festive',
    name: '传统红韵',
    from: '#7f1d1d',
    to: '#991b1b',
    accent: '#fde68a',
    pattern: '🎋',
  },
  {
    id: 'bamboo',
    name: '青竹清雅',
    from: '#14532d',
    to: '#166534',
    accent: '#fef08a',
    pattern: '🌾',
  },
]

const blessingTemplates = [
  '端午安康，粽情四溢，龙舟奋进，吉祥如意！',
  '艾叶飘香，祈愿安康，端午佳节，万事顺遂！',
  '粽子香甜，情意绵长，端午佳节，幸福安康！',
  '龙舟竞渡，意气风发，端午快乐，健康平安！',
  '五月端阳，祥瑞临门，愿你如那逆流的龙舟，勇往直前！',
]

const submitted = ref(false)
const loading = ref(false)
const errorMsg = ref('')
const canvasRef = ref<HTMLCanvasElement | null>(null)

const selectedStyle = computed(
  () => cardStyles.find((s) => s.id === form.value.cardStyle) || cardStyles[0],
)

function useTemplate(tpl: string) {
  form.value.message = tpl
}

async function handleSubmit() {
  errorMsg.value = ''
  if (!form.value.toName.trim()) {
    errorMsg.value = '请填写祝福对象'
    return
  }
  if (!form.value.message.trim()) {
    errorMsg.value = '请填写祝福语'
    return
  }
  loading.value = true
  try {
    const res = await submitWishEntry({
      instanceId: instanceId.value,
      participantName: form.value.participantName,
      toName: form.value.toName,
      message: form.value.message,
      cardStyle: form.value.cardStyle,
    })
    if (res.code === 200) {
      submitted.value = true
      await nextTick()
      drawCard()
    } else {
      errorMsg.value = res.msg || '提交失败，请稍后重试'
    }
  } catch {
    errorMsg.value = '网络错误，请检查连接后重试'
  } finally {
    loading.value = false
  }
}

function drawCard() {
  const canvas = canvasRef.value
  if (!canvas) return
  const ctx = canvas.getContext('2d')
  if (!ctx) return

  const W = 600
  const H = 400
  canvas.width = W
  canvas.height = H

  const style = selectedStyle.value

  // Background gradient
  const bg = ctx.createLinearGradient(0, 0, W, H)
  bg.addColorStop(0, style.from)
  bg.addColorStop(1, style.to)
  ctx.fillStyle = bg
  ctx.fillRect(0, 0, W, H)

  // Decorative circles
  ctx.save()
  ctx.globalAlpha = 0.07
  ctx.fillStyle = '#ffffff'
  ctx.beginPath()
  ctx.arc(W - 60, 60, 140, 0, Math.PI * 2)
  ctx.fill()
  ctx.beginPath()
  ctx.arc(60, H - 60, 100, 0, Math.PI * 2)
  ctx.fill()
  ctx.restore()

  // Wave pattern at bottom
  ctx.save()
  ctx.globalAlpha = 0.12
  ctx.fillStyle = '#ffffff'
  ctx.beginPath()
  ctx.moveTo(0, H - 50)
  for (let x = 0; x <= W; x += 30) {
    ctx.quadraticCurveTo(x + 15, H - 70, x + 30, H - 50)
  }
  ctx.lineTo(W, H)
  ctx.lineTo(0, H)
  ctx.closePath()
  ctx.fill()
  ctx.restore()

  // Top border line
  ctx.save()
  ctx.strokeStyle = style.accent
  ctx.lineWidth = 2
  ctx.globalAlpha = 0.6
  ctx.beginPath()
  ctx.moveTo(40, 28)
  ctx.lineTo(W - 40, 28)
  ctx.stroke()
  ctx.restore()

  // Title
  ctx.fillStyle = style.accent
  ctx.font = 'bold 32px "Noto Serif SC", serif'
  ctx.textAlign = 'center'
  ctx.textBaseline = 'middle'
  ctx.fillText('端 午 安 康', W / 2, 58)

  // Bottom border line
  ctx.save()
  ctx.strokeStyle = style.accent
  ctx.lineWidth = 2
  ctx.globalAlpha = 0.6
  ctx.beginPath()
  ctx.moveTo(40, 86)
  ctx.lineTo(W - 40, 86)
  ctx.stroke()
  ctx.restore()

  // Decorative pattern emojis
  ctx.font = '28px serif'
  ctx.textAlign = 'left'
  ctx.textBaseline = 'middle'
  ctx.fillText(style.pattern, W - 80, 130)
  ctx.fillText('🎑', W - 80, 175)

  // To
  ctx.fillStyle = 'rgba(255,255,255,0.9)'
  ctx.font = '18px "Noto Serif SC", serif'
  ctx.textAlign = 'left'
  ctx.textBaseline = 'top'
  ctx.fillText(`亲爱的 ${form.value.toName || '朋友'}：`, 48, 108)

  // Message text (with wrap)
  ctx.fillStyle = '#ffffff'
  ctx.font = '19px "Noto Serif SC", serif'
  wrapText(ctx, form.value.message, 48, 148, W - 160, 30, 5)

  // From
  if (form.value.participantName) {
    ctx.fillStyle = 'rgba(255,255,255,0.75)'
    ctx.font = '15px "Noto Serif SC", serif'
    ctx.textAlign = 'right'
    ctx.textBaseline = 'bottom'
    ctx.fillText(`— ${form.value.participantName}`, W - 48, H - 28)
  }

  // Bottom border
  ctx.save()
  ctx.strokeStyle = style.accent
  ctx.lineWidth = 2
  ctx.globalAlpha = 0.6
  ctx.beginPath()
  ctx.moveTo(40, H - 20)
  ctx.lineTo(W - 40, H - 20)
  ctx.stroke()
  ctx.restore()
}

function wrapText(
  ctx: CanvasRenderingContext2D,
  text: string,
  x: number,
  y: number,
  maxWidth: number,
  lineHeight: number,
  maxLines: number,
) {
  const chars = Array.from(text)
  let line = ''
  let lines = 0
  ctx.textAlign = 'left'
  ctx.textBaseline = 'top'

  for (let i = 0; i < chars.length; i++) {
    const testLine = line + chars[i]
    if (ctx.measureText(testLine).width > maxWidth && line !== '') {
      ctx.fillText(line, x, y + lines * lineHeight)
      lines++
      if (lines >= maxLines) {
        return
      }
      line = chars[i]
    } else {
      line = testLine
    }
  }
  if (line) {
    ctx.fillText(line, x, y + lines * lineHeight)
  }
}

function downloadCard() {
  const canvas = canvasRef.value
  if (!canvas) return
  const link = document.createElement('a')
  link.download = `端午祝福卡-${form.value.toName || '祝福'}.png`
  link.href = canvas.toDataURL('image/png')
  link.click()
}

function resetForm() {
  submitted.value = false
  errorMsg.value = ''
  form.value = { participantName: '', toName: '', message: '', cardStyle: 'lake' }
}
</script>

<template>
  <div class="min-h-screen" style="background: #f0fdf4">
    <Navbar />

    <!-- Hero Banner -->
    <section
      class="relative pt-16 pb-12 overflow-hidden"
      style="background: linear-gradient(135deg, #022c22 0%, #064e3b 40%, #065f46 70%, #0a6640 100%)"
    >
      <!-- Floating decorations -->
      <div class="absolute inset-0 overflow-hidden pointer-events-none">
        <div class="absolute top-8 left-8 text-6xl opacity-15 animate-pulse">🎋</div>
        <div
          class="absolute top-16 right-16 text-5xl opacity-15 animate-pulse"
          style="animation-delay: 0.8s"
        >
          🐉
        </div>
        <div
          class="absolute bottom-4 left-1/4 text-4xl opacity-10 animate-pulse"
          style="animation-delay: 1.4s"
        >
          🎑
        </div>
        <div
          class="absolute bottom-6 right-1/3 text-5xl opacity-10 animate-pulse"
          style="animation-delay: 0.4s"
        >
          🌾
        </div>
      </div>

      <div class="relative max-w-4xl mx-auto px-6 text-center">
        <div
          class="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-yellow-400/20 text-yellow-300 text-sm font-medium mb-6 border border-yellow-400/30"
        >
          <span>🎏</span>
          端午佳节限定活动
        </div>
        <h1 class="text-5xl font-bold text-white mb-4" style="font-family: 'Noto Serif SC', serif">
          端午祝福卡
        </h1>
        <p class="text-xl text-green-200 leading-relaxed">
          为你在乎的人，送上一张充满心意的端午祝福卡
        </p>
      </div>
    </section>

    <!-- Main Content -->
    <div class="max-w-5xl mx-auto px-6 py-12">
      <!-- Submitted State -->
      <div v-if="submitted" class="text-center">
        <div
          class="inline-flex items-center gap-3 px-6 py-3 bg-green-100 text-green-700 rounded-full text-lg font-medium mb-8 border border-green-200"
        >
          <span class="text-2xl">🎉</span>
          祝福已送出！
        </div>

        <!-- Card Preview -->
        <div class="flex justify-center mb-8">
          <div class="shadow-2xl rounded-2xl overflow-hidden ring-4 ring-white/50">
            <canvas ref="canvasRef" class="block" style="max-width: 100%" />
          </div>
        </div>

        <div class="flex justify-center gap-4 flex-wrap">
          <button
            @click="downloadCard"
            class="px-8 py-3 rounded-full font-bold text-white shadow-lg transition-all hover:-translate-y-0.5 hover:shadow-xl"
            style="background: linear-gradient(135deg, #064e3b, #0a6640)"
          >
            ⬇ 下载祝福卡
          </button>
          <button
            @click="resetForm"
            class="px-8 py-3 rounded-full font-medium border-2 border-emerald-600 text-emerald-700 hover:bg-emerald-50 transition-all"
          >
            再发一张
          </button>
        </div>
      </div>

      <!-- Form State -->
      <div v-else class="grid grid-cols-1 lg:grid-cols-2 gap-10">
        <!-- Left: Form -->
        <div class="bg-white rounded-2xl shadow-sm border border-emerald-100 p-8">
          <h2
            class="text-2xl font-bold text-gray-800 mb-7"
            style="font-family: 'Noto Serif SC', serif"
          >
            填写祝福信息
          </h2>

          <!-- Card Style Selection -->
          <div class="mb-6">
            <label class="block text-sm font-semibold text-gray-600 mb-3">选择卡片风格</label>
            <div class="grid grid-cols-3 gap-3">
              <button
                v-for="style in cardStyles"
                :key="style.id"
                class="relative py-3 px-2 rounded-xl text-sm font-medium text-white transition-all border-2"
                :style="{
                  background: `linear-gradient(135deg, ${style.from}, ${style.to})`,
                  borderColor: form.cardStyle === style.id ? style.accent : 'transparent',
                  boxShadow: form.cardStyle === style.id ? `0 0 0 2px ${style.accent}40` : 'none',
                }"
                @click="form.cardStyle = style.id"
              >
                <div class="text-lg mb-1">{{ style.pattern }}</div>
                <div>{{ style.name }}</div>
                <div
                  v-if="form.cardStyle === style.id"
                  class="absolute -top-2 -right-2 w-5 h-5 bg-yellow-400 rounded-full flex items-center justify-center text-xs text-green-900 font-bold"
                >
                  ✓
                </div>
              </button>
            </div>
          </div>

          <!-- Recipient -->
          <div class="mb-5">
            <label class="block text-sm font-semibold text-gray-600 mb-2">
              祝福对象
              <span class="text-red-500 ml-1">*</span>
            </label>
            <input
              v-model="form.toName"
              type="text"
              maxlength="20"
              placeholder="请输入对方的名字"
              class="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-400/20 outline-none transition-all text-gray-800"
            />
          </div>

          <!-- Sender -->
          <div class="mb-5">
            <label class="block text-sm font-semibold text-gray-600 mb-2">你的名字（选填）</label>
            <input
              v-model="form.participantName"
              type="text"
              maxlength="20"
              placeholder="落款署名，不填则不显示"
              class="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-400/20 outline-none transition-all text-gray-800"
            />
          </div>

          <!-- Message -->
          <div class="mb-5">
            <label class="block text-sm font-semibold text-gray-600 mb-2">
              祝福语
              <span class="text-red-500 ml-1">*</span>
            </label>
            <textarea
              v-model="form.message"
              maxlength="200"
              rows="4"
              placeholder="写下你的端午祝福..."
              class="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-400/20 outline-none transition-all text-gray-800 resize-none"
            />
            <div class="text-xs text-gray-400 text-right mt-1">{{ form.message.length }}/200</div>
          </div>

          <!-- Quick Templates -->
          <div class="mb-6">
            <label class="block text-sm font-semibold text-gray-600 mb-2">快速选用祝福语</label>
            <div class="space-y-2">
              <button
                v-for="(tpl, i) in blessingTemplates"
                :key="i"
                class="w-full text-left px-3 py-2 rounded-lg text-sm text-gray-600 bg-gray-50 hover:bg-emerald-50 hover:text-emerald-700 transition-colors border border-transparent hover:border-emerald-200"
                @click="useTemplate(tpl)"
              >
                {{ tpl }}
              </button>
            </div>
          </div>

          <!-- Error -->
          <div
            v-if="errorMsg"
            class="mb-4 px-4 py-3 bg-red-50 border border-red-200 text-red-600 rounded-xl text-sm"
          >
            {{ errorMsg }}
          </div>

          <!-- Submit -->
          <button
            @click="handleSubmit"
            :disabled="loading"
            class="w-full py-4 rounded-xl font-bold text-white text-lg transition-all hover:-translate-y-0.5 hover:shadow-xl disabled:opacity-60 disabled:cursor-not-allowed disabled:translate-y-0"
            style="background: linear-gradient(135deg, #064e3b, #0a6640)"
          >
            <span v-if="loading" class="inline-flex items-center gap-2">
              <svg class="w-5 h-5 animate-spin" fill="none" viewBox="0 0 24 24">
                <circle
                  class="opacity-25"
                  cx="12"
                  cy="12"
                  r="10"
                  stroke="currentColor"
                  stroke-width="4"
                />
                <path
                  class="opacity-75"
                  fill="currentColor"
                  d="M4 12a8 8 0 018-8v8H4z"
                />
              </svg>
              生成中...
            </span>
            <span v-else>生成祝福卡 🎋</span>
          </button>
        </div>

        <!-- Right: Card Preview -->
        <div class="flex flex-col items-center">
          <h2 class="text-lg font-semibold text-gray-600 mb-4 self-start">实时预览</h2>
          <div
            class="w-full rounded-2xl overflow-hidden shadow-xl border border-white"
            :style="{
              background: `linear-gradient(135deg, ${selectedStyle.from}, ${selectedStyle.to})`,
              minHeight: '280px',
            }"
          >
            <!-- Preview card (CSS-based, mirrors canvas design) -->
            <div class="relative p-8 text-white" style="min-height: 280px">
              <!-- Top decoration -->
              <div
                class="absolute top-0 right-0 w-40 h-40 rounded-full opacity-10"
                style="background: white; transform: translate(30%, -30%)"
              />
              <div
                class="absolute bottom-0 left-0 w-28 h-28 rounded-full opacity-10"
                style="background: white; transform: translate(-30%, 30%)"
              />

              <div
                class="relative text-center text-xl font-bold mb-3 pb-3 border-b"
                :style="{ color: selectedStyle.accent, borderColor: selectedStyle.accent + '60' }"
                style="font-family: 'Noto Serif SC', serif; letter-spacing: 0.3em"
              >
                端 午 安 康
              </div>

              <div class="relative">
                <p class="text-white/80 text-sm mb-3">
                  亲爱的
                  <span class="text-white font-medium">{{ form.toName || '朋友' }}</span
                  >：
                </p>
                <p
                  class="text-white leading-relaxed text-base min-h-[60px]"
                  style="font-family: 'Noto Serif SC', serif"
                >
                  {{ form.message || '在这里填写你的祝福语...' }}
                </p>
                <p
                  v-if="form.participantName"
                  class="text-right mt-4 text-white/70 text-sm"
                >
                  — {{ form.participantName }}
                </p>
              </div>

              <!-- Pattern emoji -->
              <div class="absolute top-12 right-8 text-3xl opacity-60">
                {{ selectedStyle.pattern }}
              </div>
            </div>
          </div>

          <p class="text-xs text-gray-400 mt-3 text-center">
            提交后将生成精美祝福卡图片，可下载保存
          </p>

          <!-- Festival Info -->
          <div class="mt-8 bg-white rounded-2xl p-6 border border-emerald-100 w-full shadow-sm">
            <div class="text-sm font-semibold text-emerald-700 mb-3 flex items-center gap-2">
              <span>🎏</span> 关于端午节
            </div>
            <div class="space-y-2 text-sm text-gray-500 leading-relaxed">
              <p>端午节，每年农历五月初五，是中国四大传统节日之一。</p>
              <p>相传起源于纪念爱国诗人屈原，人们包粽子、赛龙舟、挂艾草，祈求平安健康。</p>
              <p>送上一张祝福卡，让这份传统节日的温情，传递给你在乎的人。</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Hidden canvas for actual download image generation -->
    <canvas ref="canvasRef" class="hidden" />

    <Footer />
  </div>
</template>
