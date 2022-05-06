package com.devthiagofurtado.creator;

import com.devthiagofurtado.converter.DozerConverter;
import com.devthiagofurtado.creator.json.BooksVOJsonTest;
import com.devthiagofurtado.data.model.Book;
import com.devthiagofurtado.data.vo.BooksVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class BookCreator {

    private static String TESTE = "teste";

    public static BooksVO voSemId() {
        return BooksVO.builder()
                .author(TESTE)
                .lauchDate(new Date())
                .title(TESTE)
                .price(1.0)
                .build();
    }

    public static BooksVO voComId() {
        return BooksVO.builder()
                .key(1L)
                .author(TESTE)
                .lauchDate(new Date())
                .title(TESTE)
                .price(1.0)
                .build();
    }

    public static Book entityComId() {
        return DozerConverter.parseObject(voComId(), Book.class);
    }

    public static BooksVOJsonTest voJsonTest(BooksVO booksVO) {
      return     BooksVOJsonTest.builder()
                .author(booksVO.getAuthor())
                .key(booksVO.getKey())
                .price(booksVO.getPrice())
                .title(booksVO.getTitle())
                .lauchDate("2022-04-30")
                .build();
    }
}
