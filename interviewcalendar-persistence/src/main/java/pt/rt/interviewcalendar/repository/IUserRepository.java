package pt.rt.interviewcalendar.repository;

import org.springframework.data.repository.CrudRepository;
import pt.rt.interviewcalendar.model.domain.User;

public interface IUserRepository extends CrudRepository<User, Long> {


}
