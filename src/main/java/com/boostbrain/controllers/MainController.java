package com.boostbrain.controllers;

import com.boostbrain.dao.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    private final MainRepository mainRepository;

    @Autowired
    public MainController(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    @RequestMapping(path = "/createStudent",
            method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sayHello(
                    String name,
                    String email,
                    String hours) {

        return new ResponseEntity<>("{" +
                "studentName:" + name +
                "email" + email +
                "hours" + hours +
                "}", HttpStatus.OK);
    }


}
