package boost.brain.course.messages;

public interface Constants {
    String API_PREFIX = "/api";

    String MESSAGES_CONTROLLER_PREFIX = API_PREFIX + "/messages";

    String CREATE_PREFIX = "/create";
    String READ_PREFIX = "/read";
    String UPDATE_PREFIX = "/update";
    String DELETE_PREFIX = "/delete";

    String COUNT_PREFIX = "/count";
    String ALL_MESSAGES_FOR_USER = "/all-messages-for-user";
    String DELETE_ALL_MESSAGES_FOR_USER = "/delete-all-messages-for-user";
    String MESSAGES_AS_READ = "/message-as-read";
}
