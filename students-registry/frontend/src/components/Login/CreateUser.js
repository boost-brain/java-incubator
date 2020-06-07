import css from "./login.module.css";
import {Col, Container, Row} from "react-bootstrap";
import React from "react";
import {Field, reduxForm} from "redux-form";
import {connect} from "react-redux";
import {createNewUserThunkCreator} from "../../redux/users-reducer";
import {createNewCredentialThunkCreator, createNewUser} from "../../redux/login-reducer";

const CreateUserForm = (props) => {
    return (
        <form className={css.formDesign} onSubmit={props.handleSubmit}>
            <div>Name:
                <Field type={"text"} placeholder={"Name"} component={"input"} name={"name"}/>
            </div>
            <div>E-mail:
                <Field type={"email"} placeholder={"E-mail"} component={"input"} name={"email"}/>
            </div>
            <div>Password:
                <Field type={"password"} placeholder={"Password"} component={"input"} name={"password"}/>
            </div>
            <div> GitHubId:
                <Field type={"text"} placeholder={"GitHubId"} component={"input"} name={"gitHubId"}/>
            </div>
            <div>Hours in week:
                <Field type={"text"} placeholder={"Hours in week"} component={"input"} name={"hours"}/>
            </div>

            <div>
                <button> OK</button>
            </div>
        </form>
    )
};

const CreateUserReduxForm = reduxForm({
    // a unique name for the form
    form: 'createUser'
})(CreateUserForm);

const CreateUser = (props) => {
    let onSubmit = (formData) => {
        debugger
        let newUser = {
            name: formData.name,
            email: formData.email,
            gitHubId: formData.gitHubId,
            hours: formData.hours
        };
        props.createNewUserThunkCreator(newUser);
        let newCredentials = {
            login: formData.email,
            password: formData.password
        };
        props.createNewCredentialThunkCreator(newCredentials);
        props.createNewUser();
    };
    return (
        <span>
             <Container fluid={true} className={css.formDesign}>
                 <Row xs="2">
                 <Col></Col>
                 <Col>
                     <h2 className={css.formHeader}>Create new User</h2>
                       <CreateUserReduxForm onSubmit={onSubmit}
                                            createNewUserThunkCreator={props.createNewUserThunkCreator}
                                            createNewCredentialThunkCreator={props.createNewCredentialThunkCreator}
                                            createNewUser={props.createNewUser}/>
                 </Col>
                 <Col></Col>
                 </Row>
             </Container>

        </span>
    )

};
const mapStateToProps = (state) => {
    return {
    }
};
let mpDispatchToProps = {
    createNewUserThunkCreator,
    createNewCredentialThunkCreator,
    createNewUser
};


export default connect(mapStateToProps, mpDispatchToProps)(CreateUser);
