package com.devthiagofurtado.creator;

import com.devthiagofurtado.data.model.Person;
import com.devthiagofurtado.data.vo.PersonVO;

public class PersonCreator {

    private static String TESTE = "teste";

    public static PersonVO voSemId(){
        return PersonVO.builder()
                .address(TESTE)
                .firstName(TESTE)
                .lastName(TESTE)
                .gender(TESTE)
                .build();
    }

    public static PersonVO voComId(){
        return PersonVO.builder()
                .key(1L)
                .address(TESTE)
                .firstName(TESTE)
                .lastName(TESTE)
                .gender(TESTE)
                .build();
    }

    public static Person entityComId(){
        return Person.builder()
                .id(1L)
                .address(TESTE)
                .firstName(TESTE)
                .lastName(TESTE)
                .gender(TESTE)
                .build();
    }
}
