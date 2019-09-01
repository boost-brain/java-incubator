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
    // import {mapMutations} from 'vuex'

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
        },
        created () {
            console.log("created App")
         //   this.loadProjects()
        }
    };
</script>
