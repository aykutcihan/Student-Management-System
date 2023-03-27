package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MeetRequestWithoutId implements Serializable {

    @NotNull
    private String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Future
    private LocalDate date;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalTime startTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalTime stopTime;

    @NotNull
    private Long[] studentIds;
}
