package br.com.grupo63.serviceidentification.api.controller.client;

import br.com.grupo63.serviceidentification.api.controller.AbstractAPIController;
import br.com.grupo63.serviceidentification.api.controller.dto.DefaultResponseDTO;
import br.com.grupo63.serviceidentification.controller.ClientController;
import br.com.grupo63.serviceidentification.controller.dto.ClientControllerDTO;
import br.com.grupo63.serviceidentification.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "CRUD de clientes para gerenciamento")
@RequiredArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientAPIController extends AbstractAPIController {

    private final ClientController clientController;

    @Operation(
            tags = "1ª chamada - Fluxo principal - Identificação",
            summary = "Identificar o cliente (Utilizado pelo Lambda de identificação)",
            description = "Recupera o ID do cliente caso ele exista e se não existir já o cria")
    @PostMapping("/identification")
    public ResponseEntity<ClientControllerDTO> identify(@RequestParam String nationalId) throws NotFoundException {
        ClientControllerDTO clientDTO = new ClientControllerDTO(nationalId);
        return ResponseEntity.ok(clientController.identify(clientDTO));
    }

    @Operation(
            summary = "Cria um cliente",
            description = "Registra um cliente através de seu CPF")
    @PostMapping
    public ResponseEntity<ClientControllerDTO> create(@RequestParam String nationalId) throws NotFoundException {
        ClientControllerDTO clientDTO = new ClientControllerDTO(nationalId);
        return ResponseEntity.ok(clientController.create(clientDTO));
    }

    @Operation(
            summary = "Recuperar cliente",
            description = "Exibe os dados de um produto a partir de seu id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientControllerDTO> read(@PathVariable("id") String id) throws NotFoundException {
        return ResponseEntity.ok(clientController.read(id));
    }

    @Operation(
            summary = "Listar clientes",
            description = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<ClientControllerDTO>> list() {
        return ResponseEntity.ok(clientController.list());
    }

    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza um cliente por id com os dados enviados")
    @PutMapping("/{id}")
    public ResponseEntity<ClientControllerDTO> update(@Valid @RequestBody ClientControllerDTO dto, @PathVariable("id") String id) throws NotFoundException {
        return ResponseEntity.ok(clientController.update(dto, id));
    }

    @Operation(
            summary = "Excluir cliente",
            description = "Exclui um cliente por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable("id") String id) throws NotFoundException {
        clientController.delete(id);
        return ResponseEntity.ok().build();
    }


}
