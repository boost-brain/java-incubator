<template>
    <v-container grid-list-md>
        <v-layout row wrap>
            <v-flex xs12 sm10 offset-sm1>
<!--                <v-tabs v-model="tabMode" vertical>-->
                <v-tabs background-color="white" centered>
                    <v-tab key="'data'">
                        <v-icon left>account_circle</v-icon>
                        Мои данные
                    </v-tab>
                    <v-tab key="'tasks'">
                        <v-icon left>done_all</v-icon>
                        Мои задачи
                    </v-tab>
                    <v-tab key="'projects'">
                        <v-icon left>list</v-icon>
                        Мои проекты
                    </v-tab>

                    <v-tab-item :key="'data'" >
                        <user-profile-data></user-profile-data>
                    </v-tab-item>
                    <v-tab-item :key="'tasks'">
                        <user-profile-tasks></user-profile-tasks>
                    </v-tab-item>
                    <v-tab-item :key="'projects'">
                        <user-profile-projects></user-profile-projects>
                    </v-tab-item>
                </v-tabs>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import userProfileData from '../components/UserProfileData'
    import userProfileProjects from '../components/UserProfileProjects'
    import userProfileTasks from '../components/UserProfileTasks'

    export default {
        name: "Profile",
        tabMode: 'data',
        data () {
            return {
                tabMode: 'data'
            }
        },
        components: {
            userProfileData,
            userProfileTasks,
            userProfileProjects
        }
    }
</script>

<style scoped>

</style>