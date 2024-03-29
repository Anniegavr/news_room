package backend.general.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipRequest {
    private long createdRequestId;
    private long senderId;
    private String username;
    private long receiverId;
    private String publicKey;
}
