package com.devthiagofurtado.controller;

import com.devthiagofurtado.data.vo.BooksVO;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//@CrossOrigin
@Api(value = "Book Endpoint", description = "Description for Book", tags = {"Book Endpoint"})
@RestController
@RequestMapping("api/book/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private PagedResourcesAssembler<BooksVO> assembler;


    //@CrossOrigin(origins = {"http://localhost:8080","http://devthiagofurtado.com"})
    @ApiOperation(value = "Busca um registro de Book por Id.")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public BooksVO buscarPorId(@PathVariable(value = "id") Long id) {
        BooksVO booksVO = bookService.findById(id);
        booksVO.add(linkTo(methodOn(BookController.class).buscarPorId(id)).withSelfRel());
        return booksVO;
    }

    //@CrossOrigin(origins = "http://localhost:8080")
    @ApiOperation(value = "Busca todos registros de Book")
    @GetMapping(value = {}, produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> buscarTodos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                               @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC: Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page,limit,Sort.by(sortDirection,"author"));
        Page<BooksVO> booksVO = bookService.findAll(pageable);
        booksVO.forEach(p -> {
            p.add(linkTo(methodOn(BookController.class).buscarPorId(p.getKey())).withSelfRel());
        });
        PagedResources<?> resources = assembler.toResource(booksVO);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    //@CrossOrigin(origins = "http://localhost:8080")
    @ApiOperation(value = "Busca todos registros de Book por author")
    @GetMapping(value = {"/findAllByAuthor"}, produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> buscarTodosPorAuthor(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                               @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                               @RequestParam(value = "author", defaultValue = "") String author
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC: Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page,limit,Sort.by(sortDirection,"author"));
        Page<BooksVO> booksVO = bookService.findAllByAuthor(author ,pageable);
        booksVO.forEach(p -> {
            p.add(linkTo(methodOn(BookController.class).buscarPorId(p.getKey())).withSelfRel());
        });
        return new ResponseEntity<>(assembler.toResource(booksVO), HttpStatus.OK);
    }

    //@CrossOrigin(origins = {"http://localhost:8080","http://devthiagofurtado.com"})
    @ApiOperation(value = "Cria um registro de Book.")
    @PostMapping(value = "/salvar", produces = {"application/json", "application/xml", "application/x-yaml"}
            , consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BooksVO salvar(@RequestBody BooksVO request) {
        BooksVO booksVO = bookService.create(request);
        booksVO.add(linkTo(methodOn(BookController.class).buscarPorId(booksVO.getKey())).withSelfRel());
        return booksVO;
    }

    @ApiOperation(value = "Atualiza um registro de Book.")
    @PutMapping(value = "/atualizar", produces = {"application/json", "application/xml", "application/x-yaml"}
            , consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BooksVO atualizar(@RequestBody BooksVO request) {
        BooksVO booksVO = bookService.update(request);
        booksVO.add(linkTo(methodOn(BookController.class).buscarPorId(booksVO.getKey())).withSelfRel());
        return booksVO;
    }

    @ApiOperation(value = "Deleta um registro de Book.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

}
