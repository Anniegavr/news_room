package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import backend.model.new_friend.FriendshipRequestAccepted;

import java.util.List;

@Repository
@Transactional
public interface FriendshipResponsesRepository extends JpaRepository<FriendshipRequestAccepted, Long> {
//    List<FriendshipRequestAccepted> findFriendshipRequestAcceptedByRequestAccepter(Long accepterId);

    List<FriendshipRequestAccepted> getAllByRequestAccepter(long requestAccepter);

    List<FriendshipRequestAccepted> getAllByFrInitiatorId(long frInitiatorId);
}
