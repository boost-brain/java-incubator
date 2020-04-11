import React from 'react';
import {connect} from "react-redux";
import Users from "./Users";
import Preloader from "../../common/preloader/Preloader";
import {
    getUsersThunkCreator,
    setCurrentPage,
    setFirstPage,
    setLastPage,
    setToggleFetching,
    setUsers
} from "../../../redux/users-reducer";


class UsersContainer extends React.Component {

    componentDidMount() {
        this.props.getUsersThunkCreator(this.props.currentPage, this.props.numberForPage);
    }

    render() {
        return (<>
                {console.log(this.props)}
                {this.props.isFetching ? <Preloader/> : null}
                <Users users={this.props.users}
                       currentPage={this.props.currentPage}
                       setCurrentPage={this.props.setCurrentPage}
                       isFetching={this.props.isFetching}
                       setFirstPage={this.props.setFirstPage}
                       setLastPage={this.props.setLastPage}
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
    }
};

let mpDispatchToProps = {
    setUsers,
    setCurrentPage,
    setToggleFetching,
    getUsersThunkCreator,
    setFirstPage,
    setLastPage,
};


export default connect(mapStateToProps, mpDispatchToProps)(UsersContainer);
