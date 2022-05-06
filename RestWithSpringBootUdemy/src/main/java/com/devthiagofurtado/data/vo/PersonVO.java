package com.devthiagofurtado.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "firstName", "lastName", "address", "gender","enabled"})
public class PersonVO extends ResourceSupport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    private String firstName;

    private String lastName;

    private String address;

    private String gender;

    private Boolean enabled;

}
