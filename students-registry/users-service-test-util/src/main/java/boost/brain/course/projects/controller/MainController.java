package boost.brain.course.projects.controller;


import boost.brain.course.projects.controller.util.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ForkJoinPool;


@Log
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    public MainController() {
        restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(10000);

        restTemplate.setRequestFactory(requestFactory);
    }

    public void run() {

        while (true) {
            log.info("Input command line");
            log.info("For exit input -ext");
            File file;
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                String line = bf.readLine();

                if (line.equals("-ext")) {
                    break;
                }

                doWorkByCommandLine(line);
            } catch (IOException e) {
                log.severe(e.getLocalizedMessage());
                log.severe("Something go wrong! repeat");
            }


        }

    }

    private void doWorkByCommandLine(String line) {

        String url = "";
        String sOperation = "";
        String sCount = "";
        Long count;

        String[] args = line.split(" ");

        for (String arg : args) {
            if (arg.contains(Constants.URL_ARG)) {
                url = arg.replace(Constants.URL_ARG, "").replace("=", "").trim();
            }

            if (arg.contains(Constants.OPERATION)) {
                sOperation = arg.replace(Constants.OPERATION, "").replace("=", "").trim();
            }

            if (arg.contains(Constants.COUNT)) {
                sCount = arg.replace(Constants.COUNT, "").replace("=", "").trim();
            }
        }

        url = getUrl(url);
        count = getCount(sCount);
        InputOperations operation = getOperation(sOperation);

        switch (operation) {
            case CREATE: {
                executeCreate(url, count);
                break;
            }
            case DELETE: {
                executeDelete(url, count);
                break;
            }
            case PAGE: {
                executePage(url, count);
                break;
            }
            case READ: {
                executeRead(url, count);
                break;
            }
            case UPDATE: {
                executeUpdate(url, count);
                break;
            }

            case USERS_FOR_EMAILS: {
                executeUsersForEmails(url, count);
                break;
            }

            case USERS_ALL: {
                executeUsersAll(url, count);
                break;
            }

            case CHECK_IF_EXISTS: {
                executeCheckIfExists(url, count);
                break;
            }

            case UPDATE_STATUS: {
                executeUpdateStatus(url, count);
                break;
            }

            case UPDATE_STATUSES_FOR_EMAILS: {
                executeUpdateStatusesForEmails(url, count);
                break;
            }

            default:
                executeCount(url, count);
        }
    }

    private void executeUpdate(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new UpdateSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Update all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeUpdateStatus(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new UpdateStatusSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("UpdateStatus all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeUpdateStatusesForEmails(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new UpdateStatusesForEmailsSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("UpdateStatus all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeUsersForEmails(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new UsersForEmailsSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("UsersForEmail all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeUsersAll(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new UsersAllSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("UsersAll for " + count + " times  = " + elapsedTime + "ms");
    }

    private void executeCheckIfExists(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new CheckIfExistsSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("CheckIfExists all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeRead(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new ReadSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Read all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }


    private void executeDelete(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new DeleteSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Delete all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executePage(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new PageSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Page all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeCount(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new CountSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Count all for " + count + " times  = " + elapsedTime + "ms");
    }

    private void executeCreate(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(new CreateSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
            Thread.yield();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Create all for " + count + " times  = " + elapsedTime + "ms");
    }


    private String getUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "localhost:8082";
        }
        return url;
    }

    private Long getCount(String sCount) {
        long count;
        if (!sCount.isEmpty()) {
            try {
                count = Long.parseLong(sCount);
            } catch (NumberFormatException e) {
                log.info("-c:" + e.getMessage());
                count = 1000L;
            }
        } else {
            count = 1000L;
        }
        return count;
    }

    private InputOperations getOperation(String operation) {
        switch (operation.toLowerCase()) {
            case Constants.CREATE_PREFIX:
                return InputOperations.CREATE;
            case Constants.DELETE_PREFIX:
                return InputOperations.DELETE;
            case Constants.PAGE_PREFIX:
                return InputOperations.PAGE;
            case Constants.READ_PREFIX:
                return InputOperations.READ;
            case Constants.UPDATE_PREFIX:
                return InputOperations.UPDATE;
            case Constants.USERS_FOR_EMAILS_PREFIX:
                return InputOperations.USERS_FOR_EMAILS;
            case Constants.USERS_ALL_PREFIX:
                return InputOperations.USERS_ALL;
            case Constants.CHECK_IF_EXISTS_PREFIX:
                return InputOperations.CHECK_IF_EXISTS;
            case Constants.UPDATE_STATUS_PREFIX:
                return InputOperations.UPDATE_STATUS;
            case Constants.UPDATE_STATUSES_FOR_EMAILS_PREFIX:
                return InputOperations.UPDATE_STATUSES_FOR_EMAILS;
            default:
                return InputOperations.COUNT;
        }
    }
}
