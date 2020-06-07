import React from 'react';
import Header from "./Header";
import {connect} from "react-redux";
import {logoutThunkCreator, setCredential, setSuccessLogin, setWrongCredential} from "../../redux/login-reducer";


class HeaderContainer extends React.Component {

    emailOnClick = () => {
        alert("profile")
    }

    logoutOnClick = () => {
        this.props.logoutThunkCreator()
    }

    render() {
        return (<div>
                <Header userEmail={this.props.userEmail}
                        emailOnClick={this.emailOnClick}
                        logoutOnClick={this.logoutOnClick}
                        isAuthenticated={this.props.isAuthenticated}

                />
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    credential: state.login.credential,
    isAuthenticated: state.login.isAuthenticated,
    userEmail: state.login.userEmail
});

const mapDispatchToProps = {
    setCredential,
    setWrongCredential,
    setSuccessLogin,
    logoutThunkCreator
};

export default connect(mapStateToProps, mapDispatchToProps)(HeaderContainer);
