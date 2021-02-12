package boost.brain.course.common.auth;

public enum UserRole {

    LOCKED("Заблокированный"),
    UNACTIVATED("Неактивированный"),
    STUDENT("Студент"),
    MENTOR("Наставник"),
    MODERATOR("Моденатор"),
    ADMINISTRATOR("Администратор");

    String value;

    UserRole(String value) {
        this.value = value;
    }
}
