package school.faang.user_service.repository.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import school.faang.user_service.entity.recommendation.Recommendation;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query(nativeQuery = true, value = """
            INSERT INTO recommendation (author_id, receiver_id, content)
            VALUES (?1, ?2, ?3) returning id
            """)
    Long create(long authorId, long receiverId, String content);

    @Query(nativeQuery = true, value = """
            UPDATE recommendation SET content = :content, updated_at = now()
            WHERE author_id = :authorId AND receiver_id = :receiverId
            """)
    @Modifying
    void update(long authorId, long receiverId, String content);

    @Modifying
    int deleteByIdAndAuthor_id(long id, long authorId);

    List<Recommendation> findAllByReceiverId(long receiverId);

    List<Recommendation> findAllByAuthorId(long authorId);

    Optional<Recommendation> findFirstByAuthorIdAndReceiverIdOrderByCreatedAtDesc(long authorId, long receiverId);
}