<template>
    <v-container>
        <v-layout row>
            <v-flex xs12 sm6 offset-sm3>
                <h1 class="text--secondary mb-3">Регистрация</h1>
                <v-form ref="form" validation class="mb-3">
                    <v-text-field
                            autocomplete="off"
                            name="email"
                            label="Email:"
                            type="text"
                            :class="{'is-invalid': $v.email.$error}"
                            @blur="$v.email.$touch()"
                            :error-messages="$v.email.$dirty && !$v.email.required ? ['Обязательно к заполнению'] : !$v.email.email ? ['Введите корректный email'] : []"
                            v-model="email"
                    ></v-text-field>
                    <v-text-field
                            name="gitHubId"
                            label="Id на github.com:"
                            type="text"
                            :class="{'is-invalid': $v.gitHubId.$error}"
                            @blur="$v.gitHubId.$touch()"
                            :error-messages="$v.gitHubId.$dirty && !$v.gitHubId.required ? ['Обязательно к заполнению'] : []"
                            v-model="gitHubId"
                    ></v-text-field>
                    <v-text-field
                            label="Ваше имя:"
                            name="name"
                            type="text"
                            :class="{'is-invalid': $v.name.$error}"
                            @blur="$v.name.$touch()"
                            :error-messages="$v.name.$dirty && !$v.name.required ? ['Обязательно к заполнению'] : []"
                            v-model="name"
                    ></v-text-field>
                    <v-text-field
                            name="hours"
                            label="Сколько часов в день в среднем Вы готовы уделять проекту?"
                            v-model="hours"
                            :class="{'is-invalid': $v.hours.$error}"
                            @blur="$v.hours.$touch()"
                            :error-messages="$v.hours.$dirty && !$v.hours.required ? ['Обязательно к заполнению'] : !$v.hours.numeric ? ['Введите числовое значение'] : !$v.hours.minValue ? ['Число часов должно быть больше 1'] : []"
                            type="text">
                    </v-text-field>
                    <v-text-field
                            label="Задайте пароль:"
                            v-model="password"
                            :append-icon="show ? 'visibility' : 'visibility_off'"
                            @click:append="show = !show"
                            :type="show ? 'text' : 'password'"
                            :class="{'is-invalid': $v.password.$error}"
                            @blur="$v.password.$touch()"
                            :error-messages="!$v.password.minLength ? ['Минимальная длина пароля 6'] : []"
                            required>
                    </v-text-field>
<!--                    <pre>{{ $v.password }}</pre>-->
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
    import { required, email, numeric, minLength, minValue } from 'vuelidate/lib/validators'

    export default {
        name: "NewUser",
        data () {
            return {
                email: '',
                gitHubId: '',
                name: '',
                password: '',
                show: false,
                hours: 1,
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
                        gitHubId: this.gitHubId,
                        hours: this.hours,
                        name: this.name,
                        updateDate: this.updateDate
                    }
                    this.registerAction(user, "user")
                    this.$router.push('/login')
                }
            }
        },
        validations: {
            email: {
                required,
                email
            },
            gitHubId: {
                required
            },
            name: {
                required
            },
            hours: {
                required,
                numeric,
                minValue: minValue(1)
            },
            password: {
                minLength: minLength(6)
            }
        }
    }
</script>

<style scoped>

</style>