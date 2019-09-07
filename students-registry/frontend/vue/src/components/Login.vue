<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app id="inspire">
        <v-content>
            <v-container class="fill-height" fluid>
                <v-row align="center" justify="center">
                    <v-col
                            cols="12"
                            sm="8"
                            md="4"
                    >
                        <v-card class="elevation-12">
                            <v-toolbar
                                    color="primary"
                                    dark
                                    flat
                            >
                                <v-toolbar-title>Login form</v-toolbar-title>
                                <div class="flex-grow-1"></div>
                            </v-toolbar>
                            <v-card-text>
                                <v-form id="login-form" @submit.prevent="doLogin">
                                    <v-text-field
                                            label="Пользователь"
                                            v-model="login"
                                            prepend-icon="person"
                                            type="text"
                                    ></v-text-field>

                                    <v-text-field
                                            id="password"
                                            label="Пароль"
                                            v-model="password"
                                            prepend-icon="lock"
                                            type="password"
                                    ></v-text-field>
                                </v-form>
                            </v-card-text>
                            <v-card-actions>
                                <div class="flex-grow-1"></div>
                                <v-btn color="primary" type="submit" form="login-form">Login</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </v-content>
    </v-app>
</template>

<!--<template>-->
    <!--<div>-->
        <!--<v-form class="login" @submit.prevent="doLogin">-->
            <!--<h1>Вход</h1>-->
            <!--<label>Пользователь</label>-->
            <!--<input required v-model="login" type="text" placeholder="Snoopy"/>-->
            <!--<label>Пароль</label>-->
            <!--<input required v-model="password" type="password" placeholder="Password"/>-->
            <!--<hr/>-->
            <!--<button type="submit">Login</button>-->
        <!--</v-form>-->
    <!--</div>-->
<!--</template>-->

<style>
    .login {
        display: flex;
        flex-direction: column;
        width: 300px;
        padding: 10px;
    }
</style>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: 'login',
        data () {
            return {
                login: '',
                password: '',
            }
        },
        methods: {
            ...mapActions(['AUTH_REQUEST']),
            doLogin: function () {
                try {
                    console.log("login: " + this.login + " " + this.password)
                    const user = {
                        login: this.login,
                        password: this.password,
                    }
                    console.log(user)
                    this.AUTH_REQUEST(user, "user").then(() => {
                        this.$router.push('/')
                    }).catch(console.log("err"))
                }catch(err){
                    console.log(err)
                }
            }
        }
    }
</script>
