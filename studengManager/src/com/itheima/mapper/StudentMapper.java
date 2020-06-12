package com.itheima.mapper;

import com.itheima.domain.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentMapper {
    @Select("select * from student")
    List<Student> findAll();

    @Insert("insert into student values(#{number},#{name},#{birthday},#{address})")
    void addStu(Student student);

    @Update("update student set name = #{name}, birthday = #{birthday}, address = #{address} where number = #{number}")
    void updateStu(Student student);

    @Delete("delete from student where number = #{number}")
    void deleteStu(String number);
}
