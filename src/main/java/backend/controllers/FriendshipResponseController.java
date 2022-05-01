package backend.controllers;

import backend.service.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.model.new_friend.FriendshipRequestAccepted;
import backend.service.security.services.FriendshipResponseService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class FriendshipResponseController{
    private static final Logger logger = LoggerFactory.getLogger(FriendshipResponseController.class);

    private final FriendshipResponseService friendshipResponseService;
    private final UserDetailsServiceImpl userDetailsService;


    /**
     * @param frResponse containing info about the current user's Id, the friendship's initiator Id, the symmetric key
     * being sent and the ACCEPT or REJECT status of the response
     * @return a ResponseEntity containing info about the newly registered friendship (from an object of type Friendship)
     * if the receiver accepts the friendship, or an object of type FriendshipRequestCreated with the status set to REJECT,
     * in case the receiver rejects the request
     */
    @PostMapping("/answer_new_fr_request")

    public ResponseEntity<?> registerResponse(@RequestBody FriendshipRequestAccepted frResponse) {
        if (friendshipResponseService.registerResponse(frResponse).getClass().getName().equals("Friendship")) {
            return ResponseEntity.ok(friendshipResponseService.registerResponse(frResponse));
        }
        else {
            return ResponseEntity.status(404).body("Error: friendship initiator with this id found");
        }
    }

    /**
     * @return a list of FriendshipRequestAccepted pertaining to the current user
     */
    @GetMapping("/answered_fr_requests")
    public ResponseEntity<?> getAllByRequests() {
        try {
            if (friendshipResponseService.getAllByRequests().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(friendshipResponseService.getAllByRequests(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Couldn't retrieve answered friendship requests for user "+userDetailsService.getUserIdFromToken());
            return ResponseEntity.status(204).body("An unexpected error occurred");
        }
    }

    @GetMapping("/responses_to_my_requests")
    public ResponseEntity<?> getResponsesToMyRequests() {
        try {
            return new ResponseEntity<>(friendshipResponseService.getResponsesToMyRequests(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Couldn't obtain responses to friendship requests for user "+ userDetailsService.getUserIdFromToken());
            return ( ResponseEntity.status(204).body("An unexpected error occurred"));
        }
    }
}

