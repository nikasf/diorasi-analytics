package gr.snika.diorasi.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.dto.EventsCountDTO;
import gr.snika.diorasi.entities.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, UUID> {
	
	@Query(value="SELECT extract(hour FROM createdDT) || ' ' AS timefield, coalesce(e.ct, 0) AS numberofevents FROM generate_series(cast(:dateFrom AS TIMESTAMP), cast(:dateTo AS TIMESTAMP), cast('1 hour' AS interval)) createdDT LEFT JOIN (SELECT extract(hour FROM event0.created_at) created_dt, count(*) ct FROM event event0 WHERE event0.website_id=:websiteId AND event0.created_at BETWEEN :dateFrom AND :dateTo GROUP BY 1 ORDER BY 1 ASC) e ON e.created_dt = extract(hour FROM createdDT)", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByHour(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

	
	@Query(value="SELECT cast(createdDT AS DATE) || ' ' AS timefield, coalesce(e.ct,0) AS numberofevents FROM generate_series(cast(:dateFrom as DATE), cast(:dateTo as DATE), cast('1 day' as interval)) createdDT LEFT JOIN (SELECT cast(event0.created_at AS DATE) created_dt, count(*) ct FROM event event0 WHERE event0.website_id=:websiteId GROUP BY 1) e ON e.created_dt = createdDT", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByDay(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
	
	
	@Query(value="SELECT concat(extract(year FROM createdDT), '-', extract(month FROM createdDT)) AS timefield, coalesce(e.ct,0) AS numberofevents FROM generate_series(cast(:dateFrom AS DATE), cast(:dateTo AS DATE), cast('1 month' AS interval)) createdDT LEFT JOIN (SELECT concat(extract(year FROM event0.created_at), '-', extract(month FROM event0.created_at)) AS created_dt, count(*) ct FROM event event0 WHERE event0.website_id=:websiteId GROUP BY 1 ORDER BY 1 ASC) e ON e.created_dt = concat(extract(year FROM createdDT), '-', extract(month FROM createdDT))", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByMonth(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
	
	
	@Query(value="SELECT extract(year FROM createdDT) || ' ' AS timefield, coalesce(e.ct, 0) AS numberofevents FROM generate_series(cast(:dateFrom AS DATE), cast(:dateTo AS DATE), cast('1 year' AS interval)) createdDT LEFT JOIN (SELECT extract(year FROM event0.created_at) created_dt, count(*) ct FROM event event0 WHERE event0.website_id=:websiteId GROUP BY 1 ORDER BY 1 ASC) e ON e.created_dt = extract(year FROM createdDT)", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByYear(@Param("websiteId") UUID websiteId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
	
}
