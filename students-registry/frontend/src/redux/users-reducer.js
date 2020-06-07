import {CreateNewUser, UsersAPI} from "../api/api";

const SET_USERS = "SET_USERS";
const SET_ALL_USERS = "SET_ALL_USERS";
const SET_TOTAL_COUNT = "SET_TOTAL_COUNT";
const SET_CURRENT_PAGE = "SET_CURRENT_PAGE";
const TOGGLE_IS_FETCHING = "TOGGLE_IS_FETCHING";
const SET_FIRST_PAGE = "SET_FIRST_PAGE";
const SET_LAST_PAGE = " SET_LAST_PAGE";
const SET_NEXT_PAGE = " SET_NEXT_PAGE";
const SET_PREV_PAGE = " SET_PREV_PAGE";
const SET_TOTAL_PAGES = "SET_TOTAL_PAGES";
const CREATE_NEW_USER = "CREATE_NEW_USER";


const firstPage = 1;

const initialState = {
    allUsers: [],
    users: [],
    numberForPage: 10,
    currentPage: firstPage,
    totalPages: 1,
    totalCount: 1,
    isFetching: false,
};

let usersReducer = (state = initialState, action) => {

    switch (action.type) {
        case SET_USERS: {
            action.users.map(user => {
                user.createDate = new Date(user.createDate).toLocaleDateString();
                user.updateDate = new Date(user.updateDate).toLocaleDateString();
                return {
                    user
                }
            });
            return {
                ...state,
                users: action.users
            }
        }

        case SET_ALL_USERS: {
            action.allUsers.map(user => {
                user.createDate = new Date(user.createDate).toLocaleDateString();
                user.updateDate = new Date(user.updateDate).toLocaleDateString();
                return {
                    user
                }
            });
            return {
                ...state,
                allUsers: action.allUsers
            }
        }


        case SET_TOTAL_COUNT: {
            return {
                ...state,
                totalCount: action.totalCount
            }
        }
        case
        SET_CURRENT_PAGE: {
            if (action.currentPage > state.totalPages) action.currentPage = state.totalPages;
            if (action.currentPage < firstPage) action.currentPage = firstPage;

            return {
                ...state,
                currentPage: action.currentPage
            }
        }
        case
        SET_PREV_PAGE: {
            let newCurrentPage = state.currentPage - 1;
            if (newCurrentPage > state.totalPages) newCurrentPage = state.totalPages;
            if (newCurrentPage < firstPage) newCurrentPage = firstPage;

            return {
                ...state,
                currentPage: newCurrentPage
            }
        }
        case
        SET_NEXT_PAGE: {
            let newCurrentPage = state.currentPage + 1;
            if (newCurrentPage > state.totalPages) newCurrentPage = state.totalPages;
            if (newCurrentPage < firstPage) newCurrentPage = firstPage;

            return {
                ...state,
                currentPage: newCurrentPage
            }
        }
        case TOGGLE_IS_FETCHING: {
            return {
                ...state,
                isFetching: action.isFetching
            }
        }
        case
        SET_FIRST_PAGE:
            return {
                ...state,
                currentPage: firstPage
            };
        case  SET_LAST_PAGE:
            return {
                ...state,
                currentPage: state.totalPages
            };
        case SET_TOTAL_PAGES:
            return {
                ...state,
                totalPages: action.totalPages
            };
        case CREATE_NEW_USER:
            return {
                ...state
            };
        default: {
            return state;
        }
    }
};

export const setUsers = (users) => ({type: SET_USERS, users: users});
export const setAllUsers = (allUsers) => ({type: SET_ALL_USERS, allUsers: allUsers});
export const setTotalUsersCount = (totalCount) => ({type: SET_TOTAL_COUNT, totalCount: totalCount});
export const setToggleFetching = (isFetching) => ({type: TOGGLE_IS_FETCHING, isFetching});
export const setTotalPages = (totalPages) => ({type: SET_TOTAL_PAGES, totalPages});
export const setFirstPage = () => ({type: SET_FIRST_PAGE});
export const setLastPage = () => ({type: SET_LAST_PAGE});
export const setCurrentPage = (currentPage) => ({type: SET_CURRENT_PAGE, currentPage});
export const setNextPage = () => ({type: SET_NEXT_PAGE});
export const setPrevPage = () => ({type: SET_PREV_PAGE});

export const createNewUser = (user) => ({type: CREATE_NEW_USER});

export const createNewUserThunkCreator = (newUser) => {
    return (dispatch) => {
        CreateNewUser.createNewUser(newUser).then(data => {
                dispatch(createNewUser(data));
            }
        )
    }
};


export const getUsersThunkCreator = (currentPage, numberForPage) => {
    return (dispatch) => {
        UsersAPI.getPaginationUsers(currentPage, numberForPage).then(data => {
            dispatch(setUsers(data));
        });
        UsersAPI.getNumberOfUsers().then(data => {
            dispatch(setTotalUsersCount(data));
            dispatch(setTotalPages(Math.ceil(data / numberForPage)))
        })
    }
};

export const getAllUsersThunkCreator = () => {
    return (dispatch) => {
        UsersAPI.getAllUsers().then(data => {
            dispatch(setAllUsers(data));
        });
    }
};

export default usersReducer;
