import React from "react";

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
                <h3 onClick={() => this.props.emailOnClick()}>
                    {this.props.userEmail}
                </h3>
                <h3 onClick={() => this.props.logoutOnClick()}>
                    Logout
                </h3>
            </div>
        )
    }

}

export default UserInfoLogo;
