package com.boostbrain.controllers;

import com.boostbrain.dao.MainRepository;
import com.boostbrain.exceptions.NotFoundException;
import com.boostbrain.logic.Constants;
import com.boostbrain.logic.Student;
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

    @RequestMapping(path = "/createStudentGET",
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

    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Student create(@RequestBody Student student) {
        Student result = mainRepository.create(student);


        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Student read(@PathVariable long id) throws NotFoundException {
        Student result = mainRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String update(@RequestBody Student student) throws NotFoundException {
        if (mainRepository.update(student)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }


    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String delete(@PathVariable long id) throws NotFoundException {
        if (mainRepository.delete(id)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }

}
