package backend.controllers;

import backend.service.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.general.payload.response.FriendshipRequests;
import backend.model.new_friend.FriendshipRequestCreated;
import backend.service.security.services.FriendshipRequestsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class FriendshipRequestsController {
    private static final Logger logger = LoggerFactory.getLogger(FriendshipRequestsController.class);

    private final FriendshipRequestsService friendshipRequestsService;
    private final UserDetailsServiceImpl userDetailsService;


    /**
     * @param frRequest - contains the senderID - automatically retrieved from JWT, the receiverID and the publicKey
     * @return a ResponseEntity containing info about an object of type FriendshipRequestCreated with
     * the senderID, the senderUsername, the receiverId and the publicKey
     */
    @PostMapping("/send_new_fr_request")

    public ResponseEntity<?> registerRequest(@RequestBody FriendshipRequestCreated frRequest) {
        if (friendshipRequestsService.registerRequest(frRequest).equals("Request has already been sent")) {
            logger.warn("Sending friendship request multiple times: user "+frRequest.getSenderId()+" to user "+frRequest.getReceiverId());
            return new ResponseEntity<>(new FriendshipRequestCreated(), HttpStatus.NOT_ACCEPTABLE);
        } else if (friendshipRequestsService.registerRequest(frRequest).equals("Request has already been sent by the other user")) {
            logger.warn("The other user has already sent a request to user "+frRequest.getSenderId());
            return new ResponseEntity<>(new FriendshipRequestCreated(), HttpStatus.CONFLICT);
        } else if (friendshipRequestsService.registerRequest(frRequest).equals("Sending request to oneself")){
            logger.warn("Trying to send friendship request to oneself: user "+frRequest.getSenderId());
            return new ResponseEntity<>(new FriendshipRequestCreated(), HttpStatus.METHOD_NOT_ALLOWED);
        }else {
            return ResponseEntity.ok(friendshipRequestsService.registerRequest(frRequest));
        }

    }

    /**
     * @return a ResponseEntity with a list of objects of type FriendshipRequestCreated
     * displaying the friendship requests sent by the current user
     */
    @GetMapping("/sent_fr_requests")
    public ResponseEntity<?> getAllSentFrRequests() {
        try {
            if (friendshipRequestsService.getAllSentFrRequests().isEmpty()) {
                return ResponseEntity.status(204).body("You have no pending requests");
            }
            return new ResponseEntity<>(friendshipRequestsService.getAllSentFrRequests(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("No content was found for friendship requests sent by user "+ userDetailsService.getUserIdFromToken());
            return ResponseEntity.badRequest().body("An unexpected error occurred");
        }
    }

    /**
     * @return a ResponseEntity with a list FriendshipRequests containing objects of type FriendshipRequestCreated
     * displaying info about friendship requests received by the current user
     */
    @GetMapping("/received_fr_requests")
    public ResponseEntity<FriendshipRequests> getAllReceivedFrRequests() {
        if (friendshipRequestsService.getAllSentFrRequests().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(friendshipRequestsService.getAllReceivedFrRequests(), HttpStatus.OK);
    }

}
