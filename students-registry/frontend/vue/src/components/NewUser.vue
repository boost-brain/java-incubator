<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <h1 class="text--secondary mb-3">Новый студент</h1>
                <v-form ref="form" validation class="mb-3">
                    <v-text-field
                            name="email"
                            label="email"
                            type="text"
                            v-model="email"
                    ></v-text-field>
                    <v-text-field
                            name="create_date"
                            label="create_date"
                            type="text"
                            v-model="create_date"
                    ></v-text-field>
                    <v-text-field
                            name="git_hab_id"
                            label="git_hab_id"
                            type="text"
                            v-model="git_hab_id"
                    ></v-text-field>
                    <v-text-field
                            name="name"
                            label="name"
                            type="text"
                            v-model="name"
                    ></v-text-field>
                    <v-textarea
                            name="update_date"
                            label="update_date"
                            type="text"
                            v-model="update_date"
                            multi-line
                            required
                            :rules="[v => !!v || 'update_date is required']"
                    ></v-textarea>
                </v-form>
                <v-layout row>
                    <v-flex xs12>
                        <v-spacer></v-spacer>
                        <v-btn
                                class="success"
                                @click="save"
                        >
                            Создать
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>

    import { mapActions } from 'vuex'

    export default {
        name: "NewUser",
        data () {
            return {
                email: '',
                create_date: '',
                git_hab_id: '',
                hours: 0,
                name: '',
                update_date: ''
            }
        },
        methods: {
            ...mapActions(['addUserAction', 'updateUserAction']),
            save() {
                console.log("save()")
                if (this.$refs.form.validate()) {
                    const user = {
                        email: this.email,
                        create_date: this.create_date,
                        git_hab_id: this.git_hab_id,
                        hours: this.hours,
                        name: this.name,
                        update_date: this.update_date
                    }
                    this.addUserAction(user, "user")
                    this.$router.push('/users')
                }
            }
        }
    }
</script>

<style scoped>

</style>