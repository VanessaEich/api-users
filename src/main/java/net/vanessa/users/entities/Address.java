package net.vanessa.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * This entity represents the main data to generate an address
 * @author Vanessa Eich on 02/01/2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", length = 100, nullable = false)
    private Long addressId;

    @Column(name = "public_place", nullable = false)
    private String publicPlace;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "principal", nullable = false)
    private Boolean principal;

    @JsonIgnore
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name= "user_id")
    private User user;

}
