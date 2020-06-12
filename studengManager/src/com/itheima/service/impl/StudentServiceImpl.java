package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Student;
import com.itheima.mapper.StudentMapper;
import com.itheima.service.IStudentService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StudentServiceImpl implements IStudentService {
    InputStream is = null;
    SqlSessionFactory  factory = null;
    SqlSession  sqlSession = null;
    StudentMapper studentMapper = null;
    public StudentServiceImpl() throws IOException {
        is = Resources.getResourceAsStream("sqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = factory.openSession();
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    @Override
    public List<Student> findAll() throws IOException {

        List<Student> students = studentMapper.findAll();
        return students;
    }

    @Override
    public Page<Object> selectByPage(int currentPage, int pageSize) {

        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Student> students = studentMapper.findAll();

        return page;
    }

    @Override
    public void addStu(Student student) {
        studentMapper.addStu(student);
        sqlSession.commit();
    }

    @Override
    public void editStu(Student student) {
        studentMapper.updateStu(student);
        sqlSession.commit();
    }

    @Override
    public void deleteStu(String number) {
        studentMapper.deleteStu(number);
        sqlSession.commit();
    }
}
