package net.vanessa.users.dtos;

import net.vanessa.users.entities.Address;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * This DTO contains the information necessary to update a user
 * @author Vanessa Eich on 02/01/2023
 */
public record RequestUpdateUserDTO(

        @NotEmpty(message = "{required.id.field}")
        String userId,
        @NotEmpty(message = "{required.name.field}")
        @Length(min = 3, max = 50, message = "{campo.nome.invalido}")
        String name,

        @NotNull(message = "{required.birthDate.field}")
        LocalDate birthDate,

        List<Address> addressList
) {
}
