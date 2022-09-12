package gr.snika.diorasi.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.dto.EventsCountDTO;
import gr.snika.diorasi.entities.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {

	@Query(value="SELECT s.device AS filterfield, count(*) as numberofevents FROM session s WHERE s.website_id=:websiteId AND s.created_at BETWEEN :dateFrom AND :dateTo GROUP BY 1 ORDER BY numberofevents DESC", nativeQuery=true)
	List<EventsCountDTO> findNoOfEventsPerDevice(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

	@Query(value="SELECT s.browser AS filterfield, count(*) as numberofevents FROM session s WHERE s.website_id=:websiteId AND s.created_at BETWEEN :dateFrom AND :dateTo GROUP BY 1 ORDER BY numberofevents DESC", nativeQuery=true)
	List<EventsCountDTO> findNoOfEventsPerBrowser(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

	@Query(value="SELECT s.os AS filterfield, count(*) as numberofevents FROM session s WHERE s.website_id=:websiteId AND s.created_at BETWEEN :dateFrom AND :dateTo GROUP BY 1 ORDER BY numberofevents DESC", nativeQuery=true)
	List<EventsCountDTO> findNoOfEventsPerOS(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

	@Query(value="SELECT s.country AS filterfield, count(*) as numberofevents FROM session s WHERE s.website_id=:websiteId AND s.created_at BETWEEN :dateFrom AND :dateTo GROUP BY 1 ORDER BY numberofevents DESC", nativeQuery=true)
	List<EventsCountDTO> findNoOfEventsPerCountry(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

	@Query(value="SELECT s.resolution AS filterfield, count(*) as numberofevents FROM session s WHERE s.website_id=:websiteId AND s.created_at BETWEEN :dateFrom AND :dateTo GROUP BY 1 ORDER BY numberofevents DESC", nativeQuery=true)
	List<EventsCountDTO> findNoOfEventsPerResolution(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

}
