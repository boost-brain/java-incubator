import React from 'react';
import css from './profile.module.css';

class ShowProfile extends React.Component {
    state = {
        nameEditMode: false,
        gitHubIdEditMode: false,
        hoursEditMode: false,
        statusEditMode: false,
        name: this.props.userFromServer.name,
        gitHubId: this.props.userFromServer.gitHubId,
        hours: this.props.userFromServer.hours,
        status: this.props.userFromServer.status
    };

    activateNameEditMode = () => {
        this.setState({nameEditMode: true})
    };
    deActivateNameEditMode = () => {
        this.setState({nameEditMode: false})
        this.doUpdate();
    };
    activateGitHubIdEditMode = () => {
        this.setState({gitHubIdEditMode: true})
    };
    deActivateGitHubIdEditMod = () => {
        this.setState({gitHubIdEditMode: false})
        this.doUpdate();
    };
    activateHoursEditMode = () => {
        this.setState({hoursEditMode: true})
    };
    deActivateHoursEditMode = () => {
        this.setState({hoursEditMode: false})
        this.doUpdate();
    };
    activateStatusEditMode = () => {
        this.setState({statusEditMode: true})
    };
    deActivateStatusEditMode = () => {
        this.setState({statusEditMode: false})
        this.doUpdate();
    };

    doUpdate = () => {
        this.props.updateUserThunkCreator({
            email: this.props.userFromServer.email,
            name: this.state.name,
            gitHubId: this.state.gitHubId,
            hours: this.state.hours,
            status: this.state.status
        })
    };

    nameOnChange = (e) => {
        this.setState({name: e.currentTarget.value});
    };
    gitHubIdOnChange = (e) => {
        this.setState({gitHubId: e.currentTarget.value});
    };
    hoursOnChange = (e) => {
        this.setState({hours: e.currentTarget.value});
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.userFromServer.name !== this.props.userFromServer.name) {
            this.setState({name: this.props.userFromServer.name})
        }
        if (prevProps.userFromServer.gitHubId !== this.props.userFromServer.gitHubId) {
            this.setState({gitHubId: this.props.userFromServer.gitHubId})
        }
        if (prevProps.userFromServer.hours !== this.props.userFromServer.hours) {
            this.setState({hours: this.props.userFromServer.hours})
        }
        if (prevProps.userFromServer.status !== this.props.userFromServer.status) {
            this.setState({status: this.props.userFromServer.status})
        }


    }

    render = () => {
        return (
            <>
                <container className={css.container}>
                    Profile
                    <h4 className={css.email}>
                        email: {this.props.userFromServer.email}
                    </h4>
                    <h4 className={css.name}>
                        {!this.state.nameEditMode &&
                        <span onClick={this.activateNameEditMode}>Name: {this.state.name} </span>
                        }

                        {this.state.nameEditMode &&
                        <input autoFocus={true} onBlur={this.deActivateNameEditMode}
                               onChange={this.nameOnChange} value={this.state.name}/>
                        }
                    </h4>
                    <h4 className={css.gitHubId}>
                        {!this.state.gitHubIdEditMode &&
                        <span onClick={this.activateGitHubIdEditMode}>gitHubId: {this.state.gitHubId} </span>
                        }

                        {this.state.gitHubIdEditMode &&
                        <input autoFocus={true} onBlur={this.deActivateGitHubIdEditMod}
                               onChange={this.gitHubIdOnChange} value={this.state.gitHubId}/>
                        }
                    </h4>
                    <h4 className={css.hours}>
                        {!this.state.hoursEditMode &&
                        <span onClick={this.activateHoursEditMode}>gitHubId: {this.state.hours} </span>
                        }

                        {this.state.hoursEditMode &&
                        <input autoFocus={true} onBlur={this.deActivateHoursEditMode}
                               onChange={this.hoursOnChange} value={this.state.hours}/>
                        }
                    </h4>
                </container>
                }
                }
                }
            </>
        );
    }
}

export default ShowProfile;

// email: {this.props.userFromServer.email}
// gitHubId: {this.props.userFromServer.gitHubId}
// name: {this.props.userFromServer.name}
// hours: {this.props.userFromServer.hours}
// createDate: {this.props.userFromServer.createDate}
// updateDate: {this.props.userFromServer.updateDate}
// status: {this.props.userFromServer.status}
