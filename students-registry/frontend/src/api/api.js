import * as axios from 'axios';

const API_LOCALHOST='http://localhost:9000/api';
const API_SERVER='http://185.255.135.104:9000/api/';
const API_URL=API_SERVER;

const loginEndPointURL = API_URL+'auth/login';

const CREATE_NEW_USER_ENDPOINT = API_URL+'users/create';
const CREATE_NEW_USER_CREDENTIALS_ENDPOINT = API_URL+'credentials/create';


const allprojectsEndPointURL = API_URL+'projects/projects-all';
const newProjectEndPointURL = API_URL+'projects/createProject';
const paginatableProjectEndPointURL = API_URL+'projects/watch/';
const UPDATE_PROJECT_ENDPOINT_URL = API_URL+'projects/update/';
const PROJECTS_COUNT_ENDPOINT_URL = API_URL+'projects/countProjects/';
const DELETE_PROJECT_ENDPOINT_URL = API_URL+'projects/delete/';             //'/delete/{projectId}'


const getTasksEndPointURL = API_URL+'tasks/page/';
const TASKS_COUNT_END_POINT = API_URL+'tasks/count/';
const NEW_TASK_END_POINT = API_URL+'tasks/create/';
const UPDATE_TASK_END_POINT = API_URL+'tasks/update/';
const DELETE_TASK_END_POINT = API_URL+'tasks/delete/';


const GET_ALL_USERS_END_POINT = API_URL+'users/users-all';
const USERS_COUNT_END_POINT = API_URL+'users/count';
const GET_PAGINATION_END_POINT = API_URL+'users/page/';

export const loginAPI = {
    doLogin(formData) {
        return axios.post(loginEndPointURL, {
            "login": formData.login,
            "password": formData.password
        }).then(response => {
            localStorage.setItem('sessionId', response.data.sessionId);
            return response.data;
        })
    }
};

export const CreateNewUser = {
    createNewUser(newUser) {
        return axios({
            method: 'POST',
            url: CREATE_NEW_USER_ENDPOINT,
            data: newUser
        }).then(function (response) {
            console.log(response);
        })
            .catch(function (error) {
                console.log(error);
            })
    },
    createNewCredentials(newCredentials) {
        return axios({
            method: 'POST',
            url: CREATE_NEW_USER_CREDENTIALS_ENDPOINT,
            data: newCredentials
        }).then(function (response) {
            console.log(response);
        })
            .catch(function (error) {
                console.log(error);
            })
    },

};


export const ProjectsAPI = {
    getAllProjects() {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(allprojectsEndPointURL, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        });
    },

    getProjectsNumber() {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(PROJECTS_COUNT_ENDPOINT_URL, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        });
    },

    createProject(newProject) {
        let sessionId = localStorage.getItem('sessionId');
        return axios({
            method: 'POST',
            url: newProjectEndPointURL,
            headers: {sessionId: sessionId},
            data: newProject
        }).then(function (response) {
            console.log(response);
        })
            .catch(function (error) {
                console.log(error);
            })
    },

    getProjectsWithPagination(currentPage, numberForPage) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(paginatableProjectEndPointURL + currentPage + "/" + numberForPage, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        });
    },

    updateProject(project) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.patch(UPDATE_PROJECT_ENDPOINT_URL, {headers: {sessionId: sessionId}}, {data: project}).then(response => {
            console.log(response);
            return response.data;
        })
            .catch(function (error) {
                console.log(error);
            });
    },
    deleteProject(idProject) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.delete(DELETE_PROJECT_ENDPOINT_URL + idProject, {headers: {sessionId: sessionId}}).then(response => {
            console.log(response);
            return response.data;
        })
            .catch(function (error) {
                console.log(error);
            });
    }
};


export const TasksAPI = {

    getTasks(page, size) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(getTasksEndPointURL + page + "/" + size, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        });
    },
    getNumberOfTasks() {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(TASKS_COUNT_END_POINT, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        })
    },
    createTask(newTask) {
        let sessionId = localStorage.getItem('sessionId');
        return axios({
            method: 'POST',
            url: NEW_TASK_END_POINT,
            headers: {sessionId: sessionId},
            data: newTask
        }).then(function (response) {
            console.log(response);
        })
            .catch(function (error) {
                console.log(error);
            })
    },
    updateTask(task) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.patch(UPDATE_TASK_END_POINT, {headers: {sessionId: sessionId}}, {data: task}).then(response => {
            console.log(response);
            return response.data;
        })
            .catch(function (error) {
                console.log(error);
            });
    },
    deleteTask(taskId) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.delete(DELETE_TASK_END_POINT + taskId, {headers: {sessionId: sessionId}}).then(response => {
            console.log(response);
            return response.data;
        })
            .catch(function (error) {
                console.log(error);
            });
    }
};


export const Usersapi = {

    getNumberOfUsers() {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(USERS_COUNT_END_POINT, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        })
    },
    getPaginationUsers(page, size) {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(GET_PAGINATION_END_POINT + page + "/" + size, {headers: {sessionId: sessionId}}).then(response => {

            return response.data;
        })
    },

    getAllUsers() {
        let sessionId = localStorage.getItem('sessionId');
        return axios.get(GET_ALL_USERS_END_POINT, {headers: {sessionId: sessionId}}).then(response => {
            return response.data;
        })
    },


};

