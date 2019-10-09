<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <v-card v-show="isNull">
                    <v-card-text>
                        <v-text-field
                                v-model="task.taskId"
                                label="id"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.description"
                                label="Адрес"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.taskName"
                                label="Название"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.taskUrl"
                                label="Адрес"
                                readonly
                        ></v-text-field>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <addEditTaskModal :task="task"></addEditTaskModal>
                        <v-btn
                                class="success ml-1"
                                @click="removeTask"
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
    import EditTaskModal from './EditTaskModal'
    import { mapActions } from 'vuex'

    export default {
        name: "task",
        props: ['taskId'],
        computed: {
            task () {
                const id = this.taskId
                return this.$store.getters.taskById(id)
            }
        },
        components:{
            addEditTaskModal: EditTaskModal
        },
        methods: {
            ...mapActions(['removeTaskAction']),
            removeTask () {
                console.log("removeTask()")
                this.removeTaskAction(this.task)
                this.$router.push('/Tasks')
            },
            isNull () {
                return this.task !== null
            }
        }
    }
</script>

<style>
    pre {
        font-size: smaller;
    }
</style>
