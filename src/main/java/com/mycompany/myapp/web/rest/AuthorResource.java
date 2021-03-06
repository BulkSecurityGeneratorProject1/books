package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Author;
import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.repository.AuthorRepository;
import com.mycompany.myapp.repository.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * REST controller for managing Author.
 */
@RestController
@RequestMapping("/app")
public class AuthorResource {

    private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

    @Inject
    private AuthorRepository authorRepository;
    
    @Inject
    private BookRepository bookRepository;

    /**
     * POST  /rest/authors -> Create a new author.
     */
    @RequestMapping(value = "/rest/authors",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Author author) {
        log.debug("REST request to save Author : {}", author);
        authorRepository.save(author);
    }

    /**
     * GET  /rest/authors -> get all the authors.
     */
    @RequestMapping(value = "/rest/authors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Author> getAll() {
        log.debug("REST request to get all Authors");
        return authorRepository.findEagerRelationships();
    }

    /**
     * GET  /rest/authors/:id -> get the "id" author.
     */
    @RequestMapping(value = "/rest/authors/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Author> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Author : {}", id);
        Author author = authorRepository.findOneEagerRelationships(id);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }       
        
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/authors/:id -> delete the "id" author.
     */
    @RequestMapping(value = "/rest/authors/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Author : {}", id);
        authorRepository.delete(id);
    }
}
