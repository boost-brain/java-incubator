package boost.brain.course.common.projects;

public enum ProjectStatus {

    PREPARATION("Подготовка"),
    DEVELOPING("В разработке"),
    SUPPORT("Поддержка"),
    SUSPENDED("Приостановлен"),
    CLOSED("Закрыт");

    String value;

    ProjectStatus(String value) {
        this.value = value;
    }
}
