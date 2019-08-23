package boost.brain.course.example.controller;

import boost.brain.course.example.Constants;
import boost.brain.course.example.controller.dto.ExampleDto;
import boost.brain.course.example.controller.exceptions.NotFoundException;
import boost.brain.course.example.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(Constants.EXAMPLE_CONTROLLER_PREFIX)
public class ExampleController {

    private final ExampleRepository exampleRepository;

    @Autowired
    public ExampleController(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ExampleDto create(@RequestBody ExampleDto exampleDto) {
        ExampleDto result = exampleRepository.create(exampleDto);


        return result;
    }

    @GetMapping(path = Constants.READ_PREFIX + "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ExampleDto read(@PathVariable long id) {
        ExampleDto result = exampleRepository.read(id);
        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String update(@RequestBody ExampleDto exampleDto) {
        if (exampleRepository.update(exampleDto)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }


    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String delete(@PathVariable long id) {
        if (exampleRepository.delete(id)) {
            return HttpStatus.OK.getReasonPhrase();
        } else {
            throw new NotFoundException();
        }
    }
}
