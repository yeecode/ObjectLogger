package com.github.yeecode.objectLogger.ApplicationDemo.business;

import com.github.yeecode.objectLogger.ApplicationDemo.model.Student;
import com.github.yeecode.objectLogger.client.service.LogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestBusiness {
    @Autowired
    private LogClient logClient;

    @RequestMapping(value = "/")
    public String main() {
        Student oldStudent = new Student();
        oldStudent.setId(8);
        oldStudent.setName("Tom");
        oldStudent.setSchoolName("Tomorrow School");
        oldStudent.setGrade(6);
        oldStudent.setAge(10);

        Student newStudent = new Student();
        newStudent.setId(8);
        newStudent.setName("Tom");
        newStudent.setSchoolName("AA School");
        newStudent.setGrade(1);
        newStudent.setAge(11);

        logClient.sendLogForObject(5, "Licy", "doTest", "Test", null, null, oldStudent, newStudent);

        return "success";
    }
}
