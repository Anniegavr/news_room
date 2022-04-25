//package backend.models;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "news_post_types")
//@NoArgsConstructor
//@Getter
//@Setter
//public class NewsPostType {
//    @Id
//    @SequenceGenerator(
//            name = "news_post_types_sequence",
//            sequenceName = "news_post_types_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "news_post_types_sequence"
//    )
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 20)
//    private ENewsType name;
//
//}
