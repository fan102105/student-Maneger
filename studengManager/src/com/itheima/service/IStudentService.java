package com.itheima.service;

import com.github.pagehelper.Page;
import com.itheima.domain.Student;

import java.io.IOException;
import java.util.List;

public interface IStudentService {
    List<Student> findAll() throws IOException;

    Page<Object> selectByPage(int currentPage, int pageSize);

    void addStu(Student student);

    void editStu(Student student);

    void deleteStu(String number);
}
