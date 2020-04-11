import React from 'react';
import {connect} from "react-redux";
import Preloader from "../../common/preloader/Preloader";

import {compose} from "redux";
import {withAuthRedirect} from "../../../HOC/withAuthRedirect";
import {
    createTaskThunkCreator,
    deleteTaskThunkCreator,
    getPaginationTasksThunkCreator,
    setCurrentPage,
    setFirstPage,
    setLastPage,
    setNextPage,
    setPreviousPage,
    setToggleFetching,
    updateTaskThunkCreator
} from "../../../redux/tasks-reducer";
import ShowTasks from "./ShowTasks";

class TasksContainer extends React.Component {

    componentDidMount() {
        this.props.getPaginationTasksThunkCreator(this.props.currentPage, this.props.numberForPage);
    };
    componentDidUpdate(prevProps, prevState, snapshot) {

        console.log('pre '+prevProps.currentPage)
        console.log('fff '+this.props.currentPage)

        console.log('pretasks '+prevProps.tasks)
        console.log('ffftasks '+this.props.tasks)

        if (this.props.currentPage !== prevProps.currentPage) {
            this.props.getPaginationTasksThunkCreator(this.props.currentPage, this.props.numberForPage)
        }
        if (this.props.tasks.length !== prevProps.tasks.length) {
            this.props.getPaginationTasksThunkCreator(this.props.currentPage, this.props.numberForPage)
        }
    }

    render = () => {
        return (
            <>
                {this.props.isFetching ? <Preloader/> : null}
                <ShowTasks tasks={this.props.tasks}
                           currentPage={this.props.currentPage}
                           setCurrentPage={this.props.setCurrentPage}
                           setNextPage={this.props.setNextPage}
                           setPreviousPage={this.props.setPreviousPage}
                           isFetching={this.props.isFetching}
                           setFirstPage={this.props.setFirstPage}
                           setLastPage={this.props.setLastPage}
                           numberForPage={this.props.numberForPage}
                           getPaginationTasksThunkCreator={this.props.getPaginationTasksThunkCreator}
                           createTaskThunkCreator={this.props.createTaskThunkCreator}
                           updateTaskThunkCreator={this.props.updateTaskThunkCreator}
                           deleteTaskThunkCreator={this.props.deleteTaskThunkCreator}

                />
            </>
        )
    };
}

const mapStateToProps = (state) => {
    return {
        tasks: state.tasksPage.tasks,
        isFetching: state.tasksPage.isFetching,
        currentPage: state.tasksPage.currentPage,
        numberForPage: state.tasksPage.numberForPage
    }
};

const mapDispatchToProps = {
    setToggleFetching,
    getPaginationTasksThunkCreator,
    createTaskThunkCreator,
    updateTaskThunkCreator,
    setCurrentPage,
    setFirstPage,
    setLastPage,
    setNextPage,
    setPreviousPage,
    deleteTaskThunkCreator
};


export default compose(
    withAuthRedirect,
    connect(mapStateToProps, mapDispatchToProps))
(TasksContainer);
