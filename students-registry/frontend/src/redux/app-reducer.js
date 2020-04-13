import {loginAPI} from "../api/api";
import {setCredential, setSuccessLogin, setUserEmail} from "./login-reducer";

const INITIALZED_SUCCESS = "INITIALZED_SUCCESS";
/**
 * Стоит ли вообще делать?
 * @type {{isInitialized: boolean}}
 */

const InitialState = {
    isInitialized: false,
};

const appReducer = (state = InitialState, action) => {
    switch (action.type) {
        case INITIALZED_SUCCESS:
            return {
                ...state,
                isInitialized: true
            };

        default:
            return state;
    }
};

export const initialSuccess = () => ({type: INITIALZED_SUCCESS});

export const initializeApp = () => (dispatch) => {
    let sessionId = localStorage.getItem('sessionId');
    let userEmail = localStorage.getItem('userEmail');
    if (typeof (sessionId) != 'undefined' && sessionId != null) {
        if (typeof (userEmail) != 'undefined' && userEmail != null) {
            loginAPI.checkSession().then(data => {
                if (data) {
                    let sessionData = {
                        sessionId: sessionId,
                        startTime: 0,
                        validTime: 0
                    }
                    dispatch(setCredential(sessionData));
                    dispatch(setSuccessLogin());
                    dispatch(setUserEmail(userEmail));

                }
                dispatch(initialSuccess())
            })
        } else dispatch(initialSuccess());
    } else dispatch(initialSuccess());
}

export default appReducer;

