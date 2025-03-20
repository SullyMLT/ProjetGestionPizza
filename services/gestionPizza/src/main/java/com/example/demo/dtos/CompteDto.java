package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CompteDto {
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean activer;
}