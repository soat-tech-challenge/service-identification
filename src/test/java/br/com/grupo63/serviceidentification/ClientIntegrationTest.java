package br.com.grupo63.serviceidentification;

import br.com.grupo63.serviceidentification.api.controller.client.ClientAPIController;
import br.com.grupo63.serviceidentification.controller.ClientController;
import br.com.grupo63.serviceidentification.controller.dto.ClientControllerDTO;
import br.com.grupo63.serviceidentification.gateway.client.ClientJpaAdapter;
import br.com.grupo63.serviceidentification.gateway.client.ClientJpaRepository;
import br.com.grupo63.serviceidentification.gateway.client.entity.ClientPersistenceEntity;
import br.com.grupo63.serviceidentification.usecase.client.ClientUseCase;
import br.com.grupo63.techchallenge.common.api.controller.dto.DefaultResponseDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientIntegrationTest {
    @Mock
    private ClientJpaRepository clientJpaRepository;

    @InjectMocks
    private ClientJpaAdapter clientJpaAdapter;
    private ClientUseCase clientUseCase;
    private ClientController clientController;
    private ClientAPIController clientAPIController;

    private final String defaultNationalId = "12312312312";
    private final ClientPersistenceEntity defaultClientPersistenceEntity = new ClientPersistenceEntity(UUID.randomUUID().toString(), false, defaultNationalId);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientUseCase = new ClientUseCase(clientJpaAdapter);
        clientController = new ClientController(clientUseCase);
        clientAPIController = new ClientAPIController(clientController);
    }

    @SneakyThrows
    @Test
    public void testIdentification_EndToEnd() {
        when(clientJpaRepository.findByNationalIdAndDeletedFalse(defaultNationalId)).thenReturn(Optional.of(defaultClientPersistenceEntity));

        ResponseEntity<ClientControllerDTO> response = clientAPIController.identify(defaultNationalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getNationalId(), defaultNationalId);
    }

    @SneakyThrows
    @Test
    public void testIdentificationWithCreation_EndToEnd() {
        when(clientJpaRepository.findByNationalIdAndDeletedFalse(defaultNationalId)).thenReturn(Optional.empty());
        when(clientJpaRepository.save(any())).thenReturn(defaultClientPersistenceEntity);

        ResponseEntity<ClientControllerDTO> response = clientAPIController.identify(defaultNationalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getNationalId(), defaultNationalId);
        verify(clientJpaRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    public void testCreate_EndToEnd() {
        when(clientJpaRepository.save(any())).thenReturn(defaultClientPersistenceEntity);

        ResponseEntity<ClientControllerDTO> response = clientAPIController.create(defaultNationalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getNationalId(), defaultNationalId);
        verify(clientJpaRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    public void testRead_EndToEnd() {
        when(clientJpaRepository.findByIdAndDeletedFalse(defaultClientPersistenceEntity.getId())).thenReturn(Optional.of(defaultClientPersistenceEntity));

        ResponseEntity<ClientControllerDTO> response = clientAPIController.read(defaultClientPersistenceEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getNationalId(), defaultNationalId);
        verify(clientJpaRepository, times(1)).findByIdAndDeletedFalse(any());
    }

    @SneakyThrows
    @Test
    public void testList_EndToEnd() {
        when(clientJpaRepository.findByDeletedFalse()).thenReturn(List.of(defaultClientPersistenceEntity));

        ResponseEntity<List<ClientControllerDTO>> response = clientAPIController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getBody().get(0).getNationalId(), defaultClientPersistenceEntity.getNationalId());
        verify(clientJpaRepository, times(1)).findByDeletedFalse();
    }

    @SneakyThrows
    @Test
    public void testUpdate_EndToEnd() {
        when(clientJpaRepository.findByIdAndDeletedFalse(defaultClientPersistenceEntity.getId())).thenReturn(Optional.of(defaultClientPersistenceEntity));

        ClientControllerDTO clientControllerDTO = new ClientControllerDTO();

        clientControllerDTO.setId(defaultClientPersistenceEntity.getId());
        clientControllerDTO.setNationalId("11111111111");

        when(clientJpaRepository.save(any())).thenReturn(new ClientPersistenceEntity(defaultClientPersistenceEntity.getId(), defaultClientPersistenceEntity.isDeleted(), clientControllerDTO.getNationalId()));

        ResponseEntity<ClientControllerDTO> response = clientAPIController.update(clientControllerDTO, defaultClientPersistenceEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getNationalId(), clientControllerDTO.getNationalId());
        verify(clientJpaRepository, times(1)).findByIdAndDeletedFalse(any());
        verify(clientJpaRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    public void testDelete_EndToEnd() {
        when(clientJpaRepository.findByIdAndDeletedFalse(defaultClientPersistenceEntity.getId())).thenReturn(Optional.of(defaultClientPersistenceEntity));
        when(clientJpaRepository.save(any())).thenReturn(new ClientPersistenceEntity(defaultClientPersistenceEntity.getId(), true, defaultClientPersistenceEntity.getNationalId()));

        ResponseEntity<Void> response = clientAPIController.delete(defaultClientPersistenceEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientJpaRepository, times(1)).findByIdAndDeletedFalse(any());
        verify(clientJpaRepository, times(1)).save(any());
    }
}
