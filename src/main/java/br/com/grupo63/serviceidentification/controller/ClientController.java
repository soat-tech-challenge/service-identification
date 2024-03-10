package br.com.grupo63.serviceidentification.controller;

import br.com.grupo63.serviceidentification.adapter.ClientAdapter;
import br.com.grupo63.serviceidentification.controller.dto.ClientControllerDTO;
import br.com.grupo63.serviceidentification.entity.client.Client;
import br.com.grupo63.serviceidentification.presenter.ClientPresenter;
import br.com.grupo63.serviceidentification.usecase.client.ClientUseCase;
import br.com.grupo63.techchallenge.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientController {

    private final ClientUseCase clientUseCase;

    public ClientControllerDTO identify(ClientControllerDTO dto) {
        Client entity = new Client();
        ClientAdapter.fillEntity(dto, entity);
        return ClientPresenter.toDto(clientUseCase.identify(entity));
    }

    public ClientControllerDTO create(ClientControllerDTO dto) {
        Client entity = new Client();
        ClientAdapter.fillEntity(dto, entity);
        entity = clientUseCase.create(entity);

        return ClientPresenter.toDto(entity);
    }

    public ClientControllerDTO read(String id) throws NotFoundException {
        return ClientPresenter.toDto(clientUseCase.read(id));
    }

    public List<ClientControllerDTO> list() {
        return this.clientUseCase.list().stream().map(ClientPresenter::toDto).toList();
    }

    public ClientControllerDTO update(ClientControllerDTO dto, String id) throws NotFoundException {
        Client entity = clientUseCase.read(id);
        ClientAdapter.fillEntity(dto, entity);
        entity = clientUseCase.update(entity);
        return ClientPresenter.toDto(entity);
    }

    public void delete(String id) throws NotFoundException {
        Client entity = clientUseCase.read(id);
        clientUseCase.delete(entity);
    }

    public void deletePersonalData(String nationalId) throws NotFoundException {
        Client entity = clientUseCase.findByNationalId(nationalId);
        clientUseCase.deletePersonalData(entity);
    }
}
