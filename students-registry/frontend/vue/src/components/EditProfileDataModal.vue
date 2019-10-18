<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-dialog width="400px" v-model="modal">
    <!--<v-btn class="warning" flat slot="activator">Изменить</v-btn>-->
    <template v-slot:activator="{ on }">
      <v-btn  class="warning" text v-on="on">Изменить</v-btn>
    </template>
    <v-card>
      <v-container>
        <v-layout row>
          <v-flex xs12>
            <v-card-title>
              <h4 class="text--primary">Редактирование профайла</h4>
            </v-card-title>
          </v-flex>
        </v-layout>
        <v-divider></v-divider>
        <v-layout row>
          <v-flex xs12>
            <v-card-text>
              <v-text-field
                      name="email"
                      label="Почта"
                      type="text"
                      v-model="editedEmail"
                      readonly
              ></v-text-field>
              <v-text-field
                      name="name"
                      label="Имя"
                      type="text"
                      v-model="editedName"
              ></v-text-field>
              <v-text-field
                      name="gitHubId"
                      label="github ID"
                      type="text"
                      v-model="editedGitHubId"
              ></v-text-field>
              <v-text-field
                      name="hours"
                      label="Часы"
                      type="text"
                      v-model="editedHours"
              ></v-text-field>
            </v-card-text>
          </v-flex>
        </v-layout>
        <v-divider></v-divider>
        <v-layout row>
          <v-flex xs12>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="onCancel">Отмена</v-btn>
              <v-btn class="success" text @click="onSave">Сохранить</v-btn>
            </v-card-actions>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        props: ['user'],
        data () {
            return {
                modal: false,
                editedName: this.user.name,
                editedEmail: this.user.email,
                editedGitHubId: this.user.gitHubId,
                editedHours: this.user.hours
            }
        },
        methods: {
            ...mapActions(['updateProfileUserAction']),
            onCancel () {
                this.editedName = this.user.name
                this.editedEmail = this.user.email
                this.editedGitHubId = this.user.gitHubId
                this.editedHours = this.user.hours
                this.modal = false
            },
            onSave () {
                if (this.editedName !== '' && this.editedEmail !== '') {
                    this.updateProfileUserAction({
                        name: this.editedName,
                        email: this.editedEmail,
                        gitHubId: this.editedGitHubId,
                        hours: this.editedHours,
                        createDate: 0,
                        updateDate: 0
                    })
                    this.modal = false
                }
            }
        }
    }
</script>
