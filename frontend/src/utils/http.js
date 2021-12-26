/* eslint-disable no-debugger */
import axios from 'axios'
import statuses from '../utils/statuses'
import modal from '../utils/modal'



// const getHeaders = (headers) => {
//     const defaultHeaders = {}
//     return Object.assign(headers || {}, defaultHeaders)
// }

const handleError = (e, errorType) => {
    debugger;
    if (e && e.message) {
        //     if (statuses.isWarning(e.response.data) || statuses.isError(e.response.data)) {
        //         if (errorType === undefined || errorType === 'modal') {
        //             modal.show(e.response.data)
        //         } else {
        //             modal.show(e.response.data)
        //         }
        //     }
        modal.show(4,e.message)
    } else if (e && e.message && e.message.indexOf('timeout') !== -1) {
        modal.show(e)
    }

}

const handleStatusCode = (e, type) => {
    if (e && e.data) {
        if (type) {
            let message = type + ' successful'
        }

        let message = ''
        if (message.length > 0) {
            if (type === undefined || type === 'modal') {
                modal.show({ StatusCode: 2, message: message })
            } else {
                modal.show({ StatusCode: 2, message: message })
            }
        }
    }

    return true
}

const getHeaders = () => {
    let token = JSON.parse(localStorage.getItem('token'));

    if (token) {
        return { Authorization: 'Bearer ' + token };
    } else {
        return {};
    }
}

export default {

    delete(url, data, headers, rejectOnError, handleOnError, messageType) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'DELETE',
                url: url,
                data: data,
                headers: getHeaders(headers),
                timeout: 300 * 240 * 1000
            }).then((r) => {
                if (handleStatusCode(r, messageType)) {
                    resolve(r.data)
                }
            }).catch((e) => {
                if (handleOnError === undefined || handleOnError === true) {
                    handleError(e, messageType)
                }
                if (rejectOnError === undefined || rejectOnError === true) {
                    reject(e)
                }
            })
        })
    },
    post(url, data, headers, rejectOnError, handleOnError, messageType) {
        debugger;
        return new Promise((resolve, reject) => {
            axios({
                
                method: 'POST',
                url: url,
                data: data,
                headers: getHeaders(headers),
                timeout: 300 * 240 * 1000
            }).then((r) => {
                if (handleStatusCode(r, messageType)) {
                    resolve(r.data)
                }
            }).catch((e) => {
                debugger;
                if (handleOnError === true) {
                    handleError(e, messageType)
                }
                if (rejectOnError === undefined || rejectOnError === true) {
                    reject(e)
                }
            })
        })
    },
    put(url, data, headers, rejectOnError, handleOnError, messageType) {
        return new Promise((resolve, reject) => {
            axios({
                method: 'PUT',
                url: url,
                data: data,
                headers: getHeaders(headers),
                timeout: 300 * 240 * 1000
            }).then((r) => {
                if (handleStatusCode(r, messageType)) {
                    resolve(r.data)
                }
            }).catch((e) => {
                if (handleOnError === undefined || handleOnError === true) {
                    handleError(e, messageType)
                }
                if (rejectOnError === undefined || rejectOnError === true) {
                    reject(e)
                }
            })
        })
    },
    get(url, data, headers, rejectOnError, handleOnError, messageType) {
        return new Promise((resolve, reject) => {
            let queryString = ''
            if (data) {
                queryString = Object.keys(data).reduce(function (str, key, i) {
                    let delimiter, val
                    delimiter = (i === 0) ? '?' : '&'
                    key = encodeURIComponent(key)
                    val = encodeURIComponent(data[key])
                    return [str, delimiter, key, '=', val].join('')
                }, '')
            }
            // var headers = getHeaders(headers) + this.authHeader();
            var headers = getHeaders(headers);
            axios({
                method: 'GET',
                url: url + queryString,
                headers: headers,
                timeout: 300 * 240 * 1000
            }).then((r) => {
                if (handleStatusCode(r, messageType)) {
                    resolve(r.data)
                }
            }).catch((e) => {
                debugger;
                if (handleOnError === undefined || handleOnError === true) {
                    handleError(e, messageType)
                }
                if (rejectOnError === undefined || rejectOnError === true) {
                    reject(e)
                }
            })
        })
    }
}
