import {ProjectsAPI} from '../api/api';
import {Redirect} from "react-router-dom";
import React from "react";

const SET_PROJECTS = "SET_PROJECTS";
const SET_ALL_PROJECTS = "SET_ALL_PROJECTS";
const SET_TOTAL_COUNT = "SET_TOTAL_COUNT";
const SET_TOTAL_PAGES = "SET_TOTAL_PAGES";
const SET_CURRENT_PAGE = "SET_CURRENT_PAGE";
const SET_CREATED = "SET_CREATED";
const TOGGLE_IS_FETCHING = "TOGGLE_IS_FETCHING";
const SET_SHOW_MODAL = "SET_SHOW_MODAL";
const SET_FIRST_PAGE = "SET_FIRST_PAGE";
const SET_LAST_PAGE = "SET_LAST_PAGE";
const SET_NEXT_PAGE = "SET_NEXT_PAGE";
const SET_PREV_PAGE = "SET_PREV_PAGE";
const DELETE_ITEM = "DELETE_ITEM";

const firstPage = 1;

const initialState = {
    allProjects: [],
    projects: [],
    numberForPage: 10,
    currentPage: firstPage,
    totalCount: 0,
    totalPages: 0,
    isFetching: false,
    created: false,
    showModal: false
};

let projectsReducer = (state = initialState, action) => {

        switch (action.type) {
            case SET_PROJECTS: {
                return {
                    ...state,
                    projects: action.projects
                }
            }
            case SET_ALL_PROJECTS: {
                return {
                    ...state,
                    allProjects: action.allProjects
                }
            }
            case
            SET_TOTAL_COUNT: {
                return {
                    ...state,
                    totalCount: action.totalCount
                }
            }
            case
            SET_NEXT_PAGE: {
                let newCurrentPage = state.currentPage + 1;
                if (newCurrentPage > state.totalPages) newCurrentPage = state.totalPages;
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
            case DELETE_ITEM:
                let newProjects = state.projects.filter(project => project.projectId !== action.projectId);
                return {
                    ...state,
                    projects: newProjects
                };
            default: {
                return state;
            }
        }
    }
;

export const setProjects = (projects) => ({type: SET_PROJECTS, projects});
export const setAllProjects = (allProjects) => ({type: SET_ALL_PROJECTS, allProjects});
export const setToggleFetching = (isFetching) => ({type: TOGGLE_IS_FETCHING, isFetching});
export const setCreated = (created) => ({type: SET_CREATED, created});
export const setShowModal = (isShowModal) => ({type: SET_SHOW_MODAL, isShowModal});
export const setTotalPages = (totalPages) => ({type: SET_TOTAL_PAGES, totalPages});
export const setFirstPage = () => ({type: SET_FIRST_PAGE});
export const setLastPage = () => ({type: SET_LAST_PAGE});
export const setNextPage = () => ({type: SET_NEXT_PAGE});
export const setPreviousPage = () => ({type: SET_PREV_PAGE});
export const deleteProject = (projectId) => ({type: DELETE_ITEM, projectId});
export const setCurrentPage = (currentPage) => ({type: SET_CURRENT_PAGE, currentPage});

export const getPaginationProjectsThunkCreator = (currentPage, numberForPage) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        ProjectsAPI.getProjectsWithPagination(currentPage, numberForPage).then(data => {
            dispatch(setProjects(data.content));
            dispatch(setTotalPages(data.totalPages));
            dispatch(setCreated(false));
            dispatch(setToggleFetching(false));
        });
    }
};

export const setAllProjectsThunkCreator = () => {
    return (dispatch) => {
        ProjectsAPI.getAllProjects().then(data => {
            dispatch(setAllProjects(data));
        })
    }
};

export const createProjectThunkCreator = (project) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        ProjectsAPI.createProject(project).then(data => {
            dispatch(setCreated(true));
            dispatch(setToggleFetching(false));
            return (
                <Redirect to="/projects/"/>
            )
        });
    }
};

export const updateProjectThunkCreator = (project) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        ProjectsAPI.updateProject(project).then(data => {
            dispatch(setToggleFetching(false));
        });

    }
};

export const deleteProjectThunkCreator = (id) => {
    return (dispatch) => {
        dispatch(setToggleFetching(true));
        ProjectsAPI.deleteProject(id).then(data => {
            dispatch(deleteProject(id));
            dispatch(setToggleFetching(false));
        })
    }
};

export default projectsReducer;
