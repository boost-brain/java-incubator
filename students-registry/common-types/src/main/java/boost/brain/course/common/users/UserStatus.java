package boost.brain.course.common.users;

public enum UserStatus {

    WAITING_FOR_A_TASK("Жду задачу"),
    BUSY("Занят"),
    TEMPORARILY_INACTIVE("Временно неактивен"),
    ACADEMIC_LEAVE("Академический отпуск");

    String value;

    UserStatus(String value) {
        this.value = value;
    }
}
