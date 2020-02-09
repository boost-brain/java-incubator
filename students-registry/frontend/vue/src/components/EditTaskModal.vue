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
              <h3 class="text--primary">Задача №{{this.task.id}}</h3>
            </v-card-title>
          </v-flex>
        </v-layout>
        <v-divider></v-divider>
        <v-layout row>
          <v-flex xs12>
            <v-card-text>
              <v-text-field
                      name="author"
                      label="Автор задачи"
                      type="text"
                      v-model="editedAuthor"
              ></v-text-field>
              <v-text-field
                      name="createDate"
                      label="Дата создания"
                      type="text"
                      v-model="editedCreateDate"
              ></v-text-field>
              <v-text-field
                      name="implementer"
                      label="Исполнитель"
                      type="text"
                      v-model="editedImplementer"
              ></v-text-field>
              <v-text-field
                      name="name"
                      label="Наименование"
                      type="text"
                      v-model="editedName"
              ></v-text-field>
              <v-text-field
                      name="project"
                      label="Номер проекта"
                      type="text"
                      v-model="editedProject"
              ></v-text-field>
              <v-text-field
                      name="text"
                      label="Текст"
                      type="text"
                      v-model="editedText"
              ></v-text-field>
              <v-text-field
                      name="updateDate"
                      label="Дата обновления"
                      type="text"
                      v-model="editedUpdateDate"
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
        props: ['task'],
        data () {
            return {
                modal: false,
                editedAuthor: this.task.author,
                editedCreateDate: this.task.createDate,
                editedImplementer: this.task.implementer,
                editedName: this.task.name,
                editedProject: this.task.project,
                editedText: this.task.text,
                editedUpdateDate: this.task.updateDate
            }
        },
        methods: {
            ...mapActions(['updateTaskAction']),
            onCancel () {
                this.editedAuthor = this.task.authot
                this.editedCreateDate = this.task.createDate
                this.editedImplementer = this.task.implementer
                this.editedName = this.task.name
                this.editedProject = this.task.project
                this.editedText = this.task.text
                this.editedUpdateDate = this.task.updateDate
                this.modal = false
            },
            onSave () {
                if (this.editedName !== '' && this.editedProject !== '' && this.editedAuthor !== '') {
                    this.updateTaskAction({
                        id: this.task.id,
                        author: this.editedAuthor,
                        createDate: this.editedCreateDate,
                        implementer: this.editedImplementer,
                        name: this.editedName,
                        project: this.editedProject,
                        text: this.editedText,
                        updateDate: this.editedUpdateDate,
                    })
                    this.modal = false
                }
            }
        }
    }
</script>
