package backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "news_posts")
public class Newspost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creator;
    private Boolean privateNews;
    private Boolean protectedNews;
    private String title;
    private String text;

}
