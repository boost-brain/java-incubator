package boost.brain.course.projects.model;

import javax.persistence.*;

@Entity
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    @Column
    private String projectUrl;
    @Column
    private String description;
    @Column
    private String projectName;

    public int getProjectId(){
        return projectId;
    }

    public  String getProjectUrl(){
        return projectUrl;
    }

    public String getDescription(){
        return description;
    }
    public String getProjectName(){
        return projectName;
    }

    public void setProjectId(int projectId){
        this.projectId=projectId;
    }
    public  void setProjectUrl(String projectUrl){
        this.projectUrl=projectUrl;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setProjectName(String projectName){
        this.projectName=projectName;
    }

    public Project(String projectName, String projectUrl, String description){
        this.projectName=projectName;
        this.description=description;
        this.projectUrl=projectUrl;
    }

    public Project(){

    }

    public String toString(){
        return getClass() + ":[ id:" + getProjectId()+ " name:"+ getProjectName() + " desc:" +getDescription()
                + " URL: "+getProjectUrl()+" ]";
    }

    @Override
    public boolean equals(Object otherObject){
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Project other = (Project)otherObject;
        return (getDescription()==other.getDescription() && getProjectName()==other.getProjectName()
        &&getProjectId()==other.getProjectId()&&getProjectUrl()==other.getProjectUrl());
    }

    @Override
    public int hashCode(){
        return 7*getProjectUrl().hashCode()+11*getProjectId()+13*getProjectName().hashCode()+17*getDescription().hashCode();
    }

}
