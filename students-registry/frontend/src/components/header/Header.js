import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import logo from '../../assets/img/bblogo.jpg';
import css from './header.module.css';
import {Link} from "react-router-dom";


const Header = (props) => {
    return (<div>
            <Container fluid>
                <Row>
                    <Col xs={1}>
                        <img src={logo} alt="Logo" className={css.logoImage}/>
                    </Col>
                    <Col>
                        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                            <span className="navbar-brand">
                                <Link to="/" className={css.linkTo}><h1>BoostBrain Java Incubator</h1></Link>
                            </span>
                            <button className="navbar-toggler" type="button" data-toggle="collapse"
                                    data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false"
                                    aria-label="Toggle navigation">
                                <span className="navbar-toggler-icon"></span>
                            </button>

                            <div className="collapse navbar-collapse" id="navbarColor02">
                                <ul className="navbar-nav mr-auto">
                                    <li className="nav-item active">
                                        <a className="nav-link">
                                            <Link to="/users" className={css.linkTo}>
                                                Students
                                            </Link>
                                            <span className="sr-only">(current)</span></a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link">
                                            <Link to="/projects" className={css.linkTo}>
                                                Projects
                                            </Link>
                                        </a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link">
                                            <Link to="/tasks" className={css.linkTo}>
                                                Tasks
                                            </Link>
                                        </a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link" href="#">Messages</a>
                                    </li>

                                </ul>

                            </div>
                        </nav>
                    </Col>
                    <Col>
                        <div className={css.loginBlock}>
                            {props.isAuthenticated ?
                                props.userEmail :
                                <Link to="/loginPage" className="bg-dark navbar-dark border">
                                    <h1> Login</h1>
                                </Link>}
                        </div>
                    </Col>
                </Row>
            </Container>
        </div>
    )
};

export default Header;
