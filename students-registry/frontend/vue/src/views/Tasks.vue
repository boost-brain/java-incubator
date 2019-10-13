<template>
    <div id="app">
        <v-app id="inspire">
            <v-card
                    class="mx-auto"
                    max-width="300"
                    tile
            >
                <v-list flat>
                    <v-list-item-group v-model="item" color="primary">
                        <v-list-item
                                v-for="(item, i) in tasks"
                                :key="i"
                        >
                            <v-list-item-content>
                                <v-list-item-title v-text="item.name"></v-list-item-title>
                                <v-list-item-subtitle v-html="item.id"></v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
                <v-pagination
                        v-model="pagination.page"
                        :length="pagination.total"
                        :total-visible="pagination.visible"
                        @input="next"
                ></v-pagination>
            </v-card>
        </v-app>
    </div>
</template>

<script>
    import {mapMutations} from 'vuex'
    import {mapActions} from 'vuex'
    import taskApi from '../api/tasks'

    export default {
        name: "Tasks",
        data () {
            return {
                item: 1,
                pagination: {
                    page: 2,
                    total: 4,
                    perPage: 20,
                    visible: 7
                }
            }
        },
        methods: {
            ...mapMutations(['addTaskMutation', 'updateTaskMutation', 'removeTaskMutation', 'emptyTasks', 'setTasks']),
            ...mapActions(['getTaskCount']),
            next (page) {
                console.log("next()")
                taskApi.get(page)
                    .then(response => {
                        console.log("response")
                        console.log(response)
                        this.setTasks(response.body)
                    })
                    .catch(error => {
                        console.log("error")
                        console.log(error)
                    })
            },
        },
        computed: {
            tasks () {
                return this.$store.getters.tasks
            }
        },
        created () {
            this.getTaskCount()
            this.emptyTasks()
            this.pagination.total = ~~(this.$store.getters.getTaskCount/2) + 1
            this.next(1)
        }
    }
</script>

<style scoped>

</style>