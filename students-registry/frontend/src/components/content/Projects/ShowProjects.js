import React from 'react';
import css from "./projects.module.css";
import {Pagination} from "react-bootstrap";
import CreateProject from "./CreateProject";
import {Link} from "react-router-dom";
import {ShowOneProject} from "./ShowOneProject";

class ShowProjects extends React.Component {

    onclickNewProject = () => {
        return (<>
                <CreateProject/>
            </>
        )
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.currentPage !== prevProps.currentPage) {
            this.props.getPaginationProjectsThunkCreator(this.props.currentPage, this.props.numberForPage)
        }
        if (this.props.projects !== prevProps.projects) {
            this.props.getPaginationProjectsThunkCreator(this.props.currentPage, this.props.numberForPage)
        }
    }

    render() {
        return (<div>
                <div>
                    <Link to="/newproject" onClick={this.onclickNewProject}>New Project</Link>
                </div>
                <div>
                    <Pagination className={css.pagination}>
                        <Pagination.First onClick={this.props.setFirstPage}/>
                        <Pagination.Ellipsis/>
                        <Pagination.Prev onClick={this.props.setPreviousPage}/>
                        <Pagination.Item>{this.props.currentPage}</Pagination.Item>
                        <Pagination.Next onClick={this.props.setNextPage}/>
                        <Pagination.Ellipsis/>
                        <Pagination.Last onClick={this.props.setLastPage}/>
                    </Pagination>

                </div>
                {this.props.projects.map(project => {
                    return (
                        <ShowOneProject project={project}
                                        updateProjectThunkCreator={this.props.updateProjectThunkCreator}
                                        deleteProjectThunkCreator={this.props.deleteProjectThunkCreator}/>)
                })}

            </div>
        )
    }
}

export default ShowProjects;
