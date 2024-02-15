<template>
  <div style="display: flex;justify-content: center;align-items: center">
    <el-card class="box-card" style="width: 400px;margin-top: 150px">
      <template #header>
        <div class="card-header">
          <span>登录</span>
        </div>
      </template>
      <div>
        <table>
          <tr>
            <td>用户名：</td>
            <td>
              <el-input v-model="hr.username" placeholder="请输入用户名" clearable></el-input>
            </td>
          </tr>
          <tr>
            <td>用户密码：</td>
            <td>
              <el-input v-model="hr.password" placeholder="请输入密码" clearable type="password"></el-input>
            </td>
          </tr>
        </table>
      </div>
      <template #footer>
        <div style="display: flex;justify-content: center">
          <el-button type="primary" @click="loginHandle">登录</el-button>
          <el-button>重置</el-button>
        </div>
      </template>
    </el-card>
  </div>

</template>

<script setup>


import {reactive, toRefs} from "vue";
import {login} from "@/api/login.js";
import {getCurrentInstance} from "vue";

const {proxy} = getCurrentInstance()

const data = reactive({
  hr:{
    username:"admin",
    password:"123"
  }
})


const {hr} = toRefs(data)


function loginHandle(){
  login(hr.value).then(res=>{
    console.log(res,"res")
    // 保存用户
    window.sessionStorage.setItem("hr",JSON.stringify(res.data))

    // 跳转
    // proxy.$router.replace("/home")
    var redirect = proxy.$router.currentRoute.value.query.redirect;
    proxy.$router.replace(redirect?redirect:"/home")

  }).catch(err=>{
    console.log(err,"err")
  })
  console.log(hr.value.username)
  console.log(hr.value.password)
}



</script>



<style scoped>

</style>