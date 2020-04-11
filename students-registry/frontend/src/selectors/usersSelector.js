import React from 'react';
import {getAllUsersThunkCreator} from "../redux/users-reducer";
import {createSelector} from "reselect";

export const getUsers = (state) => {
    if (!state.usersContent.allUsers) {
        getAllUsersThunkCreator();
    }
    return state.usersContent.allUsers;
};

export const getUsersSelectorForCreateTaskForm = createSelector(getUsers, (allUsers) => {
    return allUsers.map((user) => {
        return {
            label: user.name,
            value: user.email,
        }
    })
});

