package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface CourseService
{
    ArrayList<Course> findAll();

    // This is the paging and sorting
    List<Course> findAllPageable(Pageable pageable);

    ArrayList<CountStudentsInCourses> getCountStudentsInCourse();

    void delete(long id);
}
