package com.devthiagofurtado.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "launch_date", nullable = false, length = 80)
    private Date lauchDate;

    @Column(nullable = false, length = 100)
    private Double price;

    @Column(nullable = false, length = 6)
    private String title;

}
