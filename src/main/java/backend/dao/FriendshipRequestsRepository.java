package backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import backend.models.new_friend.FriendshipRequestCreated;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FriendshipRequestsRepository extends JpaRepository<FriendshipRequestCreated, Long>{
    List<FriendshipRequestCreated> findBySenderId(long senderId);

    List<FriendshipRequestCreated> findFriendshipRequestCreatedByReceiverId(Long receiverId);
    FriendshipRequestCreated findFriendshipRequestCreatedBySenderIdAndReceiverId(Long senderId, Long receiverId);
    boolean existsFriendshipRequestCreatedBySenderIdAndAndReceiverId(Long senderId, Long receiverId);

    void deleteFriendshipRequestCreatedBySenderIdAndReceiverId(long senderId, long receiverId);

}
