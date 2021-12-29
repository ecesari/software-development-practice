/* eslint-disable no-debugger */
import http from '../utils/http'

export default {
    Register (data) {
        return http.post(process.env.VUE_APP_API + 'register', data)
    },
    Login (data) {
        return http.post(process.env.VUE_APP_API + 'login', data)
    },
    GetTags () {
        return http.get(process.env.VUE_APP_API + 'tags')
    },
    GetProfile () {
        return http.get(process.env.VUE_APP_API + 'user')
    },
    CreateService (data) {
        return http.post(process.env.VUE_APP_API + 'service', data,null,null,true,'Create Service')
    },
    GetService (id) {
        return http.get(process.env.VUE_APP_API + 'service/'+ id)
    },
    GetServicesByUser () {
        return http.get(process.env.VUE_APP_API + 'service/userService')
    },
    GetAllServices () {
        return http.get(process.env.VUE_APP_API + 'service')
    },
    SetTags (data) {
        return http.post(process.env.VUE_APP_API + 'user/setTags',data)
    },
    GetUserServiceDetails (serviceId) {
        return http.get(process.env.VUE_APP_API + 'user/getServiceDetails/'+ serviceId)
    },
    SendUserServiceApproval (serviceId) {
        return http.get(process.env.VUE_APP_API + 'approval/request/'+serviceId)
    },
    GetApprovalListByUser () {
        return http.get(process.env.VUE_APP_API + 'approval/userRequests')
    },
    ApproveRequest (data) {
        return http.post(process.env.VUE_APP_API + 'approval/approve', data,null,null,true,'Request Approval')
    },
    DenyRequest (data) {
        return http.post(process.env.VUE_APP_API + 'approval/deny', data)
    },
    SendServiceOverApproval (serviceId) {
        return http.get(process.env.VUE_APP_API + 'service/complete/'+serviceId)
    },

    
}