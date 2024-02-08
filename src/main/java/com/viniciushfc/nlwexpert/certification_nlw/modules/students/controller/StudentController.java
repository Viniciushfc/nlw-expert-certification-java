package com.viniciushfc.nlwexpert.certification_nlw.modules.students.controller;

import com.viniciushfc.nlwexpert.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCases verifyIfHasCertificationUseCases;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO dto) {

        var result = this.verifyIfHasCertificationUseCases.execute(dto);
        if (result){
            return "Usuário já fez a prova";
        }
        return "Usuário pode fazer a prova";
    }
}
