package net.vanessa.users.repositories;

import net.vanessa.users.entities.Address;
import net.vanessa.users.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class IUsersRepositoryTest {

    @Autowired
    IUsersRepository usersRepository;

    @Before
    public void setUp(){
        List<Address> addressList = new ArrayList<>();
        Address address = new Address(null, "Rua das Flores", "89900-000", "500", "Jaraguá do Sul", true, null);
        addressList.add(address);
        User user = new User();
        user.setName("Fulano de Tal");
        user.setBirthDate(LocalDate.of(1995, Month.APRIL, 20));
        user.setAddressList(addressList);

        usersRepository.save(user);
    }

    @After
    public void tearDown(){
        usersRepository.deleteAll();
    }

    @Test
    public void testSave() {
        List<Address> addressList = new ArrayList<>();
        Address address = new Address(null, "Rua das Flores", "89900-000", "500", "Jaraguá do Sul", true, null);
        addressList.add(address);
        User user = new User();
        user.setName("Teste");
        user.setBirthDate(LocalDate.of(1995, Month.APRIL, 20));
        user.setAddressList(addressList);

        User response = usersRepository.save(user);

        assertNotNull(response);
    }

    @Test
    public void testFindByName() {
        List<User> response = usersRepository.findByName("Fulano de Tal");
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).getName(),"Fulano de Tal");
    }


}
