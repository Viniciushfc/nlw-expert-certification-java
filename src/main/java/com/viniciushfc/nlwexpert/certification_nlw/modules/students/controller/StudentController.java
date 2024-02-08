package com.viniciushfc.nlwexpert.certification_nlw.modules.students.controller;

import com.viniciushfc.nlwexpert.certification_nlw.modules.students.dto.StudentCertificationAnswersDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCases verifyIfHasCertificationUseCases;

    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO dto) {

        var result = this.verifyIfHasCertificationUseCases.execute(dto);
        if (result) {
            return "Usuário já fez a prova";
        }
        return "Usuário pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswers(@RequestBody StudentCertificationAnswersDTO dto) {
        try{
            var result = this.studentCertificationAnswersUseCase.execute(dto);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
