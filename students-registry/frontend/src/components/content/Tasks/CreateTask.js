import React from 'react';
import css from "../../Login/login.module.css";
import {Field, reduxForm} from "redux-form";
import {connect} from "react-redux";
import {compose} from "redux";
import {withAuthRedirect} from "../../../HOC/withAuthRedirect";
import {Modal} from "react-bootstrap";
import {createTaskThunkCreator, setCreated, setShowModal} from "../../../redux/tasks-reducer";
import {getAllUsersThunkCreator} from "../../../redux/users-reducer";
import {setAllProjectsThunkCreator} from "../../../redux/projects-reducer";

class createTaskForm extends React.Component {
    state = {
        show: false
    };


    constructor(props, context) {
        super(props, context);
        window.progressTaskModal = this;
    }

    componentDidMount() {
        this.handleShow();
        this.props.getAllUsersThunkCreator();
        this.props.setAllProjectsThunkCreator();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {

        if (this.props.created) {
            this.handleClose();
            this.props.setCreated(false);
        }
    }

    handleClose = () => this.setState({show: false});
    handleShow = () => this.setState({show: true});

    render() {
        return (
            <div>
                <Modal show={this.state.show} onHide={this.handleClose}>

                    <Modal.Header closeButton>
                        <Modal.Title>Create new task</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <form className={css.formDesign} onSubmit={this.props.handleSubmit}>
                            <div>Task name:
                                <Field type={"text"} placeholder={"Task Name"} component={"input"}
                                       name={"name"}/>
                            </div>

                            <div>Text:
                                <Field type={"text"} placeholder={"text"} component={"textarea"}
                                       name={"text"}/>
                            </div>

                            <div>
                                Project:
                                <Field component={"select"} name={"project"}>
                                    <option/>
                                    {this.props.allProjects && this.props.allProjects.map((project, index) => {
                                        return (
                                            <option value={project.projectId}>{project.projectName}</option>
                                        )
                                    })}
                                </Field>
                            </div>
                            <div>
                                Implementer:
                                <Field name="implementer" component="select">
                                    <option/>
                                    {this.props.allUsers && this.props.allUsers.map((user, index) => {
                                        return (
                                            <option value={user.email}>{user.name} ({user.email})</option>
                                        )
                                    })}
                                </Field>

                                <div>Author:
                                    <label>   {this.props.userEmail}</label>
                                </div>
                            </div>
                            <div>
                                <button> OK</button>
                            </div>
                        </form>
                    </Modal.Body>
                </Modal>
            </div>)
            ;
    };
}

const CreateTaskReduxForm = reduxForm({
    form: 'createTaskForm'
})(createTaskForm);


const CreateTask = (props) => {
    let onSubmit = (formData) => {
        let task = {
            author: props.userEmail,
            implementer: formData.implementer,
            project: formData.project,
            name: formData.name,
            text: formData.text,
        };
        props.createTaskThunkCreator(task);
        window.progressTaskModal.handleClose();
    };
    return (
        <CreateTaskReduxForm
            allUsers={props.allUsers}
            userEmail={props.userEmail}
            allProjects={props.allProjects}
            onSubmit={onSubmit}
            getAllUsersThunkCreator={props.getAllUsersThunkCreator}
            setAllProjectsThunkCreator={props.setAllProjectsThunkCreator}/>
    )
};

const mapStateToProps = (state) => {
    return {
        showModal: state.login.showModal,
        created: state.tasksPage.created,
        allUsers: state.usersContent.allUsers,
        userEmail: state.login.userEmail,
        allProjects: state.projectsPage.allProjects
    }
};
const mapDispatchToProps = {
    createTaskThunkCreator,
    setShowModal,
    setCreated,
    getAllUsersThunkCreator,
    setAllProjectsThunkCreator

};

export default compose(
    withAuthRedirect,
    connect(mapStateToProps, mapDispatchToProps))
(CreateTask);
