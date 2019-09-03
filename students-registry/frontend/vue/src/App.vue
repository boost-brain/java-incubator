<template>
  <v-app>
    <v-app-bar app>

      <v-toolbar-title>
        <router-link to="/" tag="span" class="pointer">Каталог студентов</router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn
                v-for="link in links"
                :key="link.title"
                :to="link.url"
                text
        >
          <v-icon left>{{link.icon}}</v-icon>
          {{link.title}}
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>

    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
    import {mapMutations} from 'vuex'
    // import { USER_REQUEST } from './store/actions/user'
    // import { AUTH_REQUEST } from './store/actions/auth'
    // import { AUTH_LOGOUT } from './store/actions/auth'

    export default {
        name: 'App',
        data () {
            return {
                drawer: true,
                links: [
                    {title: 'Home', icon: 'home', url: '/'},
                    {title: 'Проекты', icon: 'list', url: '/projects'},
                    {title: 'Студенты', icon: 'supervised_user_circle', url: '/users'},
                    {title: 'Создать', icon: 'create', url: '/new'}
                ]
            }
        },
        methods: {
            // loadProjects () {
            //     console.log("loadProjects()")
            //     this.$store.resource = this.$resource('http://localhost:8080/all')
            //     this.$store.resource.get().then(response => response.json())
            //         .then(projects => {
            //             for (var value of getProjects) {
            //                 this.addProjectMutation(value)
            //             }
            //         })
            // },
            // // ...mapMutations(['addPointMutation', 'updatePointMutation', 'removePointsMutation'])
            // ...mapMutations(['addProjectMutation', 'updateProjectMutation', 'removeProjectMutation'])
            ...mapMutations(['AUTH_LOGOUT'])
        },
        created () {
            //console.log("remove token")
            // commit(AUTH_LOGOUT, state) //4delete
            // localStorage.removeItem('user-token')
           // this.AUTH_LOGOUT()


         // //   this.loadProjects()
         //    if (this.$store.getters.isAuthenticated) {
         //        console.log("login: " + this.login + " " + this.password)
         //        const user = {
         //            login: this.login,
         //            password: this.password,
         //        }
         //        console.log(user)
         //        this.AUTH_REQUEST(user, "user")
         //    }
        }
    };
</script>
