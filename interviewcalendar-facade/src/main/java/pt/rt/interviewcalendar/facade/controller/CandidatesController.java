package pt.rt.interviewcalendar.facade.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pt.rt.interviewcalendar.api.CandidatesApi;
import pt.rt.interviewcalendar.api.model.InterviewSlot;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.management.service.IInterwiewSlotsManagerService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CandidatesController implements CandidatesApi {

    private final IInterwiewSlotsManagerService interviewSlotsManagerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CandidatesController(IInterwiewSlotsManagerService interviewSlotsManagerService, ModelMapper modelMapper) {
        this.interviewSlotsManagerService = interviewSlotsManagerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<InterviewSlot>> listCandidateInterviewSlots(Long candidateId, @Valid List<Long> interviewerIdList) {

        if (interviewerIdList.isEmpty()) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Interviewer id list cannot be empty");
        }

        return ResponseEntity.ok(this.interviewSlotsManagerService.listInterviewSlots(candidateId, interviewerIdList)
                .stream()
                .map(is -> this.modelMapper.map(is, InterviewSlot.class))
                .collect(Collectors.toList()));
    }
}
