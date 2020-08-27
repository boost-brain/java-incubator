package boost.brain.course.auth;

public interface Constants {
    String API_PREFIX = "/api";

    String CREDENTIALS_CONTROLLER_PREFIX = API_PREFIX + "/credentials";
    String LOGIN_CONTROLLER_PREFIX = API_PREFIX + "/login";

    String CREATE_PREFIX = "/create";
    String READ_PREFIX = "/read";
    String UPDATE_PREFIX = "/update";
    String DELETE_PREFIX = "/delete";

    String LOGIN_PREFIX = "/login";
    String LOGOUT_PREFIX = "/logout";
    String CHECK_PREFIX = "/check";
    String USER_ROLE_PREFIX = "/role";

    long TIME_DELAY_FOR_TEMPORARILY_INACTIVE = 7 * 24 * 60 * 60 * 1000;
    long TIME_DELAY_FOR_ACADEMIC_LEAVE = 14 * 24 * 60 * 60 * 1000;
    long FIXED_DELAY_UPDATE_STATUSES_FOR_USERS = 24 * 60 * 60 * 1000;

    //Prefixes for external services
    String UPDATE_STATUSES_FOR_EMAILS_PREFIX = "/update-statuses-for-emails";
}
