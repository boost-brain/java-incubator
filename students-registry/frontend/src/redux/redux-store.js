import {applyMiddleware, combineReducers, createStore} from "redux";
import {reducer as formReducer} from 'redux-form'
import loginReducer from "./login-reducer";
import thunkMiddleware from "redux-thunk";
import projectsReducer from "./projects-reducer";
import tasksReducer from "./tasks-reducer";
import usersReducer from "./users-reducer";

let reducers = combineReducers(
    {
        form: formReducer,
        login: loginReducer,
        projectsPage: projectsReducer,
        tasksPage: tasksReducer,
        taskUpdateForm: formReducer,
        createTaskForm: formReducer,
        createProjectForm: formReducer,
        updateProjectForm: formReducer,
            createUser:formReducer,
        usersContent: usersReducer
    });

let store = createStore(reducers,
    applyMiddleware(thunkMiddleware));
window.store = store;

export default store;
