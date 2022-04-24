package backend.general.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import backend.models.new_friend.FriendshipRequestCreated;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipRequests {
    private  List<FriendshipRequestCreated> notificationsList;

}
