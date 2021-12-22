/* eslint-disable no-debugger */
import http from '../utils/http'

export default {
    Register (data) {
        return http.post(process.env.VUE_APP_API + 'register', data).then((r) => {
            debugger;
            if (response.jwt) {
                localStorage.setItem('token', JSON.stringify(jwt));
            }
        })
    },
    Login (data) {
        return http.post(process.env.VUE_APP_API + 'login', data)
    },
    GetTags () {
        return http.get(process.env.VUE_APP_API + 'tags')
    }
}