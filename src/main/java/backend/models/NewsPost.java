//package backend.models;
//
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table
//@ToString
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class NewsPost {
//    @Id
//    @SequenceGenerator(
//            name = "news_post",
//            sequenceName = "news_post",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "news_post"
//    )
//    private Long newsPostId;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "news_post_types", joinColumns = @JoinColumn(name = "news_post_id"), inverseJoinColumns = @JoinColumn(name = "news_post_types_id"))
//    private Set<NewsPostType> newsPostType = new HashSet<>();
//    private String newsTitle;
//    private String newsText;
//}
