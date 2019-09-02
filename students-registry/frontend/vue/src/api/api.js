const mocks = {
  'auth': { 'POST': { token: 'This-is-a-mocked-token' } },
  'user/me': { 'GET': { name: 'doggo', title: 'siri' } }
}

// const apiCall = ({url, method, ...args}) => new Promise((resolve, reject) => {
const apiCall = ({url, method}) => new Promise((resolve, reject) => {
  setTimeout(() => {
    try {
      resolve(mocks[url][method || 'GET'])
      //reject(new Error("123"))
      console.log(`Mocked '${url}' - ${method || 'GET'}`)
      console.log('response: ', mocks[url][method || 'GET'])
    } catch (err) {
      reject(new Error(err))
    }
  }, 1000)
})

export default apiCall
