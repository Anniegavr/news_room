package backend.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.service.security.services.FriendshipService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class FriendshipController {
    private static final Logger logger = LoggerFactory.getLogger(FriendshipController.class);

    private final FriendshipService friendshipService;

    /**
     * @return a list containing objects of type Friendship to display all registered friendships
     */
    @GetMapping("/friendships")
    public ResponseEntity<?> getAllFriendships() {
        try {
            List<?> friendships = friendshipService.getAllFriendships();

            if (friendships.isEmpty()) {
                return ResponseEntity.status(204).body("No registered friendships");
            }
            return new ResponseEntity<>(friendships, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Couldn't retrieve information about all registered friendships");
            return ResponseEntity.status(204).body("An unexpected error occurred");
        }
    }


    /**
     * @return a ResponseEntity containing an object of type List with info about the user's friendships
     */
    @GetMapping("/searchUserFriendships")
    public ResponseEntity<?> getFriendshipById() {
        try {
            List<?> fr = friendshipService.getFriendshipById();

            if (fr.isEmpty()) {
                return ResponseEntity.status(204).body("No registered friendships");
            }
            return new ResponseEntity<>(fr, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Couldn't retrieve information about all registered friendships for user + " );
            return ResponseEntity.status(204).body("An unexpected error occurred");
        }

    }


    /**
     * Request to clear the entire list of registered friendships
     * @return a ResponseEntity informing about the delete request status
     */
    @DeleteMapping("/friendships")
    public ResponseEntity<?> deleteAllFriendships() {
        try {
            friendshipService.deleteAllFriendships();
            return ResponseEntity.status(200).body("Friendships deleted");
        } catch (Exception e) {
            logger.error("Couldn't process request to delete all friendships from database");
            return ResponseEntity.status(417).body("An unexpected error occurred");
        }

    }

    /**
     * Function to remove a friendship that belongs to a specific user
     * @param id of the user
     * @return a ResponseEntity informing about the delete request status
     */
    @DeleteMapping("/friendships/{friendshipId}")
    public ResponseEntity<?> deleteFriendship(@PathVariable("friendshipId") long id) {
        try {
            friendshipService.deleteFriendship(id);
            return ResponseEntity.status(200).body("Friendship removed");
        } catch (Exception e) {
            logger.error("Couldn't delete friendship with id "+id);
            return ResponseEntity.status(417).body("An unexpected error occurred");
        }
    }

}
