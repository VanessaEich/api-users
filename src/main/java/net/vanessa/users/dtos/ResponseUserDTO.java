package net.vanessa.users.dtos;

import net.vanessa.users.entities.Address;

import java.time.LocalDate;
import java.util.List;

/**
 * This DTO lists the users records
 * @author Vanessa Eich on 02/01/2023
 */
public record ResponseUserDTO(

        Long userId,
        String name,
        LocalDate birthDate,
        List<Address> addressList
) {
}
