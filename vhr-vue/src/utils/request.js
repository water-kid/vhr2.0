import axios from "axios"
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

// 所有的请求发送的都是json
axios.defaults.headers["Content-Type"] = "application/json;charset=utf-8"

const service = axios.create({
    timeout:10000
})



// 请求拦截器，， 统一添加令牌
service.interceptors.request.use()

// 相应拦截器，，  处理错误
service.interceptors.response.use(success=>{
    // 请求成功
    // 服务端的状态码，，，，
    var code = success.data.status || 200;

    if (code === 200){
        if (success.data.message){
            ElMessage.success(success.data.message)
        }

        // 返回服务器返回的json，，， respbean
        return success.data
    }else{
        ElMessage.error(success.data.message)
        return Promise.reject(success.data.message)
    }

},error=>{
    // http状态码不是200
    ElMessage.error(error)

    let status = error.response.status
    if (status === 401){
        router.replace("/")
    }
    return Promise.reject(error)
})


export default service;