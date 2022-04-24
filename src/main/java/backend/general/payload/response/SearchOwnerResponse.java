package backend.general.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import backend.general.payload.request.SearchAllOwnersRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchOwnerResponse {
    private  List<SearchAllOwnersRequest> usersFound = new ArrayList<>();

}