package backend.model.new_friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class FriendshipRequestAccepted {
    @Id
    @SequenceGenerator(
            name = "request_response_sequence",
            sequenceName = "request_response_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "request_response_sequence"
    )
    private long responseToRequestId;
    private long frInitiatorId;
    private String initiatorUsername;
    private long requestAccepter;
    private String accepterUsername;
//    @Column(columnDefinition="TEXT")
    private String symmetricKey;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EStatus status;

    public FriendshipRequestAccepted(long responseToRequestId, long frInitiatorId, String initiatorUsername, long requestAccepter, String accepterUsername, String symmetricKey, EStatus status) {
        this.responseToRequestId = responseToRequestId;
        this.frInitiatorId = frInitiatorId;
        this.initiatorUsername = initiatorUsername;
        this.requestAccepter = requestAccepter;
        this.accepterUsername = accepterUsername;
        this.symmetricKey = symmetricKey;
        this.status = status;
    }

}
