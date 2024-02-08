package com.viniciushfc.nlwexpert.certification_nlw.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCertificationAnswersDTO {

    private String email;
    private String technology;
    private List<QuestionAnswerDTO> questionAnswers;
}
