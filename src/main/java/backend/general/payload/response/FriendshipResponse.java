package backend.general.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipResponse {
    private  List<FriendshipResponseItem> responses = new ArrayList<>();
}
