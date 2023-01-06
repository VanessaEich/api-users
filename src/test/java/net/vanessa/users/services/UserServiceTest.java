package net.vanessa.users.services;

import net.vanessa.users.dtos.RequestUserDTO;
import net.vanessa.users.dtos.ResponseUserDTO;
import net.vanessa.users.entities.Address;
import net.vanessa.users.entities.User;
import net.vanessa.users.repositories.IUsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * This class represents a service concept that will perform tests on the different methods of the application's business layer
 * @author Vanessa Eich on 02/01/2023
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class UserServiceTest {

    @MockBean
    IUsersRepository repository;

    @Autowired
    UserService service;

    private static final String NAME = "Ciclano Souza";
    private static final LocalDate BIRTHDATE = LocalDate.of(1995, Month.APRIL, 20);
    private static final  Address ADDRESS = new Address(null, "Rua das Flores", "89900-000", "500", "Jaraguá do Sul", true, null);

    @Before
    public void setUp(){
        BDDMockito.given(repository.findByName(Mockito.anyString())).willReturn(List.of(new User()));
    }
    @Test
    public void testFindByName(){
        List<ResponseUserDTO> user = service.findByName("Fulano de Tal");
        assertFalse(user.isEmpty());
    }

    @Test
    public void testSave() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(ADDRESS);
        BDDMockito.given(repository.save(Mockito.any(User.class))).willReturn(getMockUser());
        ResponseUserDTO response = service.save(new RequestUserDTO(NAME, BIRTHDATE, addressList));

        assertNotNull(response);
        assertEquals("Ciclano Souza", response.name());
    }

    public User getMockUser(){
        List<Address> addressList = new ArrayList<>();
        addressList.add(ADDRESS);
        User user = new User();
        user.setName(NAME);
        user.setBirthDate(BIRTHDATE);
        addressList.add(ADDRESS);
        user.setAddressList(addressList);

        return user;
    }

    @Test
    public void testFindAll() {
        List<User> list = new ArrayList<>();
        list.add(getMockUser());

        BDDMockito.given(repository.findAll()).willReturn(list);

        List<ResponseUserDTO> response = service.listAll();

        assertNotNull(response);
        assertEquals(response.size(), 1);

    }

}
