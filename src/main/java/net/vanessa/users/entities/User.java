package net.vanessa.users.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.vanessa.users.dtos.RequestUpdateUserDTO;
import net.vanessa.users.dtos.RequestUserDTO;
import net.vanessa.users.dtos.ResponseUserDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This entity represents the main data to generate a user
 * @author Vanessa Eich on 02/01/2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", length = 100, nullable = false)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> addressList = new ArrayList<>();

    /**
     * This method converts a user to a generic DTO type
     * @author Vanessa Eich on 02/01/2023
     */

    public ResponseUserDTO toDTO() {
        return new ResponseUserDTO(
                this.userId,
                this.name,
                this.birthDate,
                this.addressList
        );
    }

    /**
     * This method creates a user based on the data received from a RequestUserDTO
     * @author Vanessa Eich on 02/01/2023
     */
    public User (RequestUserDTO requestDTO) {
        this.name = requestDTO.name();
        this.birthDate= requestDTO.birthDate();
        List<Address> list = new ArrayList<>(requestDTO.addressList());
        this.addressList = list;
    }

    /**
     * This method updates a user based on the data received from a RequestUpdateUserDTO
     * @author Vanessa Eich on 02/01/2023
     */
    public User (RequestUpdateUserDTO requestUpdateUserDTO) {
        this.name = requestUpdateUserDTO.name();
        this.birthDate= requestUpdateUserDTO.birthDate();
        List<Address> list = new ArrayList<>(requestUpdateUserDTO.addressList());
        this.addressList = list;
    }

    /**
     * This method validates if there is already a registered main address
     * @author Vanessa Eich on 03/01/2023
     */
    public boolean validatePrincipalAddress(){
        boolean principalAddressIsPresent = false;
        boolean hasMoreThanOnePrincipalAddress = false;

        for(Address address : this.addressList) {
            if(address.getPrincipal() && principalAddressIsPresent ==true){
                hasMoreThanOnePrincipalAddress = true;
            } else if(address.getPrincipal()){
                principalAddressIsPresent=true;
            }
        }
        return hasMoreThanOnePrincipalAddress;
    }
}
