import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import {loadMenus} from "@/api/menus.js";
import HomeView from "@/views/HomeView.vue";
import router from "@/router/index.js";

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})

// const modules = import.meta.glob("@/view/**/*.vue")
const modules = import.meta.glob("@/views/**/*.vue")

export const menuStore = defineStore("menus",{
  state:()=>({
    // 每次刷新menu为空
    menus:[]
  }),
  actions:{
    clearMenus(){
      this.menus = []
    },

    initMenus(){

      return new Promise((resolve, reject)=>{
        loadMenus().then(res=>{

          // 带路径的menus
          this.menus = res.data

          //
          let fmtMenus = formatMenus(res.data)


          // fmtMenus.forEach(route=>{
          //   router.addRoute(route)
          // })
          console.log(fmtMenus,"fmtmenus",res.data)

          resolve(fmtMenus)

        })
      })

    }
  }
})




function formatMenus(menus){
  let result = []
  menus.forEach(item=>{
    let {path,component,name,children} = item
    if (children && children instanceof Array){
      // 有子路由
      children =  formatMenus(children)
    }

    let formatItem = {
      path:path,name:name,children:children,
      component:loadView(component)
    }

    result.push(formatItem)

  })

  console.log(result,menus,111)
  return result
}


function loadView(viewPath){
  if (viewPath === "/src/views/HomeView.vue"){
    return HomeView
  }else {
    return modules[viewPath]
  }
}
