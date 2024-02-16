

<template>
  <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
    <el-tab-pane label="部门管理" name="first">部门管理</el-tab-pane>
    <el-tab-pane label="职位管理" name="positionManager">
      <position-manager/>
    </el-tab-pane>
    <el-tab-pane label="奖惩规则" name="third">奖惩规则</el-tab-pane>
    <el-tab-pane label="权限组" name="fourth">权限组</el-tab-pane>
  </el-tabs>
</template>


<script setup>

import {reactive, toRefs} from "vue";
import PositionManager from "@/components/system/PositionManager.vue";
const data = reactive({
  activeName:"positionManager"
})


const {activeName} = toRefs(data)

</script>

<style scoped>


</style>