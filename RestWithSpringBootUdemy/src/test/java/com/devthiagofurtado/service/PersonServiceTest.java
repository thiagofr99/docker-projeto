package com.devthiagofurtado.service;

import com.devthiagofurtado.creator.PersonCreator;
import com.devthiagofurtado.data.model.Person;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {

        BDDMockito.when(personRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(PersonCreator.entityComId()));

        BDDMockito.when(personRepository.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreator.entityComId());

        BDDMockito.when(personRepository.findAll())
                .thenReturn(Collections.singletonList(PersonCreator.entityComId()));

        BDDMockito.when(personRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(PersonCreator.entityComId())));

        BDDMockito.doNothing().when(personRepository).delete(ArgumentMatchers.any(Person.class));

    }

    @Test
    void create_retornaPersonVO_sucesso() {

        PersonVO teste = personService.create(PersonCreator.voSemId());

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getKey()).isNotNull();

    }

    @Test
    void update_retornaPersonVO_sucesso() {

        PersonVO teste = personService.update(PersonCreator.voComId());

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getKey()).isNotNull();
    }

    @Test
    void update_retornaResourceNotFoundException_erro() {
        BDDMockito.when(personRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()-> personService.update(PersonCreator.voComId()))
                .isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    void delete() {

        personService.delete(1L);

    }

    @Test
    void findById_retornaPersonVO_sucesso() {

        PersonVO teste = personService.findById(1L);

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getKey()).isNotNull();
    }

    @Test
    void findById_retornaResourceNotFoundException_erro() {
        BDDMockito.when(personRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> personService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    void findAll() {

        Pageable pageable = PageRequest.of(1,12);

        Page<PersonVO> teste = personService.findAll(pageable);

        Assertions.assertThat(teste).isNotNull().isNotEmpty();
        Assertions.assertThat(teste.get().collect(Collectors.toList()).get(0).getKey()).isNotNull();
    }
}