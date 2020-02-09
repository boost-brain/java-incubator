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
                                v-for="(item, i) in users"
                                :key="i"
                        >
                            <v-list-item-content>
                                <v-list-item-title v-text="item.name"></v-list-item-title>
                                <v-list-item-subtitle v-html="item.email"></v-list-item-subtitle>
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
    import {mapGetters} from 'vuex'

    export default {
        name: "Users",
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
            next (page) {
                console.log("next()")
                this.loadUsersAction(page)
                // userApi.get(page)
                //     .then(response => {
                //         this.setUsers(response.body)
                //     })
                //     .catch(error => {
                //         console.log(error)
                //     })
            },
            ...mapMutations(['addUserMutation', 'updateUserMutation', 'removeUserMutation', 'emptyUsers', 'setUsers']),
            ...mapActions(['getUserCount', 'loadUsersAction']),
            ...mapGetters(['getLoading'])
        },
        computed: {
            users () {
                return this.$store.getters.users
            },
            loading () {
                return this.getLoading()
            }
        },
        created () {
            this.getUserCount()
            this.emptyUsers()
            this.pagination.total = ~~(this.$store.getters.getUserCount/2) + 1
            this.next(1)
        }
    }
</script>

<style scoped>

</style>