package boost.brain.course;


public class TestUtil {
    public static void main(String[] args) {
        String credentialsUrl = "http://localhost:8081/api/credentials";
        String loginUrl = "http://localhost:8081/api/login";
        String testAdminLogin = "test2@mail.ru";
        String testAdminPwd = "2";

        new TestAuthService(credentialsUrl, loginUrl, testAdminLogin, testAdminPwd)
                .test(10);
    }


}
