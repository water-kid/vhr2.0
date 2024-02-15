import './assets/base.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'


import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

import {menuStore} from "@/stores/counter.js";
// 存的menu
const mStore = menuStore()


router.beforeEach((to,from,next)=>{
    console.log(to.path==="/")
    if (to.path === "/"){
        // 去登录页面
        next()

        // next() 后面还会执行
        return
    }

    // 判断是否登录，，，登录才能访问
    if (window.sessionStorage.getItem("hr")){

        if (mStore.menus && mStore.menus.length!=0){


            // 跳转没有刷新
            next()
        }else{
            // 浏览器刷新，，， menus就会为空
            // mStore.
            mStore.initMenus().then(fmtMenus=>{

                fmtMenus.forEach(item=>{
                    router.addRoute(item)
                })

                // 中断当前跳转，，，重新开启一个跳转
                next({...to})
            })



        }


    }else{
        // 没有登录
        // next("/?redirect="+to.path)
         next({
             path:"/",
             query:{
                 redirect:to.path
             }
         })
    }



})


app.mount('#app')
