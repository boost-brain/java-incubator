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
    import userApi from '../api/users'

    export default {
        name: "Users",
        data () {
            return {
                // array: [],
                item: 1,
                pagination: {
                    page: 1,
                    total: 5,
                    perPage: 0,
                    visible: 7
                }
            }
        },
        methods: {
            next (page) {
                userApi.get(page)
                    .then(response => {
                        this.setUsers(response.body)
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            loadUsers (page) {
                console.log("loadUsers()")
                userApi.get(page)
                    .then(response => {
                        this.setUsers(response.body)
                    })
                    .catch(error => {
                        console.log(error)
                    })
                this.getUserCount()
                this.pagination.total = ~~(this.$store.getters.getUserCount/2) + 1
            },
            ...mapMutations(['addUserMutation', 'updateUserMutation', 'removeUserMutation', 'emptyUsers', 'setUsers']),
            ...mapActions(['getUserCount']),
        },
        computed: {
            users () {
                return this.$store.getters.users
            }
        },
        created () {
            this.emptyUsers()
            this.loadUsers(1)

        }
    }
</script>

<style scoped>

</style>