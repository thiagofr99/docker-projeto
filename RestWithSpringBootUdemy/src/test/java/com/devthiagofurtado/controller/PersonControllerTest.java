package com.devthiagofurtado.controller;

import com.devthiagofurtado.creator.BookCreator;
import com.devthiagofurtado.creator.PersonCreator;
import com.devthiagofurtado.data.vo.BooksVO;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.service.BookService;
import com.devthiagofurtado.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Autowired
    private ObjectMapper mapper;

    private final String BASE_URL = "/api/person/v1";

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        headers = new HttpHeaders();
        headers.add("Accept","application/json");

        BDDMockito.when(personService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PersonCreator.voComId());

        BDDMockito.when(personService.findAllByFirstName(ArgumentMatchers.anyString(),ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(PersonCreator.voComId())));

        BDDMockito.when(personService.create(ArgumentMatchers.any(PersonVO.class)))
                .thenReturn(PersonCreator.voComId());

        BDDMockito.when(personService.update(ArgumentMatchers.any(PersonVO.class)))
                .thenReturn(PersonCreator.voComId());
    }

    @Test
    void buscarPorId() throws Exception {
        mockMvc.perform(get(BASE_URL+"/1").headers(headers)).andExpect(status().isOk());
    }

    @Test
    void buscarTodos() throws Exception {
        mockMvc.perform(get(BASE_URL).headers(headers)).andExpect(status().isOk());
    }

    @Test
    void buscarTodosPorFirstName() throws Exception {
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("page", "1");
        param.add("limit", "10");
        param.add("direction", "ASC");
        param.add("firstName", "a");
        mockMvc.perform(get(BASE_URL+"/findAllByFirstName").params(param).headers(headers)).andExpect(status().isOk());
    }


    @Test
    void salvar() throws Exception {
        String jsonRequest = mapper.writeValueAsString(PersonCreator.voComId());

        mockMvc.perform(post(BASE_URL+"/salvar").headers(headers).contentType(APPLICATION_JSON_UTF8).content(jsonRequest)).andExpect(status().isOk());
    }

    @Test
    void atualizar() throws Exception {
        String jsonRequest = mapper.writeValueAsString(PersonCreator.voComId());

        mockMvc.perform(put(BASE_URL+"/atualizar").headers(headers).contentType(APPLICATION_JSON_UTF8).content(jsonRequest)).andExpect(status().isOk());
    }

    @Test
    void delete_retornaNada() throws Exception {
        mockMvc.perform(delete(BASE_URL+"/1").headers(headers)).andExpect(status().isOk());
    }
}