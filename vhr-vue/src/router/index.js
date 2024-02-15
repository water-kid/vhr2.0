import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import UserList from "@/views/user/UserList.vue";
import UserAnalysis from "@/views/user/UserAnalysis.vue";
import UserLog from "@/views/user/UserLog.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView,
      hidden:true
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
      children:[
        {
          path: '/userlist',
          name: '用户列表',
          component: UserList
        },
        {
          path: '/analysis',
          name: '用户分析',
          component: UserAnalysis
        },
        {
          path: '/log',
          name: '用户日志',
          component: UserLog
        }
      ]
    },

    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router
