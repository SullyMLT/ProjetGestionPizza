package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CommandeDto {
    private Long id;
    private String description;
    private boolean validation;
    private String date;
    private float prix;
}
