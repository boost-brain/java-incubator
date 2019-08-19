<template>
  <v-dialog width="400px" v-model="modal">
    <v-btn class="warning" flat slot="activator">Изменить</v-btn>

    <v-card>
      <v-container>
        <v-layout row>
          <v-flex xs12>
            <v-card-title>
              <h3 class="text--primary">{{this.point.id}}</h3>
            </v-card-title>
          </v-flex>
        </v-layout>
        <v-divider></v-divider>
        <v-layout row>
          <v-flex xs12>
            <v-card-text>
              <b>editedID</b>
              <v-text-field
                name="status"
                label="Статус"
                type="text"
                v-model="editedStatus"
              ></v-text-field>
              <v-text-field
                name="A_ADD"
                label="A_ADD"
                type="text"
                v-model="editedA_ADD"
              ></v-text-field>
            </v-card-text>
          </v-flex>
        </v-layout>
        <v-divider></v-divider>
        <v-layout row>
          <v-flex xs12>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn flat @click="onCancel">Отмена</v-btn>
              <v-btn class="success" flat @click="onSave">Сохранить</v-btn>
            </v-card-actions>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>
  import { mapActions } from 'vuex'

  export default {
    props: ['point'],
    data () {
      return {
        modal: false,
        editedID: this.point['id'],
        editedText: this.point.text,
        editedSubject: this.point.subject,
      }
    },
    methods: {
      ...mapActions(['updatePointAction']),
      onCancel () {
        this.editedID = this.point.id
        this.editedText = this.point.text
        this.editedSubject = this.point.subject
        this.modal = false
      },
      onSave () {
        if (this.editedID !== '' && this.editedSubject !== '' && this.editedText !== '') {
          this.updatePointAction({
            id: this.editedID,
            subject: this.editedSubject,
            text: this.editedText
          })
          this.modal = false
        }
      }
    }
  }
</script>
