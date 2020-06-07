import React from "react";
import {Link} from "react-router-dom";

class UserInfoLogo extends React.Component {
    state = {
        userEmail: ""
    }

    setCurrentEmailByProps = () => {
        this.userEmail = this.props.userEmail;
    }

    componentDidMount() {
        this.setCurrentEmailByProps();
        this.userEmail = this.props.userEmail;
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.userEmail !== this.props.userEmail) {
            this.setCurrentEmailByProps()
        }
        this.userEmail = this.props.userEmail;
    }

    render() {
        console.log(`UserInfoLogo ` + this.userEmail + ' ' + this.props.userEmail)
        return (
            <div>
                <h3>
                    <Link to="/profile">{this.props.userEmail}</Link>

                </h3>
                <h3 onClick={() => this.props.logoutOnClick()}>
                    Logout
                </h3>
            </div>
        )
    }

}

export default UserInfoLogo;
