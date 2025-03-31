package com.dev.vizzscript.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    private String id;
    private String schoolName;
    private String location;
    private String principalName;
}
