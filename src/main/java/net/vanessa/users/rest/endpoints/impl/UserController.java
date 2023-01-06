package net.vanessa.users.rest.endpoints.impl;

import lombok.AllArgsConstructor;
import net.vanessa.users.dtos.RequestUpdateUserDTO;
import net.vanessa.users.rest.endpoints.IUserController;
import net.vanessa.users.dtos.RequestUserDTO;
import net.vanessa.users.dtos.ResponseUserDTO;
import net.vanessa.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * This class is intended to implement the IUserController based on Rest communication.
 * @author Vanessa Eich on 03/01/2023
 */
@AllArgsConstructor
@Component
public class UserController implements IUserController {

    private final UserService service;

    @Override
    public ResponseEntity<List<ResponseUserDTO>> listAll (){

        List<ResponseUserDTO> listResponseDTO = service.listAll();
        return ResponseEntity.ok().body(listResponseDTO);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> save (@RequestBody @Valid RequestUserDTO user,
                                                 UriComponentsBuilder uriBuilder){
        ResponseUserDTO responseUserDTO = service.save(user);
        URI uri = uriBuilder.path("/api/users/{id}").buildAndExpand(responseUserDTO.userId()).toUri();
        return ResponseEntity.created(uri).body(responseUserDTO);
    }

    @Override
    public ResponseEntity<List<ResponseUserDTO>> findByName (@RequestParam(value = "name",
            required = false, defaultValue = "") String name){
        List<ResponseUserDTO> listResponseDTO = service.findByName(name);
        return ResponseEntity.ok().body(listResponseDTO);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> findById (@PathVariable Long id){
        ResponseUserDTO responseUserDTO = service.findById(id);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> updateUser (@PathVariable Long id,
                                                        @RequestBody @Valid RequestUpdateUserDTO user){
        ResponseUserDTO userDTO = service.updateUser(id, user);
        return ResponseEntity.ok().body(userDTO);
    }
}
