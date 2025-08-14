package school.faang.user_service.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import school.faang.user_service.entity.user.User;

import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            insert into subscription (follower_id, followee_id) values (:followerId, :followeeId)
            """)
    @Modifying
    void followUser(long followerId, long followeeId);

    @Query(nativeQuery = true, value = """
            delete from subscription where follower_id = :followerId and followee_id = :followeeId
            """)
    @Modifying
    void unfollowUser(long followerId, long followeeId);

    @Query(nativeQuery = true, value = """
            select exists(select 1 from subscription where follower_id = :followerId and followee_id = :followeeId)
            """)
    boolean existsByFollowerIdAndFolloweeId(long followerId, long followeeId);

    @Query(nativeQuery = true, value = "select count(id) from subscription where followee_id = :followeeId")
    int findFollowersAmountByFolloweeId(long followeeId);

    @Query(nativeQuery = true, value = "select count(id) from subscription where follower_id = :followerId")
    int findFolloweesAmountByFollowerId(long followerId);

    @Query(nativeQuery = true, value = """
            select u.* from users as u
            join subscription as subs on u.id = subs.follower_id
            where subs.followee_id = :followeeId
            """)
    Stream<User> findByFolloweeId(long followeeId);

    @Query(nativeQuery = true, value = """
            select u.* from users as u
            join subscription as subs on u.id = subs.followee_id
            where subs.follower_id = :followerId
            """)
    Stream<User> findByFollowerId(long followerId);
}