package pt.rt.interviewcalendar.application;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pt.rt.interviewcalendar.model.domain.Slot;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.service.ISlotRepositoryService;
import pt.rt.interviewcalendar.service.IUserRepositoryService;

@SpringBootApplication
@ComponentScan(basePackages = "pt.rt.interviewcalendar")
@EnableJpaRepositories(basePackages = "pt.rt.interviewcalendar")
@EntityScan(basePackages = "pt.rt.interviewcalendar")
public class Application {

    private static IUserRepositoryService userRepositoryService;
    private static ISlotRepositoryService slotRepositoryService;

    @Autowired
    public Application(IUserRepositoryService userRepositoryService,
                       ISlotRepositoryService slotRepositoryService) {
        this.userRepositoryService = userRepositoryService;
        this.slotRepositoryService = slotRepositoryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        loadData();
    }

    private static void loadData() {
        User u1 = User.builder().name("David").role("INTERVIEWER").build();
        User u2 = User.builder().name("Ingrid").role("INTERVIEWER").build();
        User u3 = User.builder().name("Carl").role("CANDIDATE").build();
        userRepositoryService.save(u1);
        userRepositoryService.save(u2);
        userRepositoryService.save(u3);

        Slot slotu1_1 = Slot.builder().user(u1)
                .startDateTime(new DateTime("2020-09-28T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-28T16:00:00.00Z").toDate()).build();

        Slot slotu1_2 = Slot.builder().user(u1)
                .startDateTime(new DateTime("2020-09-29T06:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-29T16:00:00.00Z").toDate()).build();

        Slot slotu1_3 = Slot.builder().user(u1)
                .startDateTime(new DateTime("2020-09-30T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-30T16:00:00.00Z").toDate()).build();

        Slot slotu1_4 = Slot.builder().user(u1)
                .startDateTime(new DateTime("2020-10-01T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-10-01T16:00:00.00Z").toDate()).build();

        Slot slotu1_5 = Slot.builder().user(u1)
                .startDateTime(new DateTime("2020-10-02T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-10-02T16:00:00.00Z").toDate()).build();

        slotRepositoryService.save(slotu1_1);
        slotRepositoryService.save(slotu1_2);
        slotRepositoryService.save(slotu1_3);
        slotRepositoryService.save(slotu1_4);
        slotRepositoryService.save(slotu1_5);

        Slot slotu2_1 = Slot.builder().user(u2)
                .startDateTime(new DateTime("2020-09-28T12:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-28T18:00:00.00Z").toDate()).build();

        Slot slotu2_2 = Slot.builder().user(u2)
                .startDateTime(new DateTime("2020-09-29T08:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-29T12:00:00.00Z").toDate()).build();

        Slot slotu2_3 = Slot.builder().user(u2)
                .startDateTime(new DateTime("2020-09-30T12:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-30T18:00:00.00Z").toDate()).build();

        Slot slotu2_4 = Slot.builder().user(u2)
                .startDateTime(new DateTime("2020-10-01T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-10-01T12:00:00.00Z").toDate()).build();

        slotRepositoryService.save(slotu2_1);
        slotRepositoryService.save(slotu2_2);
        slotRepositoryService.save(slotu2_3);
        slotRepositoryService.save(slotu2_4);

        Slot slotu3_1 = Slot.builder().user(u3)
                .startDateTime(new DateTime("2020-09-28T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-28T10:00:00.00Z").toDate()).build();

        Slot slotu3_2 = Slot.builder().user(u3)
                .startDateTime(new DateTime("2020-09-29T07:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-29T10:00:00.00Z").toDate()).build();

        Slot slotu3_3 = Slot.builder().user(u3)
                .startDateTime(new DateTime("2020-09-30T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-30T10:00:00.00Z").toDate()).build();

        Slot slotu3_4 = Slot.builder().user(u3)
                .startDateTime(new DateTime("2020-10-01T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-10-01T10:00:00.00Z").toDate()).build();

        Slot slotu3_5 = Slot.builder().user(u3)
                .startDateTime(new DateTime("2020-10-02T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-10-02T10:00:00.00Z").toDate()).build();

        Slot slotu3_6 = Slot.builder().user(u3)
                .startDateTime(new DateTime("2020-10-07T10:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-10-07T12:00:00.00Z").toDate()).build();

        slotRepositoryService.save(slotu3_1);
        slotRepositoryService.save(slotu3_2);
        slotRepositoryService.save(slotu3_3);
        slotRepositoryService.save(slotu3_4);
        slotRepositoryService.save(slotu3_5);
        slotRepositoryService.save(slotu3_6);
    }

}
