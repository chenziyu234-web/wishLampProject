<template>
  <footer class="bg-gray-900 text-gray-400 pt-16 pb-8">
    <div class="max-w-7xl mx-auto px-6">
      <div class="grid grid-cols-4 gap-12 mb-12">
        <div>
          <div class="flex items-center gap-2 mb-4">
            <span class="text-2xl">🏮</span>
            <span class="text-xl font-bold text-white font-display">WishLamp</span>
          </div>
          <p class="text-sm leading-relaxed">
            专业的互动活动 SaaS 平台，为企业和组织提供丰富的线上线下互动活动解决方案。
          </p>
        </div>
        <div>
          <h4 class="text-white font-medium mb-4">活动模板</h4>
          <ul class="space-y-2 text-sm">
            <li><router-link to="/templates/lantern-blessing" class="hover:text-white transition-colors">灯笼祈福</router-link></li>
            <li><router-link to="/templates/riddle-guessing" class="hover:text-white transition-colors">猜灯谜</router-link></li>
            <li><router-link to="/templates/lucky-draw" class="hover:text-white transition-colors">幸运抽奖</router-link></li>
            <li><router-link to="/templates/photo-vote" class="hover:text-white transition-colors">投票评选</router-link></li>
          </ul>
        </div>
        <div>
          <h4 class="text-white font-medium mb-4">解决方案</h4>
          <ul class="space-y-2 text-sm">
            <li class="hover:text-white transition-colors cursor-pointer">企业年会</li>
            <li class="hover:text-white transition-colors cursor-pointer">节日营销</li>
            <li class="hover:text-white transition-colors cursor-pointer">校园活动</li>
            <li class="hover:text-white transition-colors cursor-pointer">社区互动</li>
          </ul>
        </div>
        <div>
          <h4 class="text-white font-medium mb-4">联系我们</h4>
          <ul class="space-y-2 text-sm">
            <li>📞 400-888-0000</li>
            <li>📧 contact@wishlamp.com</li>
            <li>🏢 深圳市南山区科技园</li>
          </ul>
        </div>
      </div>
      <div class="border-t border-gray-800 pt-8 text-center text-sm">
        <p>&copy; {{ new Date().getFullYear() }} WishLamp. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>
