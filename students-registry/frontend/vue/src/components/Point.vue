<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <v-card v-show="isNull">
                    <v-card-text>
                        <v-text-field
                                v-model="point.projectId"
                                label="id"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="point.description"
                                label="Адрес"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="point.projectName"
                                label="Название"
                                readonly
                        ></v-text-field>
                        <v-text-field
                                v-model="point.projectUrl"
                                label="Адрес"
                                readonly
                        ></v-text-field>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <addEditPointModal :point="point"></addEditPointModal>
                        <v-btn
                                class="success ml-1"
                                @click="removePoint"
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
    import EditPointModal from './EditPointModal'
    import { mapActions } from 'vuex'

    export default {
        name: "point",
        props: ['projectId'],
        computed: {
            point () {
                const id = this.projectId
                return this.$store.getters.pointById(id)
            }
        },
        components:{
            addEditPointModal: EditPointModal
        },
        methods: {
            ...mapActions(['removePointAction']),
            removePoint () {
                console.log("removePoint()")
                this.removePointAction(this.point)
                this.$router.push('/Projects')
            },
            isNull () {
                return this.point !== null
            }
        }
    }
</script>

<style>
    pre {
        font-size: smaller;
    }
</style>
