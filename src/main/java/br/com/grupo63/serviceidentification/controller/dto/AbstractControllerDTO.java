package br.com.grupo63.serviceidentification.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractControllerDTO {

    @Schema(defaultValue = "1")
    protected Long id;

}
