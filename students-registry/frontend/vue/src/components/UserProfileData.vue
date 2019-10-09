<template>
    <div>
        <v-card>
            <v-card-text>
                <p></p>
                <p>{{user.name}}</p>
                <p>{{user.email}}</p>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    // import {mapGetters} from 'vuex'

    export default {
        computed: {
          //  ...mapGetters(['getUser']),
            user () {
                return this.$store.getters.getUser
            }
        },
    }
</script>

<style scoped>

</style>