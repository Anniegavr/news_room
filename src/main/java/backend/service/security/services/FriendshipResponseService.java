package backend.service.security.services;

import backend.model.User;
import backend.repository.UserRepository;
import backend.service.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import backend.repository.FriendshipRepository;
import backend.repository.FriendshipRequestsRepository;
import backend.repository.FriendshipResponsesRepository;
import backend.general.payload.response.FriendshipResponse;
import backend.general.payload.response.FriendshipResponseItem;
import backend.model.Friendship;
import backend.model.new_friend.FriendshipRequestAccepted;
import backend.model.new_friend.FriendshipRequestCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.model.new_friend.EStatus.ACCEPT;

@Service
//@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
public class FriendshipResponseService {
    private final FriendshipResponsesRepository friendshipResponsesRepository;

    private final FriendshipRequestsRepository friendshipRequestsRepository;

    private final FriendshipRepository friendshipRepository;

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl UserDetailsService;


    /**
     * @param frResponse containing info about the current user's Id, the friendship's initiator Id, the symmetric key
     * being sent and the ACCEPT or REJECT status of the response
     * @return a List containing info about the newly registered friendship (from an object of type Friendship)
     * if the receiver accepts the friendship, or an object of type FriendshipRequestCreated with the status set to REJECT,
     * in case the receiver rejects the request
     */
    public Object registerResponse(FriendshipRequestAccepted frResponse) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentUserId = UserDetailsService.getUserIdFromToken();

        frResponse.setRequestAccepter(currentUserId);
        frResponse.setAccepterUsername(currentUser);
        Optional<User> initiatorUser = userRepository.findById(frResponse.getFrInitiatorId());
        if (initiatorUser.isPresent() && friendshipRequestsRepository.existsFriendshipRequestCreatedBySenderIdAndAndReceiverId(frResponse.getFrInitiatorId(), frResponse.getRequestAccepter())) {
            frResponse.setInitiatorUsername(initiatorUser.get().getUsername());

            if (frResponse.getStatus().name() == "ACCEPT") {

                FriendshipRequestAccepted friendshipRequestResponse = new FriendshipRequestAccepted(
                        frResponse.getResponseToRequestId(),
                        frResponse.getFrInitiatorId(),
                        frResponse.getInitiatorUsername(),
                        frResponse.getRequestAccepter(),
                        frResponse.getAccepterUsername(),
                        frResponse.getSymmetricKey(),
                        frResponse.getStatus());

                friendshipResponsesRepository.save(friendshipRequestResponse);
                FriendshipRequestCreated answered = friendshipRequestsRepository.findFriendshipRequestCreatedBySenderIdAndReceiverId(frResponse.getFrInitiatorId(), frResponse.getRequestAccepter());
                answered.setStatus(ACCEPT);
                friendshipRequestsRepository.delete(answered);
                Friendship newFriendship = new Friendship(friendshipRequestResponse.getFrInitiatorId(), friendshipRequestResponse.getRequestAccepter());
                friendshipRepository.save(newFriendship);

                return newFriendship;
            } else {
                FriendshipRequestAccepted friendshipRequestResponse = new FriendshipRequestAccepted(
                        frResponse.getResponseToRequestId(),
                        frResponse.getFrInitiatorId(),
                        frResponse.getInitiatorUsername(),
                        frResponse.getRequestAccepter(),
                        frResponse.getAccepterUsername(),
                        frResponse.getSymmetricKey().replaceAll(frResponse.getSymmetricKey(), ""),
                        frResponse.getStatus());
                friendshipResponsesRepository.save(friendshipRequestResponse);
                FriendshipRequestCreated answered = friendshipRequestsRepository.findFriendshipRequestCreatedBySenderIdAndReceiverId(frResponse.getFrInitiatorId(), frResponse.getRequestAccepter());
                friendshipRequestsRepository.delete(answered);
                return friendshipRequestResponse;
            }
        }
        else {
            return "Error: friendship initiator with this id found";
        }
    }

    /**
     * @return a list of FriendshipRequestAccepted pertaining to the current user
     */
    public List getAllByRequests() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Long accepterId = userRepository.findUserByUsername(currentUser).getId();
        List<FriendshipRequestAccepted> requestsList = new ArrayList<>();
        requestsList.addAll(friendshipResponsesRepository.getAllByRequestAccepter(accepterId));
        return requestsList;
    }

    public FriendshipResponse getResponsesToMyRequests() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Long initiatorId = userRepository.findUserByUsername(currentUser).getId();
        List<FriendshipRequestAccepted> responsesList = new ArrayList<>();
        List<FriendshipResponseItem> responsesListItems = new ArrayList<>();
        responsesList.addAll(friendshipResponsesRepository.getAllByFrInitiatorId(initiatorId));
        for (FriendshipRequestAccepted item:
                responsesList) {
            Optional<Friendship> friendshipTry = friendshipRepository.findFriendshipByUserOneIdAndUserTwoId(initiatorId, item.getRequestAccepter());
            if (friendshipTry.isPresent()){
                Friendship friendship = friendshipTry.get();
                item.setAccepterUsername(userRepository.findById(item.getRequestAccepter()).get().getUsername());
                FriendshipResponseItem new_response = new FriendshipResponseItem(friendship, item);
                responsesListItems.add(new_response);
            }
        }
        FriendshipResponse friendshipResponse = new FriendshipResponse();
        friendshipResponse.setResponses(responsesListItems);

        return friendshipResponse;
    }
}
