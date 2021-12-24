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
        return http.post(process.env.VUE_APP_API + 'service', data)
    },
}