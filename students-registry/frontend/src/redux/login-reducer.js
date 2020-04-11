import {CreateNewUser, loginAPI} from "../api/api";

const SET_SUBMIT_SUCCEEDED = "SET_SUBMIT_SUCCEEDED";
const SET_WRONG_CREDENTIAL = "SET_WRONG_CREDENTIAL";
const CREATE_NEW_CREDENTIALS = "CREATE_NEW_CREDENTIALS";
const CREATE_NEW_USER = "CREATE_NEW_USER";
const SET_SUCCESS_LOGIN = "SET_SUCCESS_LOGIN";
const SET_USER_EMAIL = "SET_USER_EMAIL";

const InitialState = {
    credential: {
        sessionId: null,
        startTime: 0,
        validTime: 0
    },

    isAuthenticated: false,
    userEmail: null,
    isUserCreated:false
};


let loginReducer = (state = InitialState, action) => {
    switch (action.type) {
        case SET_SUBMIT_SUCCEEDED:

            return {
                ...state,
                credential: action.credential
            };
        case SET_WRONG_CREDENTIAL:
            return {
                ...state,
                isAuthenticated: false
            };
        case SET_SUCCESS_LOGIN:
            return {
                ...state,
                isAuthenticated: true
            };
        case SET_USER_EMAIL:
            return {
                ...state,
                userEmail: action.email
            };
        case CREATE_NEW_CREDENTIALS:
            return {
                ...state
            };
        case CREATE_NEW_USER:
            return {
                ...state,
                isUserCreated:true
            };
        default: {
            return state;
        }
    }
};

export const setCredential = (credential) => ({type: SET_SUBMIT_SUCCEEDED, credential: credential});
export const setWrongCredential = () => ({type: SET_WRONG_CREDENTIAL});
export const setSuccessLogin = () => ({type: SET_SUCCESS_LOGIN});
export const setUserEmail = (email) => ({type: SET_USER_EMAIL, email: email});
export const createNewCredential = ()  => ({type: CREATE_NEW_CREDENTIALS});
export const createNewUser = () => ({type: CREATE_NEW_USER})

export const createNewCredentialThunkCreator=(newCredentials)=>{
return (dispatch)   =>{
    CreateNewUser.createNewCredentials(newCredentials).then(data=>{
        dispatch(createNewCredential());
    })
}
};

export const loginThunkCreator = (formData) => {
    return (dispatch) => {
        loginAPI.doLogin(formData).then(data => {
            dispatch(setCredential(data));
            dispatch(setSuccessLogin());
            dispatch(setUserEmail(formData.login));
        })
    }
};

export default loginReducer;
