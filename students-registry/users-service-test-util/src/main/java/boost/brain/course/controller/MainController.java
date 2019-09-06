package boost.brain.course.controller;


import boost.brain.course.controller.util.*;
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
        requestFactory.setReadTimeout(1000);

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
                continue;
            }


        }

    }

    private void doWorkByCommandLine(String line) {

        String url = "";
        String sOperation = "";
        String sCount = "";
        Long count = 0L;

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
            default:
                executeCount(url, count);
        }
    }

    private void executeUpdate(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        boolean done = false;
        done = pool.invoke(new UpdateSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Update all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeRead(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        boolean done = false;
        done = pool.invoke(new ReadSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Read all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }


    private void executeDelete(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        boolean done = false;
        done = pool.invoke(new DeleteSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Delete all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executePage(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        boolean done = false;
        done = pool.invoke(new PageSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Page all for " + count + " (20 on page) times  = " + elapsedTime + "ms");
    }

    private void executeCount(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        boolean done = false;
        done = pool.invoke(new CountSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Count all for " + count + " times  = " + elapsedTime + "ms");
    }

    private void executeCreate(String url, Long count) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(4);
        boolean done = false;
        done = pool.invoke(new CreateSpam(url, count, restTemplate));

        while (pool.getActiveThreadCount() != 0) {
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
        Long count;
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
            default:
                return InputOperations.COUNT;
        }
    }
}
