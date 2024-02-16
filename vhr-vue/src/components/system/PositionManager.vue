<template>

  <div>
    <div></div>
    <div>
      <el-table :data="tableData" style="width: 100%" stripe border>
        <el-table-column prop="id" label="编号" width="180" />
        <el-table-column prop="name" label="职位名称" width="180" />
        <el-table-column prop="createDate" label="创建时间" />
        <el-table-column prop="enabled" label="是否启用">
          <template #default="scope">
            <el-switch
                v-model="scope.row.enabled"
                inline-prompt
                active-text="是"
                inactive-text="否"
                @change="enabledChange(scope.row)"
            />
          </template>
        </el-table-column>

        <el-table-column>
          <template #default="scope">
            <el-button>编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="display: flex;justify-content: flex-end">
        <el-pagination
            background layout="sizes,prev, pager, next,jumper,->,total"
            :page-sizes="[2,4,6]"
            @change="paginationChange"
            :total="total" />
      </div>
    </div>


  </div>
</template>

<script setup>

import {reactive, toRefs} from "vue";
import {loadAllPosition, updatePosition} from "@/api/system/position.js";

const data = reactive({
  tableData:[],
  total:0,
  page:1,
  size:2
})


const {tableData,total,page,size} = toRefs(data)

function enabledChange(row){
  // var position = JSON.parse(row);
  console.log(row)
  updatePosition(row).then(res=>{
    positionList()
  })
}


function paginationChange(page1,size1){
  page.value = page1
  size.value = size1
  positionList()
}

function positionList(){
  loadAllPosition({
    page:page.value,
    size:size.value
  }).then(res=>{
    console.log(res,"res")
    tableData.value =res.data
    total.value = res.total
  })
}


positionList()

</script>



<style scoped>

</style>