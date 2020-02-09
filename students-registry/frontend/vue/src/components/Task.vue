<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <v-card v-show="isNull">
                    <v-card-text>
                        <v-text-field
                                v-model="task.id"
                                label="id"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.name"
                                label="Наименование"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.text"
                                label="Описание"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.project"
                                label="Проект №"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.author"
                                label="Автор задачи"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="task.implementer"
                                label="Исполнитель"
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
                    <p></p>
                    <v-alert  v-if="show" type="error">
                        error: {{ this.error }}
                    </v-alert>
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
        props: ['id'],
        computed: {
            task () {
                return this.$store.getters.getTaskById(this.id)
            },
            error () {
                return this.$store.getters.getError
            },
            show() {
                return JSON.stringify(this.error) !== JSON.stringify({})
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
            },
        }
    }
</script>

<style>
    pre {
        font-size: smaller;
    }
</style>
