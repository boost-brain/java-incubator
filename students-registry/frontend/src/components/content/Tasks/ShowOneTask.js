import React from 'react';
import css from "./task.module.css";

export class ShowOneTask extends React.Component {
    state = {
        textEditMode: false,
        nameEditMode: false,
        projectEditMode: false,
        authorEditMode: false,
        implementerEditMode: false,
        id: this.props.task.id,
        author: this.props.task.author,
        implementer: this.props.task.implementer,
        project: this.props.task.project,
        name: this.props.task.name,
        text: this.props.task.text,
        createDate: this.props.task.createDate,
        updateDate: this.props.task.updateDate,
    };
    activateTextEditMode = () => {
        this.setState({textEditMode: true})
    };
    deActivateTextEditMode = () => {
        this.setState({textEditMode: false});
        this.doUpdate();
    };
    activateNameEditMode = () => {
        this.setState({nameEditMode: true})
    };
    deActivateNameEditMode = () => {
        this.setState({nameEditMode: false});
        this.doUpdate();
    };
    activateProjectEditMode = () => {
        this.setState({projectEditMode: true})
    };
    deActivateProjectEditMode = () => {
        this.setState({projectEditMode: false});
        this.doUpdate();
    };
    activateAuthorEditMode = () => {
        this.setState({authorEditMode: true})
    };
    deActivateAuthorEditMode = () => {
        this.setState({authorEditMode: false});
        this.doUpdate();
    };

    activateImplementerEditMode = () => {
        this.setState({implementerEditMode: true})
    };
    deActivateImplementerEditMode = () => {
        this.setState({implementerEditMode: false});
        this.doUpdate();
    };
    doUpdate = () => {
        this.props.updateTaskThunkCreator({
            id: this.state.id,
            author: this.state.author,
            name: this.state.name,
            text: this.state.text,
            implementer: this.state.implementer,
            project: this.state.project,
        })
    };

    doDeleteTask = () => {
        this.props.deleteTaskThunkCreator(this.state.id);
    };

    nameOnChange = (e) => {
        this.setState({name: e.currentTarget.value});
    };
    authorOnChange = (e) => {
        this.setState({author: e.currentTarget.value});
    };
    textOnChange = (e) => {
        this.setState({text: e.currentTarget.value});
    };
    implementerOnChange = (e) => {
        this.setState({implementer: e.currentTarget.value});
    };
    projectOnChange = (e) => {
        this.setState({project: e.currentTarget.value});
    };


    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.task.name !== this.props.task.name) {
            this.setState({name: this.props.task.name})
        }
        if (prevProps.task.author !== this.props.task.author) {
            this.setState({author: this.props.task.author})
        }
        if (prevProps.task.text !== this.props.task.text) {
            this.setState({text: this.props.task.text})
        }
        if (prevProps.task.implementer !== this.props.task.implementer) {
            this.setState({implementer: this.props.task.implementer})
        }
        if (prevProps.task.project !== this.props.task.project) {
            this.setState({project: this.props.task.project})
        }
    }

    render() {
        return <div>

            <h4 className={css.name}>
                {!this.state.nameEditMode &&
                <span onClick={this.activateNameEditMode}>Name: {this.state.name} </span>
                }

                {this.state.nameEditMode &&
                <input autoFocus={true} onBlur={this.deActivateNameEditMode}
                       onChange={this.nameOnChange} value={this.state.name}/>
                }
            </h4>
            <container className={css.container}>
                <span className={css.id}>ID: {this.props.task.id}      </span>

                <span className={css.text}>
                {!this.state.textEditMode &&
                <span onClick={this.activateTextEditMode}>Text: {this.state.text} </span>
                }

                    {this.state.textEditMode &&
                    <input className={css.textInput} autoFocus={true} onBlur={this.deActivateTextEditMode}
                           onChange={this.textOnChange} value={this.state.text}
                    />
                    }
                </span>

                <span className={css.project}>
                {!this.state.projectEditMode &&
                <span onClick={this.activateProjectEditMode}>Project: {this.state.project} </span>
                }

                    {this.state.projectEditMode &&
                    <input className={css.projectInput} autoFocus={true} onBlur={this.deActivateProjectEditMode}
                           onChange={this.projectOnChange} value={this.state.project}/>
                    }
                </span>

                <span className={css.author}>
                {!this.state.authorEditMode &&
                <span onClick={this.activateAuthorEditMode}>Author: {this.state.author} </span>
                }

                    {this.state.authorEditMode &&
                    <input className={css.authorInput} autoFocus={true} onBlur={this.deActivateAuthorEditMode}
                           onChange={this.authorOnChange} value={this.state.author}/>
                    }
                </span>

                <span className={css.implementer}>
                {!this.state.implementerEditMode &&
                <span onClick={this.activateImplementerEditMode}>Implementer: {this.state.implementer} </span>
                }

                    {this.state.implementerEditMode &&
                    <input className={css.implementerInput} autoFocus={true} onBlur={this.deActivateImplementerEditMode}
                           onChange={this.implementerOnChange} value={this.state.implementer}/>
                    }
                </span>

                <span className={css.delete} onClick={this.doDeleteTask}>
                    Delete
                </span>
            </container>

            <hr/>
        </div>
    }
}


