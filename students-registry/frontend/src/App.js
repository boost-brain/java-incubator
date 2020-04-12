import React from 'react';
import './App.css';
import {Col, Container, Row} from "react-bootstrap";
import Content from "./components/content/Content";
import css from './app.module.css';
import HeaderContainer from "./components/header/HeaderContainer";
import {connect} from "react-redux";
import {initializeApp} from "./redux/app-reducer";
import Preloader from "./components/common/preloader/Preloader";

class App extends React.Component {
    componentDidMount() {
        this.props.initializeApp();
    }

    render() {
        if (!this.props.isInitialized) {
            return (<div>
                <Preloader/>
            </div>)
        }
        return (
            <div className={css.container}>
                <Container fluid={true}>
                    <Row>
                        <Col>
                            <HeaderContainer/>
                        </Col>
                    </Row>
                    <Content/>
                </Container>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        isInitialized: state.app.isInitialized
    }
};
let mpDispatchToProps = {
    initializeApp
};

export default connect(mapStateToProps, mpDispatchToProps)(App);

