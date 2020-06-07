import React from 'react';
import busyLogo from "../../assets/img/busy.gif";
import freeLogo from "../../assets/img/free.gif";
import academicLeaveLogo from "../../assets/img/academic.gif";
import temporalyInactiveLogo from "../../assets/img/inactive.gif";

export
const userStatus = [
    "WAITING_FOR_A_TASK",
    "BUSY",
    "TEMPORARILY_INACTIVE",
    "ACADEMIC_LEAVE"
]

export let busyLogoChoice = (status) => {
    if (status.toUpperCase() === "BUSY") return busyLogo;
    if (status.toUpperCase() === "WAITING_FOR_A_TASK") return freeLogo;
    if (status.toUpperCase() === "ACADEMIC_LEAVE") return academicLeaveLogo;
    if (status.toUpperCase() === "TEMPORARILY_INACTIVE") return temporalyInactiveLogo;
};
