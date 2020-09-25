package pt.rt.interviewcalendar.repository;

import org.springframework.data.repository.CrudRepository;
import pt.rt.interviewcalendar.model.domain.Slot;

public interface ISlotRepository extends CrudRepository<Slot, Long> {

}
