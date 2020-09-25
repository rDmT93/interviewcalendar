package pt.rt.interviewcalendar.management.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.management.function.GetOverlapPeriods;
import pt.rt.interviewcalendar.management.function.SplitIntervalHourPeriods;
import pt.rt.interviewcalendar.management.service.IInterwiewSlotsManagerService;
import pt.rt.interviewcalendar.management.validator.RoleValidator;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;
import pt.rt.interviewcalendar.model.dto.RoleEnum;
import pt.rt.interviewcalendar.service.ISlotRepositoryService;
import pt.rt.interviewcalendar.service.IUserRepositoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewSlotsManagerService implements IInterwiewSlotsManagerService {

    private final RoleValidator roleValidator;
    private final GetOverlapPeriods getOverlapPeriods;
    private final SplitIntervalHourPeriods splitIntervalHourPeriods;
    private final IUserRepositoryService userRepositoryService;
    private final ISlotRepositoryService slotRepositoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public InterviewSlotsManagerService(RoleValidator roleValidator,
                                        GetOverlapPeriods getOverlapPeriods,
                                        SplitIntervalHourPeriods splitIntervalHourPeriods,
                                        IUserRepositoryService userRepositoryService,
                                        ISlotRepositoryService slotRepositoryService,
                                        ModelMapper modelMapper) {
        this.roleValidator = roleValidator;
        this.getOverlapPeriods = getOverlapPeriods;
        this.splitIntervalHourPeriods = splitIntervalHourPeriods;
        this.userRepositoryService = userRepositoryService;
        this.slotRepositoryService = slotRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<InterviewSlotDTO> listInterviewSlots(Long candidateId, List<Long> interviewerIdList) {
        User candidate = userRepositoryService.get(candidateId);
        roleValidator.accept(candidate, RoleEnum.CANDIDATE.toString());

        List<User> interviewers = userRepositoryService.getAllById(interviewerIdList);
        if (interviewerIdList.size() != interviewers.size()) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Interviewers not found");
        }
        interviewers.forEach(i -> roleValidator.accept(i, RoleEnum.INTERVIEWER.toString()));

        List<InterviewSlotDTO> interviewSlots = getOverlapPeriods.apply(candidate, interviewers);
        return getOverlapPeriods.apply(candidate, interviewers)
                .stream()
                .map(splitIntervalHourPeriods::apply)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
