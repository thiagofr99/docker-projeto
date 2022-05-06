package com.devthiagofurtado.service;

import com.devthiagofurtado.creator.BookCreator;
import com.devthiagofurtado.creator.PersonCreator;
import com.devthiagofurtado.data.model.Book;
import com.devthiagofurtado.data.model.Person;
import com.devthiagofurtado.data.vo.BooksVO;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.repository.BookRepository;
import com.devthiagofurtado.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {

        BDDMockito.when(bookRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(BookCreator.entityComId()));

        BDDMockito.when(bookRepository.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.entityComId());

        BDDMockito.when(bookRepository.findAll())
                .thenReturn(Collections.singletonList(BookCreator.entityComId()));

        BDDMockito.when(bookRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(BookCreator.entityComId())));

        BDDMockito.doNothing().when(bookRepository).delete(ArgumentMatchers.any(Book.class));

    }

    @Test
    void create_retornaPersonVO_sucesso() {

        BooksVO teste = bookService.create(BookCreator.voSemId());

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getKey()).isNotNull();

    }

    @Test
    void update_retornaPersonVO_sucesso() {

        BooksVO teste = bookService.update(BookCreator.voComId());

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getKey()).isNotNull();
    }

    @Test
    void update_retornaResourceNotFoundException_erro() {
        BDDMockito.when(bookRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()-> bookService.update(BookCreator.voComId()))
                .isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    void delete() {

        bookService.delete(1L);

    }

    @Test
    void findById_retornaPersonVO_sucesso() {

        BooksVO teste = bookService.findById(1L);

        Assertions.assertThat(teste).isNotNull();
        Assertions.assertThat(teste.getKey()).isNotNull();
    }

    @Test
    void findById_retornaResourceNotFoundException_erro() {
        BDDMockito.when(bookRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bookService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    void findAll() {

        Pageable pageable = PageRequest.of(1,5, Sort.by(Sort.Direction.ASC,"firstName"));

        Page<BooksVO> teste = bookService.findAll(pageable);


        Assertions.assertThat(teste).isNotNull().isNotEmpty();
        Assertions.assertThat(teste.getContent().get(0).getKey()).isNotNull();
    }
}