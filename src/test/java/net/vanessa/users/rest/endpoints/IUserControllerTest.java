package net.vanessa.users.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.vanessa.users.dtos.RequestUserDTO;
import net.vanessa.users.dtos.ResponseUserDTO;
import net.vanessa.users.entities.Address;
import net.vanessa.users.entities.User;
import net.vanessa.users.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class represents a service concept that will perform tests on the different methods of the application's business layer
 * @author Vanessa Eich on 02/01/2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
public class IUserControllerTest {

    private static final String NAME = "Ciclano Souza";
    private static final LocalDate BIRTHDATE = LocalDate.of(1995, Month.APRIL, 20);
    private static final  Address ADDRESS = new Address(null, "Rua das Flores", "89900-000", "500", "Jaraguá do Sul", true, null);
    private static final String URL = "/api/users";
    @MockBean
    UserService service;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(service.save(Mockito.any(RequestUserDTO.class))).willReturn(getMockUser());
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveInvalidUser() throws Exception {

        BDDMockito.given(service.save(Mockito.any(RequestUserDTO.class))).willReturn(getMockUser());
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayloadFail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListAll() throws Exception {
        List<Address> addressList = new ArrayList<>();
        addressList.add(ADDRESS);
        User user = new User();
        user.setName(NAME);
        user.setBirthDate(BIRTHDATE);
        addressList.add(ADDRESS);
        List<User> list = new ArrayList<>();
        list.add(user);

        BDDMockito.given(service.listAll()).willReturn(List.of(user.toDTO()));
        mvc.perform(MockMvcRequestBuilders.get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public ResponseUserDTO getMockUser(){
        List<Address> addressList = new ArrayList<>();
        addressList.add(ADDRESS);
        User user = new User();
        user.setName(NAME);
        user.setBirthDate(BIRTHDATE);
        addressList.add(ADDRESS);
        user.setAddressList(addressList);

        return user.toDTO();
    }

    public String getJsonPayload() throws JsonProcessingException {
        List<Address> addressList = new ArrayList<>();
        addressList.add(ADDRESS);
        RequestUserDTO dto = new RequestUserDTO(NAME, BIRTHDATE, addressList);
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper.writeValueAsString(dto);
    }

    public String getJsonPayloadFail() throws JsonProcessingException {
        List<Address> addressList = new ArrayList<>();
        addressList.add(ADDRESS);
        RequestUserDTO dto = new RequestUserDTO("", BIRTHDATE, addressList);
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper.writeValueAsString(dto);
    }
}
