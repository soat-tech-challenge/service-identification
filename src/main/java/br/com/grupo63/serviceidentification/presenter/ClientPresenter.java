package br.com.grupo63.serviceidentification.presenter;

import br.com.grupo63.serviceidentification.controller.dto.ClientControllerDTO;
import br.com.grupo63.serviceidentification.entity.client.Client;

public class ClientPresenter {

    public static ClientControllerDTO toDto(Client entity) {
        ClientControllerDTO dto = new ClientControllerDTO();

        dto.setId(entity.getId());
        dto.setNationalId(entity.getNationalId());

        return dto;
    }

}
