package br.com.grupo63.serviceidentification.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientControllerDTO extends AbstractControllerDTO {

    @Schema(example = "01234567890")
    private String nationalId;
}
