package com.ali.animalsanctuary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;

}
