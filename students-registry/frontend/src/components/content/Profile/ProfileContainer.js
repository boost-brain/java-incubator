import React from 'react';
import {compose} from "redux";
import {withAuthRedirect} from "../../../HOC/withAuthRedirect";
import {connect} from "react-redux";
import Preloader from "../../common/preloader/Preloader";
import ShowProfile from "./ShowProfile";
import {setToggleFetching, setUserFromServer, updateUserThunkCreator} from "../../../redux/profile-reducer";

class ProfileContainer extends React.Component {
    componentDidMount() {
        this.props.setUserFromServer()
    }

    render = () => {
        return (
            <>
                {this.props.isFetching ? <Preloader/> : null}
                <ShowProfile userFromServer={this.props.userFromServer}
                             updateUserThunkCreator={this.props.updateUserThunkCreator}/>
            </>
        )
    }
}

const
    mapStateToProps = (state) => {
        return {
            isFetching: state.profile.isFetching,
            userFromServer: state.profile.userFromServer,
            userEmail: state.login.userEmail,
        }
    };

const
    mapDispatchToProps = {
        setToggleFetching,
        setUserFromServer,
        updateUserThunkCreator

    };
export default compose(
    withAuthRedirect,
    connect(mapStateToProps, mapDispatchToProps)
)(ProfileContainer)
