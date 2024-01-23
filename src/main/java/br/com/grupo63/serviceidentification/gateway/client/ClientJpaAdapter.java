package br.com.grupo63.serviceidentification.gateway.client;

import br.com.grupo63.serviceidentification.entity.client.Client;
import br.com.grupo63.serviceidentification.gateway.client.entity.ClientPersistenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
// TODO: Perguntar se faz sentido essa classe existir sendo que temos o ClientGateway
// isso existia para não haver conflitos com os metodos na interface do repository
public class ClientJpaAdapter implements IClientGateway {

    private final ClientJpaRepository jpaRepository;

    @Override
    public List<Client> findByDeletedFalse() {
        return jpaRepository.findByDeletedFalse().stream()
                .map(ClientPersistenceEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Client saveAndFlush(Client client) {
        ClientPersistenceEntity entity = new ClientPersistenceEntity(client);

        entity = jpaRepository.saveAndFlush(entity);

        return entity.toModel();
    }

    @Override
    public Optional<Client> findByNationalId(String nationalId) {
        return jpaRepository.findByNationalIdAndDeletedFalse(nationalId)
                .map(ClientPersistenceEntity::toModel);
    }

    @Override
    public Optional<Client> findByIdAndDeletedFalse(String id) {
        return jpaRepository.findByIdAndDeletedFalse(id)
                .map(ClientPersistenceEntity::toModel);
    }
}
