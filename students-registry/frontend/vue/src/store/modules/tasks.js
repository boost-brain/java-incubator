import taskApi from "../../api/tasks";

export default {
    state: {
        tasks: [],
        taskCount: 0,
    },
    mutations: {
        setTasks (state, payload) {
            console.log("setTasks mutation")
            state.tasks = payload
        },
        emptyTasks (state){
            state.tasks = []
        },
        createTask (state, payload) {
            state.tasks.push(payload)
        },
        addTaskMutation: function (state, task) {
            console.log("addTaskMutation")
            state.tasks = [
                ...state.tasks,
                task
            ]
        },
        updateTaskMutation(state, task) {
            console.log("updateTaskMutation")
            const updateIndex = state.tasks.findIndex(item => item.id === task['id'])
            state.tasks = [
                ...state.tasks.slice(0, updateIndex),
                task,
                ...state.tasks.slice(updateIndex + 1)
            ]
        },
        removeTaskMutation(state, task) {
            console.log("removeTaskMutation")
            const deletionIndex = state.tasks.findIndex(item => item['id'] === task['@id'])

            if (deletionIndex > -1) {
                state.tasks = [
                    ...state.tasks.slice(0, deletionIndex),
                    ...state.tasks.slice(deletionIndex + 1)
                ]
            }
        },
        setTaskCount(state, payload){
            console.log('setTaskCount mutation')
            state.taskCount = payload
        }
    },
    actions: {
        async loadTasksAction ({commit}) {
            commit('setLoading', true)
            await this.state.resource.get().then(response => response.json())
                .then(tasks => {
                    for (var data of tasks) {
                        commit('addTaskMutation', data)
                    }
                })
            commit('setLoading', false)
        },
        async addTaskAction({commit, state}, task) {
            const result = await taskApi.add(task)
            const data = await result.json()
            const index = state.tasks.findIndex(item => item['id'] === data['@id'])
            if (index > -1) {
                commit('updateTaskMutation', data)
            } else {
                commit('addTaskMutation', data)
            }
        },
        async updateTaskAction({commit}, task) {
            const result = await taskApi.update(task)
            console.log(task)
            const data = await result.json()
            commit('updateTaskMutation', data)
        },
        async removeTaskAction({commit}, task) {
            const result = await taskApi.remove(task.id)
            if (result.ok) {
                commit('removeTaskMutation', task)
            }
        },
        async getTaskCount({commit}) {
            console.log('getTaskCount action')
            const result = await taskApi.count()
            const count = await result.json()
            commit('setTaskCount', count)
        },
    },
    getters: {
        getTaskCount (state) {
            return state.taskCount
        },
        tasks (state) {
            return state.tasks
        },
    }
}
