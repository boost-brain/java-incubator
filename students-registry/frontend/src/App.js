import React from 'react';
import './App.css';
import {Col, Container, Row} from "react-bootstrap";
import Content from "./components/content/Content";
import css from './app.module.css';
import HeaderContainer from "./components/header/HeaderContainer";
import {loginIfTrueWithStartAppThunkCreator} from "./redux/login-reducer";

class App extends React.Component {
    componentDidMount() {
        let sessionId = localStorage.getItem('sessionId');
        let userEmail = localStorage.getItem('userEmail');
        if (!!userEmail) {
            if (!!sessionId) {
                loginIfTrueWithStartAppThunkCreator(sessionId, userEmail);
            }
        }
    }

    render() {
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

export default App;
