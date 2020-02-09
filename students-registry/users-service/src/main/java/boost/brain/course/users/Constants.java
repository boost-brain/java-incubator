package boost.brain.course.users;

public interface Constants {
    String API_PREFIX = "/api";

    String USERS_CONTROLLER_PREFIX = API_PREFIX + "/users";

    String CREATE_PREFIX = "/create";
    String READ_PREFIX = "/read";
    String UPDATE_PREFIX = "/update";
    String PUT_PREFIX = "/put";
    String DELETE_PREFIX = "/delete";

    String COUNT_PREFIX = "/count";
    String PAGE_PREFIX = "/page";
    String FILTER_PREFIX = "/filter";

    String USERS_FOR_EMAILS_PREFIX = "/users-for-emails";
    String USERS_ALL_PREFIX = "/users-all";
    String CHECK_IF_EXISTS_PREFIX = "/check-if-exists";
    String UPDATE_STATUS_PREFIX = "/update-status";
    String UPDATE_STATUSES_FOR_EMAILS_PREFIX = "/update-statuses-for-emails";
}
