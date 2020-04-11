import React from 'react';
import css from './users.module.css';
import freeLogo from '../../../assets/img/free.gif';
import busyLogo from '../../../assets/img/busy.gif'
import academicLeaveLogo from '../../../assets/img/academic.gif';
import temporalyInactiveLogo from '../../../assets/img/inactive.gif';
import {Pagination} from "react-bootstrap";

let busyLogoChoice = (status) => {
    if (status.toUpperCase() === "BUSY") return busyLogo;
    if (status.toUpperCase() === "WAITING_FOR_A_TASK") return freeLogo;
    if (status.toUpperCase() === "ACADEMIC_LEAVE") return academicLeaveLogo;
    if (status.toUpperCase() === "TEMPORARILY_INACTIVE") return temporalyInactiveLogo;
};

const Users = (props) => {
    const handleFirstSelect = () => {
        props.setFirstPage();
    };
    const handleLastSelect = () => {
        props.setLastPage();
    };
    const handlePrevSelect = () => {
        props.setCurrentPage(props.currentPage - 1);
    };
    const handleNextSelect = () => {
        props.setCurrentPage(props.currentPage + 1);
    };
    return (<div className={css.thead}>
            <div>
                <Pagination className={css.pagination}>
                    <Pagination.First onClick={handleFirstSelect}/>
                    <Pagination.Ellipsis/>
                    <Pagination.Prev onClick={handlePrevSelect}/>
                    <Pagination.Item>{props.currentPage}</Pagination.Item>
                    <Pagination.Next onClick={handleNextSelect}/>
                    <Pagination.Ellipsis/>
                    <Pagination.Last onClick={handleLastSelect}/>
                </Pagination>
            </div>
            <container className={css.container}>
            <table className="table table-hover table-bordered table-striped table-responsive">
                <thead>
                <tr>
                    <th scope="col " className={css.name}>Name</th>
                    <th scope="col " className={css.email}>E-mail</th>
                    <th scope="col " className={css.github}>GitHubId</th>
                    <th scope="col " className={css.hours}>Hours in week</th>
                    <th scope="col " className={css.createDate}>Create Date</th>
                    <th scope="col " className={css.updateDate}>Update Date</th>
                    <th scope="col " className={css.status}>Status</th>
                </tr>
                </thead>
                <tbody>
                {props.users.map(user => {
                    return (
                        <tr>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                            <td>{user.gitHubId}</td>
                            <td>{user.hours}</td>
                            <td>{user.createDate}</td>
                            <td>{user.updateDate}</td>
                            <td><img className={css.statusLogo} src={busyLogoChoice(user.status)}/></td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
            </container>
        </div>
    )
};

export default Users;
