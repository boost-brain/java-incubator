<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div id="app">
        <v-app id="inspire">
            <v-data-table
                    :headers="headers"
                    :items="myPoints"
                    sort-by="id"
                    class="elevation-1"
            >
                <template v-slot:top>
                    <v-toolbar flat color="white">
                        <v-toolbar-title>Список студентов</v-toolbar-title>
                        <v-divider
                                class="mx-4"
                                inset
                                vertical
                        ></v-divider>
                        <v-spacer></v-spacer>
                        <v-dialog v-model="dialog" max-width="500px">
                            <template v-slot:activator="{ on }">
                                <v-btn color="primary" dark class="mb-2" v-on="on">Ввести вручную</v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                    <span class="headline">{{ formTitle }}</span>
                                </v-card-title>

                                <v-card-text>
                                    <v-container grid-list-md>
                                        <v-layout wrap>
                                            <v-flex xs12 sm6 md4>
                                                <v-text-field v-model="editedItem.id" label="ID"></v-text-field>
                                            </v-flex>
                                            <v-flex xs12 sm6 md4>
                                                <v-text-field v-model="editedItem.text" label="Text"></v-text-field>
                                            </v-flex>
                                            <v-flex xs12 sm6 md4>
                                                <v-text-field v-model="editedItem.subject" label="Subject"></v-text-field>
                                            </v-flex>
                                        </v-layout>
                                    </v-container>
                                </v-card-text>

                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                                    <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-toolbar>
                </template>
                <template v-slot:item.action="{ item }">
                    <v-icon
                            small
                            class="mr-2"
                            @click="editItem(item)"
                    >
                        edit
                    </v-icon>
                    <v-icon
                            small
                            @click="deleteItem(item)"
                    >
                        delete
                    </v-icon>
                </template>
                <template v-slot:no-data>
                    <v-btn color="primary" @click="init">Reset</v-btn>
                </template>
            </v-data-table>
        </v-app>
    </div>
</template>

<script>

    // import EditPointModal from '../components/EditPointModal'
    // import MenuIcon from "vue-material-design-icons/Menu.vue"

    export default {
        data () {
            return {
                dialog: false,
                singleSelect: false,
                search: '',
                selected:[],
                headers: [
                    { text: 'id', value: 'id' },
                    { text: 'ФИО', value: 'text' },
                    { text: 'email', value: 'subject' },
                    { text: 'Actions', value: 'action', sortable: false },
                ],
                editedIndex: -1,
                editedItem: {
                    id: 0,
                    text: 0,
                    subject: 0,
                },
                defaultItem: {
                    id: 0,
                    text: 0,
                    subject: 0,
                },
            }
        },

        watch: {
            dialog (val) {
                val || this.close()
            },
        },

        components: {
            //MenuIcon
        },

        computed: {
            formTitle () {
                return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
            },
            myPoints () {
                return this.$store.getters.points
            }
        },
        methods:{
            init() {
                console.log("init run");
            },
            showAlert(a){
                if (event.target.classList.contains('btn__content')) return;
                alert('Alert! \n' + a.name);
            },
            editItem (item) {
                this.editedIndex = this.myPoints.indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.dialog = true
            },

            deleteItem (item) {
                const index = this.myPoints.indexOf(item)
                confirm('Are you sure you want to delete this item?') && this.myPoints.splice(index, 1)
            },

            close () {
                this.dialog = false
                setTimeout(() => {
                    this.editedItem = Object.assign({}, this.defaultItem)
                    this.editedIndex = -1
                }, 300)
            },

            save () {
                if (this.editedIndex > -1) {
                    Object.assign(this.myPoints[this.editedIndex], this.editedItem)
                } else {
                    this.myPoints.push(this.editedItem)
                }
                this.close()
            },
        }
    }
</script>

<style scoped>

</style>
