package pt.rt.interviewcalendar.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "Slot", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "startDateTime", "endDateTime"}, name = "slot_unique_userId_startDateTime_endDateTime")})
public class Slot {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SLOT")
    @SequenceGenerator(name = "SEQ_SLOT", sequenceName = "SEQ_SLOT", allocationSize = 10)
    @Column(name = "slotId")
    private Long slotId;

    @Column(name = "startDateTime")
    private Date startDateTime;

    @Column(name = "endDateTime")
    private Date endDateTime;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

}