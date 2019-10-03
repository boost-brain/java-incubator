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
                            name="gitHabId"
                            label="gitHabId"
                            type="text"
                            v-model="gitHabId"
                    ></v-text-field>
                    <v-text-field
                            name="name"
                            label="name"
                            type="text"
                            v-model="name"
                    ></v-text-field>
                    <v-text-field
                            label="Password"
                            v-model="password"
                            type="password"
                            required>
                    </v-text-field>
                    <v-text-field
                            label="Hours"
                            v-model="hours"
                            type="text"
                            required>
                    </v-text-field>
                    <v-text-field
                            name="create_date"
                            label="create_date"
                            type="text"
                            v-model="createDate"
                    ></v-text-field>
                    <v-textarea
                            name="update_date"
                            label="update_date"
                            type="text"
                            v-model="updateDate"
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
                gitHabId: '',
                name: '',
                password: '',
                hours: 0,
                createDate: '',
                updateDate: ''
            }
        },
        methods: {
            ...mapActions(['registerAction']),
            save() {
                console.log("register()")
                if (this.$refs.form.validate()) {
                    const user = {
                        email: this.email,
                        password: this.password,
                        createDate: this.createDate,
                        gitHabId: this.gitHabId,
                        hours: this.hours,
                        name: this.name,
                        updateDate: this.updateDate
                    }
                    this.registerAction(user, "user")
                    this.$router.push('/users')
                }
            }
        }
    }
</script>

<style scoped>

</style>