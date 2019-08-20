package com.boostbrain.dao;

import com.boostbrain.Main;
import com.boostbrain.entities.MainEntity;
import com.boostbrain.logic.Student;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingDeque;

@Repository
@Transactional
public class MainRepository {
    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public MainRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    /*
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
     */
    public Student create(Student student) {
        if (student == null) {
            return null;
        }

        MainEntity mainEntity = new MainEntity();
        BeanUtils.copyProperties(student, mainEntity, "id");
        entityManager.persist(student);

        Student result = new Student();
        BeanUtils.copyProperties(mainEntity, result);

        return result;
    }

    public boolean update(Student student) {
        return false;
    }

    public boolean delete(long id) {
        MainEntity mainEntity = entityManager.find(MainEntity.class, id);
        if (mainEntity == null) {
            return false;
        }
        entityManager.remove(mainEntity);
        return true;
    }

    public Student read(long id) {
        MainEntity mainEntity = entityManager.find(MainEntity.class, id);
        if (mainEntity == null) {
            return null;
        }

        Student result = new Student();
        BeanUtils.copyProperties(mainEntity, result);

        return result;
    }
}
