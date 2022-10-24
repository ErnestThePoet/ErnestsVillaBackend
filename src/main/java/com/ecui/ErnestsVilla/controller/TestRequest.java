package com.ecui.ErnestsVilla.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRequest {
    private String birthday;
    private Integer age;
    private List<Integer> as;
}
