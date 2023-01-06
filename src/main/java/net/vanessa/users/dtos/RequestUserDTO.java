package net.vanessa.users.dtos;

import net.vanessa.users.entities.Address;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * This DTO contains the information necessary to register a new user
 * @author Vanessa Eich on 02/01/2023
 */
public record RequestUserDTO(

        @NotEmpty(message = "{required.name.field}")
        @Length(min = 3, max = 50, message = "{invalid.name.field}")
        String name,

        @NotNull(message = "{required.birthDate.field}")
        LocalDate birthDate,

        @NotNull(message = "{required.address.field}")
        List<Address> addressList
) {

}
