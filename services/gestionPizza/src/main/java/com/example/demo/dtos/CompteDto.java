package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CompteDto {
    private long id;
    private String username;
    private String password;
    private List<Long> commandeIds;
}