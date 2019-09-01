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
              <h3 class="text--primary">Проект №{{this.project.projectId}}</h3>
            </v-card-title>
          </v-flex>
        </v-layout>
        <v-divider></v-divider>
        <v-layout row>
          <v-flex xs12>
            <v-card-text>
              <v-text-field
                      name="projectName"
                      label="Наименование проекта"
                      type="text"
                      v-model="editedName"
              ></v-text-field>
              <v-text-field
                      name="URL"
                      label="URL"
                      type="text"
                      v-model="editedURL"
              ></v-text-field>
              <v-text-field
                      name="Description"
                      label="Описание"
                      type="text"
                      v-model="editedDescription"
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
        props: ['project'],
        data () {
            return {
                modal: false,
                editedID: this.project['projectId'],
                editedDescription: this.project.description,
                editedURL: this.project.projectUrl,
                editedName: this.project.projectName
            }
        },
        methods: {
            ...mapActions(['updatePointAction']),
            onCancel () {
                this.editedID = this.project.projectId
                this.editedDescription = this.project.description
                this.editedURL = this.project.projectUrl
                this.editedName = this.project.projectName
                this.modal = false
            },
            onSave () {
                if (this.editedID !== '' && this.editedURL !== '' && this.editedDescription !== '') {
                    this.updatePointAction({
                        projectId: this.editedID,
                        description: this.editedDescription,
                        projectUrl: this.editedURL,
                        projectName: this.editedName
                    })
                    this.modal = false
                }
            }
        }
    }
</script>
