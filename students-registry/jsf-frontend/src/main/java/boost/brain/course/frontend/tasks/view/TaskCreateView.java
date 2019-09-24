package boost.brain.course.frontend.tasks.view;

import boost.brain.course.common.auth.UserDto;
import boost.brain.course.frontend.auth.bean.HttpSessionBean;
import boost.brain.course.frontend.tasks.model.Project;
import boost.brain.course.frontend.tasks.model.Task;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScope
@Data
@Log
public class TaskCreateView {
    final private HttpSessionBean httpSessionBean;
    private List<Task> tasks;
    @Value("${tasks-service-tasks-url}")
    private String tasksUrl;
    @Value("${users-service-url}")
    private String usersUrl;
    @Value("${projects-service-url}")
    private String projectsUrl;
    private RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;

    private String taskName;
    private String taskImplementer;
    private String taskProject;
    private String taskText;
    private String taskAuthor;
    private String message;

    private List<UserDto> users;
    private List<Project> projects;

    @Inject
    public TaskCreateView(HttpSessionBean httpSessionBean) {
        this.httpSessionBean = httpSessionBean;
    }

    @PostConstruct
    public void init() {
        log.info("Init TaskCreateView");
        restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        restTemplate = restTemplateBuilder.build();
        if (!StringUtils.isEmpty(httpSessionBean.getLogin())) taskAuthor = httpSessionBean.getLogin();
        message = "Необходимо заполнить все поля...";
        users = this.getListUsers();
        projects = this.getListProjects();

    }

    public void createButton() {
        log.info("Do TaskCreateView.createButton");
        if (this.createNewTask()) {
            redirect("/secured/tasks/for-user");
        } else {
            message = "Ошибка! Проверьте корректность заполнения полей!";
            log.severe("Error: " + message);
        }
    }

    public List<String> completeImplementer(String query) {
        log.info("Do TaskCreateView.completeImplementer");
        List<String> filteredUsers = new ArrayList<>();
        if (users == null || users.isEmpty()) return filteredUsers;
        for (UserDto user : users) {
            if (user.getGitHabId().toLowerCase().contains(query)) {
                filteredUsers.add(user.getEmail());
            }
        }
        return filteredUsers;
    }

    public List<String> completeProject(String query) {
        log.info("Do TaskCreateView.completeProject");
        List<String> filteredProjects = new ArrayList<>();
        if (projects == null || projects.isEmpty()) return filteredProjects;
        for (Project project : projects) {
            if (project.getProjectName().toLowerCase().contains(query)) {
                filteredProjects.add(String.valueOf(project.getProjectId()));
            }
        }
        return filteredProjects;
    }

    public String emailToGitHubId(final String email) {
        for (UserDto user : users) {
            if (email.equals(user.getEmail())) {
                return user.getGitHabId();
            }
        }
        return email;
    }

    public String projectIdToName(final String id) {
        for (Project project : projects) {
            if (String.valueOf(project.getProjectId()).equals(id)) {
                return project.getProjectName();
            }
        }
        return id;
    }

    private List<UserDto> getListUsers() {
        log.info("Do TaskCreateView.getListUsers");
        List<UserDto> result = new ArrayList<>();
        try {
            ResponseEntity<List<UserDto>> response = restTemplate.exchange(
                    usersUrl + "/users-all",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDto>>() {
                    });
            List<UserDto> responseList = response.getBody();
            if (responseList != null && !responseList.isEmpty()) {
                result.addAll(responseList);
            }
        } catch (Exception e) {
            log.severe("TaskCreateView.getListUsers throws exception!");
            e.printStackTrace();
        }
        return result;
    }

    private List<Project> getListProjects() {
        log.info("Do TaskCreateView.getListProjects");
        List<Project> result = new ArrayList<>();
        try {
            ResponseEntity<List<Project>> response = restTemplate.exchange(
                    projectsUrl + "/projects-all",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Project>>() {
                    });
            List<Project> responseList = response.getBody();
            if (responseList != null && !responseList.isEmpty()) {
                result.addAll(responseList);
            }
        } catch (Exception e) {
            log.severe("TaskCreateView.getListProjects throws exception!");
            e.printStackTrace();
        }
        return result;
    }

    private boolean createNewTask() {
        log.info("Do TaskCreateView.createNewTask");
        if (StringUtils.isEmpty(taskName) ||
                StringUtils.isEmpty(taskText) ||
                !this.checkImplementer(taskImplementer) ||
                !this.checkAuthor(taskAuthor) ||
                !this.checkProject(taskProject)) {
            return false;
        }

        try {
            Task newTask = new Task();
            newTask.setAuthor(httpSessionBean.getLogin());
            newTask.setImplementer(taskImplementer);
            newTask.setName(taskName);
            newTask.setProject(Integer.valueOf(taskProject));
            newTask.setText(taskText);

            HttpEntity<Task> request = new HttpEntity<>(newTask, this.getHeaders());
            Task taskCreated = restTemplate.postForObject(tasksUrl + "/create", request, Task.class);
            if (taskCreated != null) return true;
        } catch (Exception e) {
            log.severe("TaskCreateView.createNewTask throws exception!");
            e.printStackTrace();
        }
        return false;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (httpSessionBean.getSession() != null)
            headers.add("sessionId", httpSessionBean.getSession().getSessionId());
        return headers;
    }

    private void redirect(final String path) {
        log.info("Do TaskCreateView.redirect");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (ec != null) {
            try {
                if (!org.springframework.util.StringUtils.isEmpty(path)) {
                    ec.redirect(path);
                } else {
                    ec.redirect("/");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkAuthor(String taskAuthor) {
        log.info("Do TaskCreateView.checkAuthor");
        return !StringUtils.isEmpty(taskAuthor) && this.checkUser(taskAuthor);
    }

    private boolean checkImplementer(String taskImplementer) {
        log.info("Do TaskCreateView.checkImplementer");
        return !StringUtils.isEmpty(taskImplementer) && this.checkUser(taskImplementer);
    }

    private boolean checkProject(String projectId) {
        log.info("Do TaskCreateView.checkProject");
        if (StringUtils.isEmpty(projectId)) return false;
        try {
            RestTemplate restTemplate = restTemplateBuilder.build();
            Boolean checkIfExists = restTemplate.getForObject(projectsUrl + "/check-if-exists/" + projectId, Boolean.class);
            if (checkIfExists != null && checkIfExists) return true;
        } catch (Exception e) {
            log.severe("TaskCreateView.checkProject throws exception!");
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkUser(String email) {
        log.info("Do TaskCreateView.checkUser");
        if (StringUtils.isEmpty(email)) return false;
        try {
            RestTemplate restTemplate = restTemplateBuilder.build();
            Boolean checkIfExists = restTemplate.getForObject(usersUrl + "/check-if-exists/" + email, Boolean.class);
            if (checkIfExists != null && checkIfExists) return true;
        } catch (Exception e) {
            log.severe("TaskCreateView.checkUser throws exception!");
            e.printStackTrace();
        }
        return false;
    }
}
