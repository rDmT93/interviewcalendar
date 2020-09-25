package pt.rt.interviewcalendar.facade.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pt.rt.interviewcalendar.api.SlotsApi;
import pt.rt.interviewcalendar.api.model.Slot;
import pt.rt.interviewcalendar.api.model.SlotId;
import pt.rt.interviewcalendar.management.service.ISlotManagerService;
import pt.rt.interviewcalendar.model.dto.SlotDTO;

import javax.validation.Valid;

@RestController
public class SlotsController implements SlotsApi {

    private final ISlotManagerService slotManagerService;
    private final ModelMapper modelMapper;

    @Autowired
    public SlotsController(ISlotManagerService slotManagerService, ModelMapper modelMapper) {
        this.slotManagerService = slotManagerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<SlotId> createSlot(@Valid Slot body) {
        SlotDTO savedSlotDTO = this.slotManagerService.saveSlot(this.modelMapper.map(body, SlotDTO.class));
        SlotId slotId = new SlotId();
        slotId.setId(savedSlotDTO.getSlotId());
        return ResponseEntity.ok(slotId);
    }

    @Override
    public ResponseEntity<Void> deleteSlot(Long slotId) {
        this.slotManagerService.deleteSlot(slotId);
        return ResponseEntity.noContent().build();
    }
}
