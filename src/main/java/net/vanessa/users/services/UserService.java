package net.vanessa.users.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.vanessa.users.dtos.RequestUpdateUserDTO;
import net.vanessa.users.dtos.RequestUserDTO;
import net.vanessa.users.dtos.ResponseUserDTO;
import net.vanessa.users.entities.User;
import net.vanessa.users.repositories.IUsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents a service concept that will perform different actions related to users
 * @author Vanessa Eich on 02/01/2023
 */
@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserService {

    private final IUsersRepository usersRepository;

    /**
     * This method lists all users records
     * @return the list of all users records
     * @author Vanessa Eich
     */
    public List<ResponseUserDTO> listAll() {
        List<User> users = usersRepository.findAll();

        return users.stream().map(User::toDTO).collect(Collectors.toList());
    }

    /**
     * This method creates a new User
     * @author Vanessa Eich
     */
    @Transactional
    public ResponseUserDTO save (RequestUserDTO userDTO) {
        if(userDTO.addressList().get(0).getAddressId() != null){
            userDTO.addressList().get(0).setAddressId(null);
        }
        User people = new User(userDTO);
        if (people.validatePrincipalAddress()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Há mais de um endereço principal para o usuário!");
        }
        User user = usersRepository.save(people);
        return user.toDTO();
    }

    /**
     * This method lists the data from a users by name
     * @return the list of all users records by name
     * @author Vanessa Eich
     */
    public List<ResponseUserDTO> findByName (String name){
        List<User> users = usersRepository.findByName("%" + name + "%");
        return users.stream().map(User::toDTO).collect(Collectors.toList());
    }

    /**
     * This method a users by id
     * @return a user record by id
     * @author Vanessa Eich
     */
    public ResponseUserDTO findById (Long id) {
        Optional<User> user = usersRepository.findById(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado");
        }
        return user.get().toDTO();
    }

    /**
     * This method updates an existing user
     * @author Vanessa Eich
     */
    @Transactional
    public ResponseUserDTO updateUser (Long id, RequestUpdateUserDTO user) {
        Optional<User> people = usersRepository.findById(id);
        if(people.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado");
        } else {
            User newUser = new User(user);
            if (newUser.validatePrincipalAddress()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Há mais de um endereço principal para o usuário!");
            }
            newUser.setUserId(people.get().getUserId());
            newUser.setName(user.name());
            newUser.setBirthDate(user.birthDate());
            user.addressList().forEach(x-> {
                x.setUser(newUser);
            });
            return usersRepository.save(newUser).toDTO();
        }
    }
}
