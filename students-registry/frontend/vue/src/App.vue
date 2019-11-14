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

<!--    <div>-->
<!--      <p></p>-->
<!--      <v-alert  v-if="showInfo" type="info">-->
<!--        {{ info() }}-->
<!--      </v-alert>-->
<!--      <v-alert  v-if="showError" type="warning">-->
<!--        {{ error() }}-->
<!--      </v-alert>-->
<!--    </div>-->

  </v-app>
</template>

<script>
  import { mapGetters, mapMutations} from 'vuex'

  export default {
    name: 'App',
    data () {
      return {
        drawer: true,
        links: [
          // {title: 'Home', icon: 'home', url: '/'},
          {title: 'Проекты', icon: 'list', url: '/projects'},
          {title: 'Задачи', icon: 'done_all', url: '/tasks'},
          {title: 'Студенты', icon: 'supervised_user_circle', url: '/users'},
          // {title: 'Создать', icon: 'create', url: '/new'},
          {title: 'Личный кабинет', icon: 'perm_identity', url: '/profile'},
          {title: 'Выход', icon: 'logout', url: '/logout'}
        ]
      }
    },
    methods: {
      ...mapMutations(['AUTH_LOGOUT', 'setError', 'setInfo']),
      ...mapGetters(['getInfo', 'getError']),
      info: function (){
        console.log(this.getInfo())
        return this.getInfo()
      },
      error: function (){
        console.log(this.getError())
        return this.getError()
      },
    },
    computed: {
      showError() {
        return JSON.stringify(this.getError()) !== JSON.stringify({})
      },
      showInfo() {
        return JSON.stringify(this.getInfo()) !== JSON.stringify({})
      },
    },
  };
</script>
