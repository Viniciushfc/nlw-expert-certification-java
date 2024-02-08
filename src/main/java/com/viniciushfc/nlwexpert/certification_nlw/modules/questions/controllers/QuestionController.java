package com.viniciushfc.nlwexpert.certification_nlw.modules.questions.controllers;

import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.entities.QuestionEntity;
import com.viniciushfc.nlwexpert.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.questionRepository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionToDTO(question)).collect(Collectors.toList());

        return toMap;
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDTO = QuestionResultDTO.builder().id(question.getId()).technology(question.getTechnology()).description(question.getDescription()).build();

        List<AlternativesResultDTO> alternativesResultDTOs = question.getAlternatives().stream().map(alternative -> mapAlternativesDTO(alternative)).collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativesResultDTOs);
        return questionResultDTO;
    }

    static AlternativesResultDTO mapAlternativesDTO(AlternativesEntity alternativesEntities) {
        return AlternativesResultDTO.builder().id(alternativesEntities.getId()).description(alternativesEntities.getDescription()).build();
    }
}
