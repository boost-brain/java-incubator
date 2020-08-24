package boost.brain.course;


import boost.brain.course.users.test.controller.MainController;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Application work's with command line.
 * Can execute commands:
 * -url - full url with http://
 * -o - operations what can be done
 *     count - return count of rows in table
 *     create - send generated JSON to add a new record
 *     delete - get 20 records from random page and delete them
 *     page - get 20 records from random page
 *     read - get 20 records from random page and read records
 *     update - get 20 records from random page and update record with new generated data
 *
 *     default command = count
 *
 * -c - count of operations what will be execute
 *      default = 1000
 *
 * -ext - exit application
 *
 * example
 * -url=http://localhost:8082 -o=create -c=100
 * -url=http://localhost:8082 -o=read -c=10
 * -url=http://localhost:8082 -o=delete -c=15
 * -url=http://localhost:8082 -o=update -c=4
 * -url=http://localhost:8082 -o=page -c=100
 * -url=http://localhost:8082 -o=users-for-emails -c=10
 * -url=http://localhost:8082 -o=users-all -c=1
 * -url=http://localhost:8082 -o=check-if-exists -c=1
 * -url=http://localhost:8082 -o=update-status -c=1
 * -url=http://localhost:8082 -o=update-statuses-for-emails -c=1
 *
*/
@Log
@SpringBootApplication
public class UsersServiceTestUtilApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceTestUtilApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        MainController mainController = new MainController();
        mainController.run();
        SpringApplication.exit(context);
    }
}

