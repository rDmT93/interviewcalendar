package pt.rt.interviewcalendar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SlotDTO {

    @EqualsAndHashCode.Include
    private Long slotId;
    private Long userId;
    private Date startDateTime;
    private Date endDateTime;
}