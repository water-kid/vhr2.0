import request from "@/utils/request.js";


export function loadAllPosition(params){
    return request({
        url:"/api/employee/basic/position",
        method:"get",
        params:params
    })
}


export function updatePosition(data){
    return request({
        url:"/api/employee/basic/position",
        method:"put",
        data:data
    })
}