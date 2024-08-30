package com.SlicK.basic_crud.Entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonResponse {
    private List<PersonDto> data;
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private Boolean last;
}
