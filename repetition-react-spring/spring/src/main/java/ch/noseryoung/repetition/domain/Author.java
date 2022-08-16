package ch.noseryoung.repetition.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @NotNull
    @SequenceGenerator(name = "Authorgenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int authorId;

    @Column (name = "name")
    @Size(min = 2, max = 100)
    @NotEmpty
    private String name;

    @Column (name = "birthday")
    @NotEmpty
    private String birthday;

    @Column (name = "profilePictureURL")
    @NotEmpty
    private String pp_url;

}
