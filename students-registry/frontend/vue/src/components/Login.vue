/**
* Created by vouill on 11/13/17.
*/

<template>
    <div>
        <form class="login" @submit.prevent="doLogin">
            <h1>Вход</h1>
            <label>Пользователь</label>
            <input required v-model="login" type="text" placeholder="Snoopy"/>
            <label>Пароль</label>
            <input required v-model="password" type="password" placeholder="Password"/>
            <hr/>
            <button type="submit">Login</button>
        </form>
    </div>
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
    // import {AUTH_REQUEST} from '../store/actions/auth'
    import { mapActions } from 'vuex'

    export default {
        name: 'login',
        data () {
            return {
                login: 'dogo',
                password: 'dogy',
            }
        },
        methods: {
            ...mapActions(['AUTH_REQUEST']),
            doLogin: function () {
                // const { username, password } = this
                console.log("login: " + this.login + " " + this.password)
                // this.$store.dispatch(AUTH_REQUEST, { username, password }).then(() => {
                //     this.$router.push('/')
                // })
                const user = {
                    login: this.login,
                    password: this.password,
                }
                console.log(user)
                this.AUTH_REQUEST(user, "user")
                this.$router.push('/')
            }
        },
        created () {
            this.doLogin()
        }
    }
</script>
