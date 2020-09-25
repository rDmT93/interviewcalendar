package pt.rt.interviewcalendar.management.service;

import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;

import java.util.List;

public interface IInterwiewSlotsManagerService {

    List<InterviewSlotDTO> listInterviewSlots(Long candidateId, List<Long> interviewerIdList);
}
