package ch.noseryoung.repetition.domain;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class AuthorService {

    static String message ="There is no author with id: ";
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        log.info("Create repository for communication");
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        log.info("Get everything in author table");
        List<Author> authorList = new ArrayList<>();
        Iterable<Author> authors = authorRepository.findAll();
        authors.forEach(authorList::add);
        return authorList;
    }

    public Author create(Author author) throws InstanceAlreadyExistsException {
        log.info("creating new Author");
        if (authorRepository.existsById(author.getAuthorId())){
            throw new InstanceAlreadyExistsException("author with ID: " + author.getAuthorId() + " already exists");
        }
        log.info("created {} with ID {}",author.getName() ,author.getAuthorId());
        return authorRepository.save(author);
    }

    public Author findById(int id) throws InstanceNotFoundException {
        log.info("Searching for Author with ID {}", id);
        return authorRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(message + id));
    }

    public void delete(int id) throws InstanceNotFoundException {
        log.info("Deleting entry");
        authorRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException(message + id));
        authorRepository.deleteById(id);
        log.info("Deleted Author with ID {}", id);

    }

    public Author update(int id, Author newAuthor) throws InstanceNotFoundException {
        log.info("Updating data");
        if (authorRepository.existsById(id)) {
            return authorRepository.findById(id).map(depAuthor -> {
                depAuthor.setName(newAuthor.getName());
                depAuthor.setBirthday(newAuthor.getBirthday());
                depAuthor.setPp_url(newAuthor.getPp_url());
                log.info("{} was updated", depAuthor);
                return authorRepository.save(depAuthor);
            }).orElseThrow(() -> new InstanceNotFoundException(message + id));
        } else {
            log.warn("Creating new entry as it doesnt exist");
            newAuthor.setName(newAuthor.getName());
            newAuthor.setBirthday(newAuthor.getBirthday());
            newAuthor.setPp_url(newAuthor.getPp_url());
            log.info("New author, {} was created with ID: {}", newAuthor.getName(), newAuthor.getAuthorId());
            return authorRepository.save(newAuthor);
        }
    }
}