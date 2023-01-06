package net.vanessa.users.rest.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.vanessa.users.dtos.RequestUpdateUserDTO;
import net.vanessa.users.dtos.RequestUserDTO;
import net.vanessa.users.dtos.ResponseUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * This interface defines the endpoints to get the application User
 * @author Vanessa Eich on 03/01/2023
 */

@RestController
@RequestMapping("/api/users")
@Tag(name = "API Usuários", description = "API para gerenciamento de usuários")
public interface IUserController {

    /**
     * This endpoint request the listing of Users
     * @return The list with all the Users
     * @author Vanessa Eich
     */
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados")
    @GetMapping
    ResponseEntity<List<ResponseUserDTO>> listAll ();

    /**
     * This endpoint registers a new User
     * @author Vanessa Eich
     */
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um usuário no banco de dados")
    @PostMapping
    ResponseEntity<ResponseUserDTO> save (@RequestBody @Valid RequestUserDTO user,
                                          UriComponentsBuilder uriBuilder);

    /**
     * This endpoint requests the user by name
     * @return A user
     * @author Vanessa Eich
     */
    @Operation(summary = "Buscar usuário", description = "Buscar usuário cadastrado pelo nome")
    @GetMapping("/name")
    ResponseEntity<List<ResponseUserDTO>> findByName (@RequestParam(value = "name", required = false, defaultValue = "") String name);

    /**
     * This endpoint requests the user by id
     * @return A user
     * @author Vanessa Eich
     */
    @Operation(summary = "Buscar usuário", description = "Buscar usuário cadastrado pelo id")
    @GetMapping("/{id}")
    ResponseEntity<ResponseUserDTO> findById (@PathVariable Long id);

    /**
     * This endpoint updates an existing user
     * @author Vanessa Eich
     */
    @Operation(summary = "Editar usuário", description = "Editar usuário cadastrado pelo id")
    @PutMapping("/{id}")
    ResponseEntity<ResponseUserDTO> updateUser (@PathVariable Long id, @RequestBody @Valid RequestUpdateUserDTO user);
}
