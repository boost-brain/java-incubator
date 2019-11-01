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
                                v-for="(item, i) in projects"
                                :key="i"
                        >
                            <v-list-item-content>
                                <v-list-item-title v-text="item.projectName"></v-list-item-title>
                                <v-list-item-subtitle v-html="item.projectId"></v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-card>
        </v-app>
    </div>
</template>
<script>

    import {mapActions, mapGetters, mapMutations} from 'vuex'

    export default {
        data () {
            return {
                item: 1,
            }
        },
        methods: {
            ...mapGetters(['getProjects', 'getLoading']),
            ...mapMutations(['emptyProjects']),
            ...mapActions(['loadAction']),
        },
        computed: {
            projects () {
                return this.getProjects()
            },
            loading () {
                return this.getLoading()
            }
        },
        created () {
            console.log("LoadAction()")
            this.emptyProjects()
            this.loadAction()
        }
    }
</script>

<style scoped>
    .v-progress-circular {
        margin: 1rem;
    }
</style>