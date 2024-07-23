package com.ali.animalsanctuary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for transferring {@link} data between layers.
 *
 * This class is used to encapsulate the data of a {@link} entity for use in data transfer operations,
 * such as creating or updating visit records and retrieving visit details.
 *
 * <p>Fields:
 * <ul>
 *     <li>{@link #id}: The unique identifier of the visit.</li>
 *     <li>{@link #startTime}: The start time of the visit.</li>
 *     <li>{@link #endTime}: The end time of the visit.</li>
 *     <li>{@link #userId}: The ID of the user associated with the visit.</li>
 * </ul>
 *
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;

}
