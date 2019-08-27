<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card
            max-width="500"
            class="mx-auto"
    >
        <v-toolbar
                color="gray"
                dark
        >
            <v-app-bar-nav-icon></v-app-bar-nav-icon>

            <v-toolbar-title>Проекты</v-toolbar-title>

            <div class="flex-grow-1"></div>

            <v-btn icon>
                <v-icon>search</v-icon>
            </v-btn>

        </v-toolbar>

        <v-list two-line>
            <v-list-item-group
                    v-model="selected"
                    multiple
                    active-class="gray--text"
            >
                <template v-for="(project, index) in projects">
                    <v-list-item :key="project.project_name">
                        <template v-slot:default="{ active, toggle }">
                            <v-list-item-content>
                                <v-list-item-title v-text="project.projectName"></v-list-item-title>
                                <v-list-item-subtitle class="text--primary" v-text="project.description"></v-list-item-subtitle>
                                <v-list-item-subtitle v-text="project.projectUrl"></v-list-item-subtitle>
                            </v-list-item-content>

                            <v-list-item-action>
                                <v-list-item-action-text v-text="project.projectId"></v-list-item-action-text>
                                <v-icon
                                        v-if="!active"
                                        color="grey lighten-1"
                                >
                                    star_border
                                </v-icon>

                                <v-icon
                                        v-else
                                        color="yellow"
                                >
                                    star
                                </v-icon>
                            </v-list-item-action>
                        </template>
                    </v-list-item>

                    <v-divider
                            v-if="index + 1 < projects.length"
                            :key="index"
                    ></v-divider>
                </template>
            </v-list-item-group>
        </v-list>
    </v-card>
</template>

<script>
    export default {
        name: "ProjectList",
        data() {
            return {
                selected: []
            }
        },
        computed: {
            projects () {
                return this.$store.getters.points
            }
        },
    }
</script>

<style scoped>

</style>
