import {UsersAPI} from "../api/api";

const TOGGLE_IS_FETCHING = "TOGGLE_IS_FETCHING";
const SET_CURRENT_USER = "SET_CURRENT_USER";

const InitialState = {
    isFetching: false,
    userFromServer: {
        email: "",
        gitHubId: "",
        name: "",
        hours: 0,
        createDate: "",
        updateDate: "",
        status: ""
    }
}

let profileReducer = (state = InitialState, action) => {
    switch (action.type) {
        case
        TOGGLE_IS_FETCHING: {
            return {
                ...state,
                isFetching: action.isFetching
            }
        }
        case
        SET_CURRENT_USER: {
            let newUserFromServer = {
                email: action.user.email,
                gitHubId: action.user.gitHubId,
                name: action.user.name,
                hours: action.user.hours,
                createDate: new Date(action.user.createDate).toLocaleDateString(),
                updateDate: new Date(action.user.updateDate).toLocaleDateString(),
                status: action.user.status
            }
            return {
                ...state,
                userFromServer: newUserFromServer
            }
        }

        default: {
            return state;
        }
    }
}

export const setToggleFetching = (isFetching) => ({type: TOGGLE_IS_FETCHING, isFetching});
export const setCurrentUser = (user) => ({type: SET_CURRENT_USER, user});


export const setUserFromServer = () => {
    setToggleFetching(true);
    return (dispatch) => {
        UsersAPI.readCurrentUserFromEmail().then(data => {
            dispatch(setCurrentUser(data));
            setToggleFetching(false);
        })
    }
};
export const updateUserThunkCreator = (userForUpdate) => {
    setToggleFetching(true);
    return (dispatch) => {
        UsersAPI.updateCurrentUser(userForUpdate).then(data => {
            setToggleFetching(false);
        })
    }
}


export default profileReducer;


