import {TasksAPI} from "../api/api";
import React from "react";
import {Redirect} from "react-router-dom";

const SET_TASKS = "SET_TASKS";
const SET_TOTAL_COUNT = "SET_TOTAL_COUNT";
const SET_TOTAL_PAGES = "SET_TOTAL_PAGE";
const SET_CURRENT_PAGE = "SET_CURRENT_PAGE";
const TOGGLE_IS_FETCHING = "TOGGLE_IS_FETCHING";
const SET_FIRST_PAGE = "SET_FIRST_PAGE";
const SET_LAST_PAGE = "SET_LAST_PAGE";
const SET_CREATED = "SET_CREATED";
const SET_NEXT_PAGE = "SET_NEXT_PAGE";
const SET_PREV_PAGE = "SET_PREV_PAGE";
const DELETE_ITEM = "DELETE_ITEM";
const SET_SHOW_MODAL = "SET_SHOW_MODAL";

const firstPage = 1;

const initialState = {
    tasks: [],
    numberForPage: 10,
    currentPage: firstPage,
    totalCount: 0,
    totalPages: 0,
    isFetching: false,
    created: false,
    showModal: false
};

let tasksReducer = (state = initialState, action) => {

        switch (action.type) {
            case SET_TASKS: {
                action.tasks.map(task => {
                    task.createDate = new Date(task.createDate).toLocaleDateString();
                    task.updateDate = new Date(task.updateDate).toLocaleDateString();
                    return {
                        task: task
                    }
                });
                return {
                    ...state,
                    tasks: action.tasks
                }
            }
            case SET_TOTAL_COUNT: {
                return {
                    ...state,
                    totalCount: action.totalCount
                }
            }
            case SET_TOTAL_PAGES: {
                return {
                    ...state,
                    totalPages: action.totalPages
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
            case
            SET_PREV_PAGE: {
                let newCurrentPage = state.currentPage - 1;
                if (newCurrentPage < firstPage) newCurrentPage = firstPage;
                return {
                    ...state,
                    currentPage: newCurrentPage
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
            case SET_FIRST_PAGE: {
                return {

                    ...state,
                    currentPage: firstPage
                }
            }
            case SET_LAST_PAGE: {
                return {
                    ...state,
                    currentPage: state.totalPages
                }
            }
            case
            TOGGLE_IS_FETCHING: {
                return {
                    ...state,
                    isFetching: action.isFetching
                }
            }
            case
            SET_CREATED:
                return {
                    ...state,
                    created: action.created
                };

            case
            SET_SHOW_MODAL:
                return {
                    ...state,
                    isShowModal: action.isShowModal

                };
            case
            DELETE_ITEM:
                let newTasks = state.tasks.filter(task => task.id !== action.id);
                return {
                    ...state,
                    projects: newTasks
                };

            default: {
                return state;
            }
        }
    }
;

export const setTasks = (tasks) => ({type: SET_TASKS, tasks});
export const deleteTask = (id) => ({type: DELETE_ITEM, id});
export const setFirstPage = () => ({type: SET_FIRST_PAGE});
export const setLastPage = () => ({type: SET_LAST_PAGE});
export const setCurrentPage = (currentPage) => ({type: SET_CURRENT_PAGE, currentPage});
export const setNextPage = () => ({type: SET_NEXT_PAGE});
export const setPreviousPage = () => ({type: SET_PREV_PAGE});
export const setTotalPages = (totalPages) => ({type: SET_TOTAL_PAGES, totalPages});
export const setTotalCount = (totalCount) => ({type: SET_TOTAL_COUNT, totalCount});
export const setToggleFetching = (isFetching) => ({type: TOGGLE_IS_FETCHING, isFetching});
export const setCreated = (created) => ({type: SET_CREATED, created});
export const setShowModal = (isShowModal) => ({type: SET_SHOW_MODAL, isShowModal});

export const getPaginationTasksThunkCreator = (currentPage, numberForPage) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        TasksAPI.getTasks(currentPage, numberForPage).then(data => {
            dispatch(setTasks(data));
        });
        TasksAPI.getNumberOfTasks().then(data => {
            dispatch(setTotalCount(data));
            dispatch(setTotalPages(Math.ceil(data / 10)));
            dispatch(setToggleFetching(false));
        })
    }
};

export const createTaskThunkCreator = (task) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        TasksAPI.createTask(task).then(data => {
            dispatch(setCreated(true));
            dispatch(setToggleFetching(false));
            return (
                <Redirect to="/tasks/"/>
            )
        });
    }
};

export const updateTaskThunkCreator = (task) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        TasksAPI.updateTask(task).then(data => {
            dispatch(setToggleFetching(false));
        });
    }
};

export const deleteTaskThunkCreator = (id) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        TasksAPI.deleteTask(id).then(data => {
             dispatch(deleteTask(id));
            dispatch(setToggleFetching(false));
        })
    }
};

export default tasksReducer;
