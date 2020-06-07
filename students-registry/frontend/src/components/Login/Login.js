import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import css from './login.module.css';
import {Field, reduxForm} from "redux-form";
import {connect} from "react-redux";
import {
    loginThunkCreator,
    setCredential,
    setSuccessLogin,
    setUserEmail,
    setWrongCredential
} from "../../redux/login-reducer";
import {Link} from "react-router-dom";


const LoginForm = (props) => {
    return (
        <form className={css.formDesign} onSubmit={props.handleSubmit}>
            <div>
                <Field type={"email"} placeholder={"Login e-mail"} component={"input"} name={"login"}/>
            </div>

            <div>
                <Field type={"password"} placeholder={"Password"} component={"input"} name={"password"}/>
            </div>

            <div>
                <Field component={"input"} type={"checkbox"} name={"rememberme"}/> Remember me
            </div>

            <div>
                <button> OK</button>
            </div>

            <div>
                <Link to="create_user">Create new user</Link>
            </div>
        </form>
    )
};

const LoginReduxForm = reduxForm({
    // a unique name for the form
    form: 'login'
})(LoginForm);

const Login = (props) => {
    let onSubmit = (formData) => {
        props.loginThunkCreator(formData);
    };
    return (
        <span>
             <Container fluid={true} className={css.formDesign}>
                 <Row xs="2">
                 <Col></Col>
                 <Col>
                     <h2 className={css.formHeader}>Login</h2>
                       <LoginReduxForm onSubmit={onSubmit}/>
                 </Col>
                 <Col></Col>
                 </Row>
             </Container>

        </span>
    )

};
const mapStateToProps = (state) => {
    return {
        credential: state.login.credential,
        credentialStatus: state.login.credentialStatus,
        email: state.login.email
    }
};
let mpDispatchToProps = {
    setCredential,
    setWrongCredential,
    setSuccessLogin,
    setUserEmail,
    loginThunkCreator
};


export default connect(mapStateToProps, mpDispatchToProps)(Login);
