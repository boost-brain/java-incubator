<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <h1 class="text--secondary mb-3">Регистрация</h1>
                <v-form ref="form" validation class="mb-3">
                    <v-text-field
                            name="Email:"
                            label="email"
                            type="text"
                            v-model="email"
                    ></v-text-field>
                    <v-text-field
                            name="Id на github.com:"
                            label="gitHabId"
                            type="text"
                            v-model="gitHabId"
                    ></v-text-field>
                    <v-text-field
                            label="Ваше имя:"
                            name="name"
                            type="text"
                            v-model="name"
                    ></v-text-field>
                    <v-text-field
                            label="Сколько часов в день в среднем Вы готовы уделять проекту?"
                            v-model="hours"
                            type="text">
                    </v-text-field>
                    <v-text-field
                            label="Задайте пароль:"
                            v-model="password"
                            type="password"
                            required>
                    </v-text-field>
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
                createDate: 0,
                updateDate: 0
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
                    this.$router.push('/login')
                }
            }
        }
    }
</script>

<style scoped>

</style>