<template>
    <div id="app">
        <v-app id="inspire">
            <v-card
                    class="text-start"
                    min-width="480"
            >
                <v-list two-line subheader min-width="480">
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
            </v-card>
        </v-app>
    </div>
</template>

<script>
    import {mapMutations} from 'vuex'
    import {mapActions} from 'vuex'
    import {mapGetters} from 'vuex'

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
            ...mapGetters(['getUser', 'getTasks']),
            ...mapMutations(['emptyTasks']),
            ...mapActions(['getUserTasks']),
        },
        computed: {
            tasks () {
                return this.getTasks()
            }
        },
        created () {
            console.log("Created UserProfileTasks()")
            this.emptyTasks()
            this.getUserTasks(this.getUser().email)
        }
    }
</script>

<style scoped>

</style>