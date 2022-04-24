package backend.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Table
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsPost {
    @SequenceGenerator(
            name = "news_post",
            sequenceName = "news_post",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "news_post"
    )
    @Id
    private Long newsPostId;
    private NewsType newsPostType;
    private String newsTitle;
    private String newsText;


}
