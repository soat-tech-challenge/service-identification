package br.com.grupo63.serviceidentification.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DefaultResponseDTO {
    private String title;
    private String description;
}
