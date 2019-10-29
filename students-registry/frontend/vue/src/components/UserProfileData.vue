<template>
    <div>
        <v-app id="inspire">
            <v-card>
                <v-card-text>
                    <div>{{user.name}}</div>
                    <p class="display-1 text--primary">
                        {{user.email}}
                    </p>
                    <p>{{user.gitHubId}}</p>
                    <div class="text--primary">
                        hours<br>
                        {{user.hours}}
                    </div>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <addEditProfileDataModal :user="user"></addEditProfileDataModal>
                </v-card-actions>
                <p></p>
                <v-alert  v-if="showError" type="warning">
                    error: {{ this.error }}
                </v-alert>
            </v-card>
        </v-app>
    </div>
</template>

<script>
    import EditProfileDataModal from './EditProfileDataModal'

    export default {
        computed: {
            user () {
                return this.$store.getters.getUser
            },
            error () {
                return this.$store.getters.getError
            },
            showError() {
                return JSON.stringify(this.error) !== JSON.stringify({})
            },
        },
        components:{
            addEditProfileDataModal: EditProfileDataModal
        },
    }
</script>

<style scoped>

</style>