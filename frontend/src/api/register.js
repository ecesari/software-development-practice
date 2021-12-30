/* eslint-disable no-debugger */
import http from '../utils/http'

// url, data, handleOnError,successMessage
export default {
    Register(data) {
        return http.post(process.env.VUE_APP_API + 'register', data,true)
    },
    Login(data) {
        return http.post(process.env.VUE_APP_API + 'login', data,true)
    },
    GetTags() {
        return http.get(process.env.VUE_APP_API + 'tags')
    },
    GetProfile(id) {
        if (id == null)
            return http.get(process.env.VUE_APP_API + 'user',null,true)
        else
            return http.get(process.env.VUE_APP_API + 'user/' + id,null,true)

    },
    CreateService(data) {
        return http.post(process.env.VUE_APP_API + 'service', data, true, "Create Service Successful")
    },
    GetService(id) {
        return http.get(process.env.VUE_APP_API + 'service/' + id,null,true)
    },
    GetServicesByUser() {
        return http.get(process.env.VUE_APP_API + 'service/userService')
    },
    GetAllServices() {
        return http.get(process.env.VUE_APP_API + 'service')
    },
    SetTags(data) {
        return http.post(process.env.VUE_APP_API + 'user/setTags', data)
    },
    GetUserServiceDetails(serviceId) {
        return http.get(process.env.VUE_APP_API + 'user/getServiceDetails/' + serviceId)
    },
    SendUserServiceApproval(serviceId) {
        return http.get(process.env.VUE_APP_API + 'approval/request/' + serviceId,null,true,"Request Sent")
    },
    GetApprovalListByUser() {
        return http.get(process.env.VUE_APP_API + 'approval/userRequests',null,true)
    },
    ApproveRequest(data) {
        return http.post(process.env.VUE_APP_API + 'approval/approve', data, true, 'Request Approved Successfully')
    },
    DenyRequest(data) {
        return http.post(process.env.VUE_APP_API + 'approval/deny', data, true, 'Request Denied Successfully')
    },
    SendServiceOverApproval(serviceId) {
        return http.get(process.env.VUE_APP_API + 'service/complete/' + serviceId,true, 'Service has been completed. Thank you!')
    },
    GetNotificationDetails() {
        return http.get(process.env.VUE_APP_API + 'notification/getByUser')
    },
    ReadAllNotifications() {
        return http.get(process.env.VUE_APP_API + 'notification/readAllByUser')
    },
    


}