import React from 'react';
import Header from "./Header";
import {connect} from "react-redux";
import {setCredential, setSuccessLogin, setWrongCredential} from "../../redux/login-reducer";


class HeaderContainer extends React.Component {

    render() {
        return (<div>
                <Header {...this.props}/>
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
    setSuccessLogin
};

export default connect(mapStateToProps, mapDispatchToProps)(HeaderContainer);
