import React from 'react';

class ShowProfile extends React.Component {

    render = () => {
        return (
            <>
                Profile
                email: {this.props.userFromServer.email}
                gitHubId: {this.props.userFromServer.gitHubId}
                name: {this.props.userFromServer.name}
                hours: {this.props.userFromServer.hours}
                createDate: {this.props.userFromServer.createDate}
                updateDate: {this.props.userFromServer.updateDate}
                status: {this.props.userFromServer.status}
            }
            </>
        );
    }
}

export default ShowProfile;
