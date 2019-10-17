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
                                <v-toolbar-title>Форма входа</v-toolbar-title>
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
                                <router-link to="/new" class="btn btn-link">Регистрация нового студента</router-link>
                                <v-btn color="primary" type="submit" form="login-form">Вход</v-btn>
                                <br/>
                            </v-card-actions>
                            <p></p>
                            <v-alert  v-if="showInfo" type="info">
                                {{ info() }}
                            </v-alert>
                            <v-alert  v-if="showError" type="warning">
                                {{ error() }}
                            </v-alert>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </v-content>
    </v-app>
</template>

<style>
    .login {
        display: flex;
        flex-direction: column;
        width: 300px;
        padding: 10px;
    }
</style>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex'


    export default {
        name: 'login',
        data () {
            return {
                login: '',
                password: ''
            }
        },
        computed: {
            showError() {
                return JSON.stringify(this.getError()) !== JSON.stringify({})
            },
            showInfo() {
                return JSON.stringify(this.getInfo()) !== JSON.stringify({})
            },
        },
        methods: {
            ...mapActions(['AUTH_REQUEST']),
            ...mapMutations(['setError', 'setInfo']),
            ...mapGetters(['getInfo', 'getError']),
            info: function (){
                console.log(this.getInfo())
                return this.getInfo()
            },
            error: function (){
                console.log(this.getError())
                return this.getError()
            },
            doLogin: function () {
                try {
                    console.log("login: " + this.login + " " + this.password)
                    const user = {
                        login: this.login,
                        password: this.password,
                    }
                    console.log(user)
                    this.AUTH_REQUEST(user, "user").then(() => {
                        this.$router.push('/profile')
                    })
                }catch(err){
                    console.log(err)
                }
            }
        }
    }
</script>
