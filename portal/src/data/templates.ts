export interface ActivityTemplate {
  id: number
  slug: string
  name: string
  tagline: string
  description: string
  type: 'BLESSING' | 'RIDDLE' | 'LOTTERY' | 'VOTE' | 'CHECKIN' | 'QUIZ'
  category: string
  coverImage: string
  features: string[]
  scenes: string[]
  price: string
  badge?: string
  demoUrl?: string
}

export const templates: ActivityTemplate[] = [
  {
    id: 1,
    slug: 'lantern-blessing',
    name: '灯笼祈福',
    tagline: '点亮心愿，传递祝福',
    description: '经典灯笼祈福互动，用户通过手机发送祈福语，大屏幕实时展示灯笼升起动画，营造温馨节日氛围。支持自定义祈福语、灯笼样式和背景音乐。',
    type: 'BLESSING',
    category: '节日互动',
    coverImage: 'https://images.unsplash.com/photo-1548546738-8509cb246ed3?w=600&h=400&fit=crop',
    features: ['实时大屏展示', '灯笼升起动画', '自定义祈福语', '数据统计面板', '微信扫码参与', '背景音乐可配'],
    scenes: ['春节活动', '元宵节', '中秋节', '企业年会', '商场活动'],
    price: '¥2,999 起',
    badge: '热门',
    demoUrl: '/',
  },
  {
    id: 2,
    slug: 'riddle-guessing',
    name: '猜灯谜',
    tagline: '趣味答题，赢取奖品',
    description: '传统猜灯谜与现代互动结合，支持自定义题库、多难度级别、实时排行榜。适用于各类节庆活动和企业团建，激发参与热情。',
    type: 'RIDDLE',
    category: '节日互动',
    coverImage: 'https://images.unsplash.com/photo-1513297887119-d46091b24bfb?w=600&h=400&fit=crop',
    features: ['自定义题库管理', '多难度等级', '实时排行榜', '答题数据统计', '微信扫码参与', '提示功能'],
    scenes: ['元宵节', '中秋节', '企业团建', '校园活动', '社区活动'],
    price: '¥1,999 起',
    badge: '新上线',
    demoUrl: '/t/demo/riddle',
  },
  {
    id: 3,
    slug: 'lucky-draw',
    name: '幸运抽奖',
    tagline: '惊喜不断，人人有奖',
    description: '支持大转盘、九宫格、刮刮卡等多种抽奖形式，可自定义奖品、中奖概率和活动规则，后台实时查看中奖数据。',
    type: 'LOTTERY',
    category: '营销互动',
    coverImage: 'https://images.unsplash.com/photo-1513151233558-d860c5398176?w=600&h=400&fit=crop',
    features: ['多种抽奖形式', '自定义奖品设置', '中奖概率配置', '中奖记录查询', '防作弊机制', '活动数据报表'],
    scenes: ['开业庆典', '促销活动', '年会抽奖', '线上活动', '会员日'],
    price: '¥3,499 起',
    badge: '即将上线',
  },
  {
    id: 4,
    slug: 'photo-vote',
    name: '投票评选',
    tagline: '全民参与，公正透明',
    description: '支持图片/视频投票，适用于最美评选、才艺比赛等场景。内置防刷票机制，后台实时监控，数据安全可靠。',
    type: 'VOTE',
    category: '营销互动',
    coverImage: 'https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=600&h=400&fit=crop',
    features: ['图片/视频投票', '防刷票机制', '实时排名展示', '评论互动', '分享拉票', '数据导出'],
    scenes: ['才艺比赛', '最美评选', '摄影比赛', '企业评优', '校园投票'],
    price: '¥2,499 起',
    badge: '即将上线',
  },
  {
    id: 5,
    slug: 'sign-in-wall',
    name: '签到墙',
    tagline: '科技感签到，仪式感满满',
    description: '扫码签到 + 大屏互动签到墙，支持头像飞入、弹幕、3D 地球等酷炫效果，适合会议、活动开场使用。',
    type: 'CHECKIN',
    category: '会务互动',
    coverImage: 'https://images.unsplash.com/photo-1505373877841-8d25f7d46678?w=600&h=400&fit=crop',
    features: ['扫码快速签到', '大屏实时展示', '多种动画效果', '签到数据导出', '自定义签到页', '现场统计'],
    scenes: ['企业年会', '行业峰会', '发布会', '婚礼签到', '展会活动'],
    price: '¥1,999 起',
    badge: '即将上线',
  },
  {
    id: 6,
    slug: 'knowledge-quiz',
    name: '知识竞赛',
    tagline: '寓教于乐，学以致用',
    description: '支持限时答题、闯关模式、团队对抗等多种玩法，自定义题库和奖励规则，适合培训考核、科普宣传等场景。',
    type: 'QUIZ',
    category: '教育培训',
    coverImage: 'https://images.unsplash.com/photo-1434030216411-0b793f4b4173?w=600&h=400&fit=crop',
    features: ['多种答题模式', '限时挑战', '团队对抗', '题库管理', '成绩排行', '证书生成'],
    scenes: ['企业培训', '科普活动', '校园竞赛', '安全教育', '党建学习'],
    price: '¥2,999 起',
    badge: '即将上线',
  },
]

export const categories = ['全部', '节日互动', '营销互动', '会务互动', '教育培训']

export const stats = {
  customers: '500+',
  activities: '2,000+',
  participants: '100万+',
  satisfaction: '99.8%',
}
