package com.example.jwtcors.dto;

public record AdminPanelResponse(String title,
                                 Long userId,
                                 String username,
                                 String role,
                                 Object permissions ) {
}
