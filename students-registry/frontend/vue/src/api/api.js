// import Vue from "vue/types/index";

import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

// const mocks = {
//   'auth': { 'POST': { token: 'This-is-a-mocked-token' } },
//   'user/me': { 'GET': { name: 'doggo', title: 'siri' } }
// }

const url = 'http://localhost:8084/api/login';

export default {
    login: credentials => Vue.resource(url + '/login').save(credentials)
}

// //const apiCall = ({url, method, ...args}) => new Promise((resolve, reject) => {
// const apiCallMock = ({url, method}) => new Promise((resolve, reject) => {
//     setTimeout(() => {
//         try {
//             resolve(mocks[url][method || 'GET'])
//             //reject(new Error("123"))
//             console.log(`Mocked '${url}' - ${method || 'GET'}`)
//             console.log('response: ', mocks[url][method || 'GET'])
//         } catch (err) {
//             reject(new Error(err))
//         }
//     }, 1000)
// })

//export default apiCall
