import React from 'react';
import css from "./projects.module.css";

export class ShowOneProject extends React.Component {
    state = {
        descriptionEditMode: false,
        nameEditMode: false,
        urlEditMode: false,
        projectId: this.props.project.projectId,
        projectName: this.props.project.projectName,
        projectUrl: this.props.project.projectUrl,
        description: this.props.project.description
    };
    activateDescriptionEditMode = () => {
        this.setState({descriptionEditMode: true})
    };
    deActivateDescriptionEditMode = () => {
        this.setState({descriptionEditMode: false})
        this.doUpdate();
    };
    activateNameEditMode = () => {
        this.setState({nameEditMode: true})
    };
    deActivateNameEditMode = () => {
        this.setState({nameEditMode: false})
        this.doUpdate();
    };
    activateUrlEditMode = () => {
        this.setState({urlEditMode: true})
    };
    deActivateUrlEditMode = () => {
        this.setState({urlEditMode: false})
        this.doUpdate();
    };
    doUpdate = () => {
        this.props.updateProjectThunkCreator({
            projectId: this.state.projectId,
            projectName: this.state.projectName,
            projectUrl: this.state.projectUrl,
            description: this.state.description
        })
    };

    doDeleteProject=()=>{
        this.props.deleteProjectThunkCreator(this.state.projectId);
    };

    nameOnChange = (e) => {
        this.setState({projectName: e.currentTarget.value});
    };
    urlOnChange = (e) => {
        this.setState({projectUrl: e.currentTarget.value});
    };
    descriptionOnChange = (e) => {
        this.setState({description: e.currentTarget.value});
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.project.projectName !== this.props.project.projectName) {
            this.setState({projectName: this.props.project.projectName})
        }
        if (prevProps.project.projectUrl !== this.props.project.projectUrl) {
            this.setState({projectUrl: this.props.project.projectUrl})
        }
        if (prevProps.project.description !== this.props.project.description) {
            this.setState({description: this.props.project.description})
        }


    }

    render() {
        return <div>

            <h4 className={css.name}>
                {!this.state.nameEditMode &&
                <span onClick={this.activateNameEditMode}>Name: {this.state.projectName} </span>
                }

                {this.state.nameEditMode &&
                <input autoFocus={true} onBlur={this.deActivateNameEditMode}
                       onChange={this.nameOnChange} value={this.state.projectName}/>
                }
            </h4>
            <container className={css.container}>
                <span className={css.id}>ID: {this.props.project.projectId}      </span>

                <span className={css.description}>
                {!this.state.descriptionEditMode &&
                <span onClick={this.activateDescriptionEditMode}>Description: {this.state.description} </span>
                }

                    {this.state.descriptionEditMode &&
                    <input className={css.descriptionInput} autoFocus={true} onBlur={this.deActivateDescriptionEditMode}
                           onChange={this.descriptionOnChange} value={this.state.description}
                    />
                    }
                </span>

                <span className={css.url}>
                {!this.state.urlEditMode &&
                <span onClick={this.activateUrlEditMode}>URL: {this.state.projectUrl} </span>
                }

                    {this.state.urlEditMode &&
                    <input className={css.urlInput} autoFocus={true} onBlur={this.deActivateUrlEditMode}
                           onChange={this.urlOnChange} value={this.state.projectUrl}/>
                    }
                </span>

                <span className={css.delete} onClick={this.doDeleteProject}>
                    Delete
                </span>
            </container>

            <hr/>
        </div>
    }
}


