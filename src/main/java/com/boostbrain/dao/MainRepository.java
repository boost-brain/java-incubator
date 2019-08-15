package com.boostbrain.dao;

import com.boostbrain.logic.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class MainRepository {
    @Autowired
    private DataSource dataSource;

    public void createStudent(Student student) {
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE students IF NOT EXISTS ({" +
                    "studentName VARCHAR(30) NOT NULL" +
                    "email VARCHAR(30) NOT NULL" +
                    "hours VARCHAR(10) " +
                    "})");
            statement.execute("INSERT INTO students" +
                    "(studentName, email, hours)" +
                    "VALUES(" +
                    student.getName() + ", " +
                    student.getEmail()+ ", " +
                    student.getHours() + ")");
            //Навероне надо что-то еще добавить
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
