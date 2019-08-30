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

    export default {
        name: 'App',
        data () {
            return {
                drawer: true,
                links: [
                    {title: 'Home', icon: 'home', url: '/'},
                    {title: 'Проекты', icon: 'list', url: '/projects'},
                    {title: 'Список', icon: 'table', url: '/table'},
                    {title: 'Создать', icon: 'create', url: '/new'}
                ]
            }
        },
        methods: {
            loadPoints () {
                console.log("loadPoints()")
                this.$store.resource = this.$resource('http://localhost:8080/all')
                this.$store.resource.get().then(response => response.json())
                    .then(points => {
                        for (var value of points) {
                            this.addPointMutation(value)
                        }
                    })
            },
            ...mapMutations(['addPointMutation', 'updatePointMutation', 'removePointsMutation'])
        },
        created () {
            console.log("created()")
            this.loadPoints()
        }
    };
</script>
