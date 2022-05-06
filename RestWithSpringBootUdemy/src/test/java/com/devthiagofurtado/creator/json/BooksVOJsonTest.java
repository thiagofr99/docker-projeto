package com.devthiagofurtado.creator.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BooksVOJsonTest extends ResourceSupport implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long key;

    private String author;

    private String lauchDate;

    private Double price;

    private String title;

}
