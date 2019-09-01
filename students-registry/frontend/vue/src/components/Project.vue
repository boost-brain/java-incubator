<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <v-card v-show="isNull">
                    <v-card-text>
                        <v-text-field
                                v-model="project.projectId"
                                label="id"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="project.description"
                                label="Адрес"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="project.projectName"
                                label="Название"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="project.projectUrl"
                                label="Адрес"
                                readonly
                        ></v-text-field>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <addEditProjectModal :project="project"></addEditProjectModal>
                        <v-btn
                                class="success ml-1"
                                @click="removeProject"
                        >
                            Удалить
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import EditProjectModal from './EditProjectModal'
    import { mapActions } from 'vuex'

    export default {
        name: "project",
        props: ['projectId'],
        computed: {
            project () {
                const id = this.projectId
                return this.$store.getters.projectById(id)
            }
        },
        components:{
            addEditProjectModal: EditProjectModal
        },
        methods: {
            ...mapActions(['removeProjectAction']),
            removeProject () {
                console.log("removeProject()")
                this.removeProjectAction(this.project)
                this.$router.push('/Projects')
            },
            isNull () {
                return this.project !== null
            }
        }
    }
</script>

<style>
    pre {
        font-size: smaller;
    }
</style>
