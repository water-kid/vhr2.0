<template>
  <div class="common-layout">
    <el-container>
      <el-header style="background-color:#02adff;align-items: center;display: flex;justify-content: space-between">
        <div style="font-family: 华文行楷;font-size: 30px">微人事</div>
        <div>
          <el-dropdown style="cursor: pointer" @command="menuHandle">
            <span class="el-dropdown-link" style="display: flex;align-items: center">
              {{ hr.username }}

              <img :src="hr.userface" style="width: 48px;height: 48px;border-radius: 24px" alt="">
<!--              <el-icon class="el-icon&#45;&#45;right">-->
<!--                <arrow-down/>-->
<!--              </el-icon>-->
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item command="settings">设置</el-dropdown-item>

                <el-dropdown-item divided command="logout" >注销登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
<!--  开启router模式，，自动用index进行路由跳转-->
          <el-menu
              active-text-color="#ffd04b"
              background-color="#545c64"
              class="el-menu-vertical-demo"
              default-active="2"
              text-color="#fff"
              @select="menuSelect"
          >
            <template v-for="(item,indexi) in menus">
              <el-sub-menu :index="indexi+''" v-if="!item.hidden" :key="indexi">
                <template #title>
                  <el-icon><location /></el-icon>
                  <span>{{item.name}}</span>
                </template>
                  <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">{{ child.name }}</el-menu-item>

              </el-sub-menu>
            </template>
          </el-menu>

        </el-aside>
        <el-main>

          <el-breadcrumb :separator-icon="ArrowRight" v-if="proxy.$router.currentRoute.value.path !== '/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">主页</el-breadcrumb-item>
            <el-breadcrumb-item >{{ proxy.$router.currentRoute.value.name }}</el-breadcrumb-item>

          </el-breadcrumb>
          <div v-if="proxy.$router.currentRoute.value.path === '/home'" style="font-size: 35px;font-family: 华文行楷;color: red;text-align: center">欢迎来到vhr</div>

          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script setup>
import {
  ArrowRight,
  Location,
} from '@element-plus/icons-vue'
import { ArrowDown } from '@element-plus/icons-vue'
import {reactive, toRefs,getCurrentInstance} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {logout} from "@/api/login.js";
import {loadMenus} from "@/api/menus.js";
import HomeView from "@/views/HomeView.vue";


const {proxy} = getCurrentInstance()

import {menuStore} from "@/stores/counter.js";
// const menustore =
const mStore = menuStore()


const data = reactive({
  hr:JSON.parse(window.sessionStorage.getItem("hr")),
  menus:mStore.menus
})

const { hr,menus } = toRefs(data)

//
// function loadAllMenus(){
//     loadMenus().then(res=>{
//       menus.value = res.data
//
//
//       let fmtMenus = formatMenus(res.data)
//       console.log(fmtMenus,"menus")
//       fmtMenus.forEach(menu=>{
//         proxy.$router.addRoute(menu)
//       })
//
//     })
// }
//
// // Vite 支持使用特殊的 import.meta.glob 函数从文件系统导入多个模块：这个modules类似于map，。，key是组件的路径，，value是组件对象
// // 没有加载homeview，，父路由是undefined
// const modules = import.meta.glob("@/views/**/*.vue")
// console.log(modules,"modules")
// /**
//  * 将menu 变成  route,,,, 返回的component是字符串
//  */
// function formatMenus(menus){
//   let result = []
//   menus.forEach(menu=>{
//     let {path,name,children,component} = menu
//     if (children && children instanceof Array){
//      children =  formatMenus(children)
//     }
//
//     let formatItem = {
//       path:path,
//       name:name,
//       children:children,
//       component:loadView(component)
//     }
//
//     result.push(formatItem)
//
//   })
//
//   return result
// }
//
// /**
//  * 判断加载父路由还是子路由
//  * @param viewPath
//  */
// function loadView(viewPath){
//   if (viewPath === "/src/views/HomeView.vue"){
//     return HomeView
//   }else{
//     // console.log(modules[viewPath],HomeView)
//     return modules[viewPath]
//   }
// }
//
// loadAllMenus()

/**
 *
 * @param index  放跳转的地址
 * @param indexPath
 */
function menuSelect(index,indexPath){
  console.log(index,indexPath,111)

  proxy.$router.push(index)
  // 跳了整个页面，，，， 多个router-view
}

function menuHandle(cmd){
  if (cmd === "logout"){
    console.log("logout")
    ElMessageBox.confirm(
        '注销登录. Continue?',
        'Warning',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
        .then(() => {
           logout().then(res=>{
             // 注销成功
             window.sessionStorage.removeItem("hr")


             // 退出将menus清空，，不同角色的menus不同，，需要重新获取
             mStore.clearMenus()

             proxy.$router.replace("/")
           })
        })
        .catch(() => {
          ElMessage.info("已取消操作")
        })


  }
}

</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>