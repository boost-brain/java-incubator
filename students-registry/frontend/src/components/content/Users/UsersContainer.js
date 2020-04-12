import React from 'react';
import {connect} from "react-redux";
import Users from "./Users";
import Preloader from "../../common/preloader/Preloader";
import {
    getUsersThunkCreator,
    setCurrentPage,
    setFirstPage,
    setLastPage,
    setNextPage,
    setPrevPage,
    setToggleFetching,
    setUsers
} from "../../../redux/users-reducer";
import {withAuthRedirect} from "../../../HOC/withAuthRedirect";
import {compose} from "redux";


class UsersContainer extends React.Component {

    updateCurrentUsersPage() {
        this.props.getUsersThunkCreator(this.props.currentPage, this.props.numberForPage);
    }

    componentDidMount() {
        this.updateCurrentUsersPage();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.currentPage !== this.props.currentPage) {
            this.updateCurrentUsersPage();
        }
    }

    render() {
        return (<>
                {console.log(this.props)}
                {this.props.isFetching ? <Preloader/> : null}
                <Users users={this.props.users}
                       totalPages={this.props.totalPages}
                       currentPage={this.props.currentPage}
                       setCurrentPage={this.props.setCurrentPage}
                       isFetching={this.props.isFetching}
                       setFirstPage={this.props.setFirstPage}
                       setLastPage={this.props.setLastPage}
                       setNextPage={this.props.setNextPage}
                       setPrevPage={this.props.setPrevPage}
                />
            </>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        users: state.usersContent.users,
        numberForPage: state.usersContent.numberForPage,
        currentPage: state.usersContent.currentPage,
        totalCount: state.usersContent.totalCount,
        isFetching: state.usersContent.isFetching,
        totalPages: state.usersContent.totalPages,
    }
};

let mpDispatchToProps = {
    setUsers,
    setCurrentPage,
    setToggleFetching,
    getUsersThunkCreator,
    setFirstPage,
    setLastPage,
    setNextPage,
    setPrevPage
};


export default compose(
    withAuthRedirect,
    connect(mapStateToProps, mpDispatchToProps))
(UsersContainer);
