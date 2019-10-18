package com.lambdaschool.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Need to add custom swagger documentation in models
@ApiModel(value = "Instructor", description = "The Instructor Entity")

@Entity
@Table(name = "instructor")
public class Instructor
{
    // adding custom swagger documentation for instructid
    @ApiModelProperty(name = "instructid",
            value = "primary key for Instructor",
            required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long instructid;

    @ApiModelProperty(name = "instructname",
            value = "Instructor Name",
            required = true,
            example = "John%20Mitchell")

    private String instructname;

    @OneToMany(mappedBy = "instructor")
    @JsonIgnoreProperties("instructors")
    private List<Course> courses = new ArrayList<>();

    public Instructor()
    {
    }

    public Instructor(String instructname)
    {
        this.instructname = instructname;
    }

    public long getInstructid()
    {
        return instructid;
    }

    public void setInstructid(long instructid)
    {
        this.instructid = instructid;
    }

    public String getInstructname()
    {
        return instructname;
    }

    public void setInstructname(String instructname)
    {
        this.instructname = instructname;
    }

    public List<Course> getCourses()
    {
        return courses;
    }

    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }
}
