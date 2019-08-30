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
                                :to="'/point/' + item.projectId"
                        > Открыть </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        computed: {
            projects () {
                return this.$store.getters.points
            }
        }
    }
</script>
