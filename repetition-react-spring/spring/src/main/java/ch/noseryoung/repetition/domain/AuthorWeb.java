package ch.noseryoung.repetition.domain;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/author")
public class AuthorWeb {
    private final AuthorService service;

    public AuthorWeb(AuthorService service) {
        this.service = service;
    }

    @Operation(summary = "Returns the author by id")
    @GetMapping("/{id}")
    public ResponseEntity<Author> find(@PathVariable("id") int id) throws InstanceNotFoundException {
        Author author = service.findById(id);
        return ResponseEntity.ok(author);
    }

    @Operation(summary = "Gives back all entries in authors table")
    @GetMapping
    public ResponseEntity<List<Author>> findAll() {
        List<Author> authors = service.findAll();
        return ResponseEntity.ok().body(authors);
    }

    @Operation(summary = "Creates new entry")
    @PostMapping("/")
    public ResponseEntity<Author> newAuthor(@Valid @RequestBody Author author) throws InstanceAlreadyExistsException {
        Author created = service.create(author);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(created.getAuthorId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Updates or creates Author entry based on existence")
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@Valid @PathVariable int id, @RequestBody Author updatedAuthor) throws InstanceNotFoundException {
        Author updated = service.update(id, updatedAuthor);
        return ResponseEntity.ok().body(updated);
    }

    @Operation(summary = "Delete a specific Author")
    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable("id") int id) throws InstanceNotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(Exception request) {
        return ResponseEntity.status(400).body(request.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(InstanceNotFoundException request) {
        return ResponseEntity.status(404).body(request.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(InstanceAlreadyExistsException request) {
        return ResponseEntity.status(418).body(request.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(MethodArgumentNotValidException request) {
        return ResponseEntity.status(406).body("Arguments not valid");
    }

}