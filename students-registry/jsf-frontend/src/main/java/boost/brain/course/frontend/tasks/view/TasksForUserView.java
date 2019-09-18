package boost.brain.course.frontend.tasks.view;

import boost.brain.course.frontend.auth.bean.HttpSessionBean;

import boost.brain.course.frontend.tasks.model.Project;
import boost.brain.course.frontend.tasks.model.Task;
import boost.brain.course.frontend.tasks.model.User;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
@RequestScope
@Data
@Log
public class TasksForUserView {

    final private HttpSessionBean httpSessionBean;
    private List<Task> tasks;
    @Value("${tasks-service-tasks-url}")
    private String tasksUrl;
    @Value("${users-service-url}")
    private String usersUrl;
    @Value("${projects-service-url}")
    private String projectsUrl;

    @Inject
    public TasksForUserView(HttpSessionBean httpSessionBean) {
        this.httpSessionBean = httpSessionBean;
    }

    @PostConstruct
    public void init() {
        log.info("Init TasksForUserView");
        tasks = new ArrayList<>();
        tasks.addAll(this.getListTasks());
        this.updateCacheUsers(this.checkCacheUsers(this.getEmails(tasks)));
        this.updateCacheProjects(this.checkCacheProjects(this.getProjectsIds(tasks)));
    }

    public String getUserGitHubId(final String email) {
        if (httpSessionBean.getCacheUsers().containsKey(email)) {
            return httpSessionBean.getCacheUsers().get(email).getGitHabId();
        }
        return email;
    }

    public String getProjectNameForId(final int id) {
        if (httpSessionBean.getCacheProjects().containsKey(id)) {
            return httpSessionBean.getCacheProjects().get(id).getProjectName();
        }
        return String.valueOf(id);
    }

    private List<Task> getListTasks() {
        log.info("Do getListTasks");
        List<Task> result = new ArrayList<>();

        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(this.paramsInPost(), this.getHeaders());

            ResponseEntity<List<Task>> response = restTemplate.exchange(
                    tasksUrl + "/filter?" + this.paramsStr(),
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<Task>>(){});
            result = response.getBody();
        } catch (Exception e) {
            log.severe("TasksForUserView.getListTasks throws exception!");
            e.printStackTrace();
        }
        return result;
    }

    private String paramsStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("implementer").append("=").append(httpSessionBean.getLogin());
        return sb.toString();
    }

    private MultiValueMap<String, String> paramsInPost() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        // example -> map.add("author", author);
        return map;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("sessionId", httpSessionBean.getSession().getSessionId());
        return headers;
    }

    private Set<String> getEmails(List<Task> tasks) {
        Set<String> result = new HashSet<>();
        if (tasks == null || tasks.isEmpty()) return result;
        for (Task task: tasks) {
            result.add(task.getAuthor());
            result.add(task.getImplementer());
        }
        return result;
    }

    private Set<Integer> getProjectsIds(List<Task> tasks) {
        Set<Integer> result = new HashSet<>();
        if (tasks == null || tasks.isEmpty()) return result;
        for (Task task: tasks) {
            result.add(task.getProject());
        }
        return result;
    }

    private Set<String> checkCacheUsers(Set<String> emails) {
        Set<String> result = new HashSet<>();
        if (emails == null || emails.isEmpty()) return result;
        for (String email: emails) {
            if (!httpSessionBean.getCacheUsers().containsKey(email)) {
                result.add(email);
            }
        }
        return result;
    }

    private Set<Integer> checkCacheProjects(Set<Integer> projectsIds) {
        Set<Integer> result = new HashSet<>();
        if (projectsIds == null || projectsIds.isEmpty()) return result;
        for (Integer projectsId: projectsIds) {
            if (!httpSessionBean.getCacheProjects().containsKey(projectsId)) {
                result.add(projectsId);
            }
        }
        return result;
    }

    private void updateCacheUsers(Set<String> emails) {
        log.info("Do updateCacheUsers");
        if (emails == null || emails.isEmpty()) return;
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpEntity<Set<String>> request = new HttpEntity<>(emails);

            ResponseEntity<List<User>> response = restTemplate.exchange(
                    usersUrl + "/users-for-emails",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<User>>(){});
            List<User> users = response.getBody();
            for (User user: users) {
                httpSessionBean.getCacheUsers().put(user.getEmail(),user);
            }
        } catch (Exception e) {
            log.severe("TasksForUserView.updateCacheUsers throws exception!");
            e.printStackTrace();
        }
    }

    private void updateCacheProjects(Set<Integer> projectsIds) {
        log.info("Do updateCacheProjects");
        if (projectsIds == null || projectsIds.isEmpty()) return;
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpEntity<Set<Integer>> request = new HttpEntity<>(projectsIds);

            ResponseEntity<List<Project>> response = restTemplate.exchange(
                    projectsUrl + "/projects-for-ids",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<Project>>(){});
            List<Project> projects = response.getBody();
            for (Project project: projects) {
                httpSessionBean.getCacheProjects().put(project.getProjectId(), project);
            }
        } catch (Exception e) {
            log.severe("TasksForUserView.updateCacheProjects throws exception!");
            e.printStackTrace();
        }
    }
}