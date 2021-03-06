package mk.ukim.finki.dick.prezemiakcijabackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.CommentDto;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Forum forum;

    private String submitterEmail;

    private String text;

//    private int likes;
//
//    private int dislikes;

    public Comment(Forum forum, String submitterEmail, String text) {
        this.forum = forum;
        this.submitterEmail = submitterEmail;
        this.text = text;
//        this.likes = 0;
//        this.dislikes = 0;
    }

    public void update(CommentDto commentDto) {
        this.text = commentDto.getText();
    }

//    public void like() {
//        this.likes += 1;
//    }
//
//    public void dislike() {
//        this.dislikes += 1;
//    }
}
