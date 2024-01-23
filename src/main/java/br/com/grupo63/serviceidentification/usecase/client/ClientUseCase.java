package br.com.grupo63.serviceidentification.usecase.client;

import br.com.grupo63.serviceidentification.entity.client.Client;
import br.com.grupo63.serviceidentification.exception.NotFoundException;
import br.com.grupo63.serviceidentification.gateway.client.IClientGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientUseCase implements IClientUseCase {

    private final IClientGateway gateway;

    @Override
    public Client identify(Client client) {
        Optional<Client> optionalClient = gateway.findByNationalId(client.getNationalId());

        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }

        return gateway.saveAndFlush(client);
    }

    @Override
    public Client findByNationalId(String nationalId) throws NotFoundException {
        return gateway.findByNationalId(nationalId).orElseThrow(NotFoundException::new);
    }

    @Override
    public Client create(Client client) {
        return gateway.saveAndFlush(client);
    }

    @Override
    public Client read(String id) throws NotFoundException {
        return gateway.findByIdAndDeletedFalse(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Client> list() {
        return gateway.findByDeletedFalse();
    }

    @Override
    public Client update(Client client) {
        return gateway.saveAndFlush(client);
    }

    @Override
    public void delete(Client client) {
        client.delete();

        gateway.saveAndFlush(client);
    }

}
