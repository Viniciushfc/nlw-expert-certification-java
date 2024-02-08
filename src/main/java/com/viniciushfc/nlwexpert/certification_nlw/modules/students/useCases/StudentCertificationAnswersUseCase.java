package com.viniciushfc.nlwexpert.certification_nlw.modules.students.useCases;

import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.entities.QuestionEntity;
import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.dto.StudentCertificationAnswersDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.entities.StudentEntity;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.viniciushfc.nlwexpert.certification_nlw.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCases verifyIfHasCertificationUseCases;

    public CertificationStudentEntity execute(StudentCertificationAnswersDTO dto) throws Exception {

        var hashCertification = verifyIfHasCertificationUseCases.execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if(hashCertification){
            throw new Exception("Você já tirou sua certificação!");
        }
        List<QuestionEntity> questionEntities = this.questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers().stream().forEach(questionAnswer -> {
            var question = questionEntities.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream().filter(alternative -> alternative.isCorrect()).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answerrsCertificationsEntity = AnswersCertificationsEntity.builder().answerID(questionAnswer.getAlternativeID()).questionID(questionAnswer.getQuestionID()).isCorrect(questionAnswer.isCorrect()).build();

            answersCertifications.add(answerrsCertificationsEntity);
        });

        var student = this.studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        }else{
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentID(studentID)
                .grade(correctAnswers.get())
                .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answersCertification -> {
            answersCertification.setCertificationID(certificationStudentEntity.getId());
            answersCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}
