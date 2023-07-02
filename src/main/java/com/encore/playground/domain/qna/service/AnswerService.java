package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.repository.AnswerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public List<AnswerDto> answerList(Long questionId) {
        return answerRepository.findAnswerByQuestion_Id(questionId).get().stream().map(AnswerDto::new).toList();
    }

    public List<AnswerDto> getAnswerListByMember(String memberId) {
        return answerRepository.findByMemberId(memberId).get().stream().map(AnswerDto::new).toList();
    }

    public List<AnswerDto> createAnswer(AnswerWriteDto answerWriteDto, MemberGetMemberIdDto memberIdDto) {
        QuestionDto questionDto = questionService.readQuestion(answerWriteDto.getQuestionId(), memberIdDto);
        answerRepository.save(AnswerDto.builder()
                .content(answerWriteDto.getContent())
                .createdDate(LocalDateTime.now())
                .question(questionDto.toEntity())
                .build().toEntity());

        return answerList(answerWriteDto.getQuestionId());
    }

    public List<AnswerDto> modifyAnswer(AnswerModifyDto newAnswerDto, MemberGetMemberIdDto memberIdDto) {
        AnswerDto answerDto = new AnswerDto(answerRepository.findById(newAnswerDto.getId()).get());
        answerDto.setContent(newAnswerDto.getContent());
        answerRepository.save(answerDto.toEntity());
        return answerList(newAnswerDto.getQuestionId());
    }

    public List<AnswerDto> deleteAnswer(AnswerDeleteDto answerDeleteDto, MemberGetMemberIdDto memberIdDto) {
        answerRepository.delete(answerRepository.findById(answerDeleteDto.getId()).get());
        return answerList(answerDeleteDto.getQuestionId());
    }

}
