<template>
    <div id="app">
        <v-app id="inspire">
            <div class="text-center">
                <v-progress-circular
                        v-if="loading"
                        indeterminate
                        color="primary"
                ></v-progress-circular>
            </div>
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
    import {mapGetters} from 'vuex'
    import {mapActions} from 'vuex'

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
            ...mapGetters(['getTaskCount', 'getTasks', 'getLoading']),
            ...mapMutations(['addTaskMutation', 'updateTaskMutation', 'removeTaskMutation', 'emptyTasks', 'setTasks']),
            ...mapActions(['getTaskCount', 'loadPageAction']),
            next (page) {
                console.log("next()")
                this.loadPageAction(page)
            },
        },
        computed: {
            tasks () {
                return this.getTasks()
            },
            loading () {
                return this.getLoading()
            }
        },
        created () {
            this.getTaskCount()
            this.emptyTasks()
            this.pagination.total = ~~(this.getTaskCount/2) + 1
            this.next(1)
        }
    }
</script>

<style scoped>

</style>