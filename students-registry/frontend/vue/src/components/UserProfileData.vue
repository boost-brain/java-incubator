<template>
    <div>
        <v-app id="inspire">
            <v-card>
                <v-list-item three-line>
                    <v-list-item-content>
                        <div class="overline mb-4">Новобранец</div>
                        <v-list-item-title>{{user.name}}</v-list-item-title>
                        <v-list-item-subtitle> {{user.email}} </v-list-item-subtitle>
                    </v-list-item-content>
                    <v-list-item-avatar
                            tile
                            size="80"
                            color="grey"
                    ></v-list-item-avatar>
                </v-list-item>
            </v-card>
        </v-app>
    </div>
</template>

<script>
    export default {
        computed: {
            user () {
                return this.$store.getters.getUser
            }
        },
    }
</script>

<style scoped>

</style>