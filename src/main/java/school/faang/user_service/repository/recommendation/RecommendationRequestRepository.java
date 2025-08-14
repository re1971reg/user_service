package school.faang.user_service.repository.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.exception.EntityNotFoundException;

import java.util.Optional;

public interface RecommendationRequestRepository extends JpaRepository<RecommendationRequest, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM recommendation_request
            WHERE requester_id = ?1 AND receiver_id = ?2
            ORDER BY created_at DESC
            LIMIT 1
            """)
    Optional<RecommendationRequest> findLatestPendingRequest(long requesterId, long receiverId);

    default RecommendationRequest getByIdOrThrow(long requestId) {
        return findById(requestId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Recommendation request %d not found", requestId))
        );
    }
}