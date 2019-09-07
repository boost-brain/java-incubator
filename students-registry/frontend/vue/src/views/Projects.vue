<template>
    <v-container>
        <v-layout>
            <v-flex xs12 sm6 offset-sm3>
                <v-card
                        class="elevation-10 mb-3"
                        v-for="item in projects"
                        :key="item.projectId"
                >
                    <v-card-title primary-title>
                        <div>
                            <h3 class="headline mb-0">{{ item.projectName }}</h3>
                            <div> {{ item.description }} </div>
                            <div> {{ item.projectUrl }} </div>
                            <div> {{ item.projectId }} </div>
                        </div>
                    </v-card-title>

                    <v-card-actions>
                        <v-btn
                                class="info"
                                :to="'/project/' + item.projectId"
                        > Открыть </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>

    import {mapMutations} from 'vuex'
    import projectApi from '../api/projects'

    export default {
        methods: {
            next () {
                console.log("next()")
                projectApi.get()
                    .then(response => {
                        this.setProjects(response.body)
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            ...mapMutations(['addProjectMutation', 'setProjects']),
        },
        computed: {
            projects () {
                return this.$store.getters.projects
            }
        },
        created () {
            console.log("load projects ()")
            this.next ()
        },
    }
</script>
