package com.boostbrain.services;

import com.boostbrain.dao.MainRepository;
import com.boostbrain.logic.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {
    @Autowired
    private MainRepository mainRepository;

    public void createStudents(ArrayList<Student> students) {
        students.stream().forEach((student -> {
            mainRepository.createStudent(student);
                })
        );
    }
}
