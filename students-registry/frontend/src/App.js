import React from 'react';
import './App.css';
import {Col, Container, Row} from "react-bootstrap";
import Content from "./components/content/Content";
import css from './app.module.css';
import HeaderContainer from "./components/header/HeaderContainer";

function App() {
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

export default App;
