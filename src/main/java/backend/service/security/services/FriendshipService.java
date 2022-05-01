package backend.service.security.services;

import backend.service.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import backend.repository.FriendshipRepository;
import backend.model.Friendship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FriendshipService {
    private final UserDetailsServiceImpl userDetailsService;
    private final FriendshipRepository friendshipRepository;



    /**
     * @return a list containing objects of type Friendship to display all registered friendships
     */
    public List<?> getAllFriendships() {
        return new ArrayList<>(friendshipRepository.findAll());
    }

    /**
     *
     * @param frTwoId - id of the user one tries to connect with
     * @return a Friendship object
     */
    public Friendship checkForFriendship(Long frTwoId){
        Optional<Friendship> try1 = friendshipRepository.findFriendshipByUserOneIdAndUserTwoId(userDetailsService.getUserIdFromToken(), frTwoId);
        Optional<Friendship> try2 = friendshipRepository.findFriendshipByUserOneIdAndUserTwoId(frTwoId, userDetailsService.getUserIdFromToken());
        if (try1.isPresent()){
            return try1.get();
        } else if (try2.isPresent()){
            return try2.get();
        } else{
            return null;
        }
    }

    /**
     * @return a ResponseEntity containing an object of type List with info about the user's friendships
     */
    public List<?> getFriendshipById() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userDetailsService.getUserIdFromToken();

        return friendshipRepository.findFriendshipsByUserOneIdEqualsOrUserTwoIdEquals(userId, userId);
    }


    /**
     * Request to clear the entire list of registered friendships
     * @return a ResponseEntity informing about the delete request status
     */
    public void deleteAllFriendships() {
        friendshipRepository.deleteAll();
    }

    /**
     * Function to remove a friendship that belongs to a specific user
     * @param id of the user
     * @return a ResponseEntity informing about the delete request status
     */
    public void deleteFriendship(Long id) {
        friendshipRepository.deleteById(id);
    }

}
