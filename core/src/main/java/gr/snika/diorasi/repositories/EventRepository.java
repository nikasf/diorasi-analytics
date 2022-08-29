package gr.snika.diorasi.repositories;

import java.util.Date;
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
	
	@Query(value="SELECT extract(hour from event0.created_at) || ' ' as timefield, count(*) as numberofevents FROM event event0 WHERE event0.website_id=:websiteId and event0.created_at between :dateFrom and :dateTo group by 1 order by 1 desc", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByHour(@Param("websiteId") UUID websiteId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

	
	@Query(value="SELECT CAST(event0.created_at AS DATE) || ' ' as timefield, count(*) as numberofevents FROM event event0 WHERE event0.website_id=:websiteId and event0.created_at between :dateFrom and :dateTo group by 1 order by 1 desc", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByDay(@Param("websiteId") UUID websiteId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	
	@Query(value="SELECT concat(extract(year from event0.created_at), '-', extract(month from event0.created_at)) as timefield, count(*) as numberofevents FROM event event0 WHERE event0.website_id=:websiteId and event0.created_at between :dateFrom and :dateTo group by 1 order by 1 desc", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByMonth(@Param("websiteId") UUID websiteId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	
	@Query(value="SELECT extract(year from event0.created_at) || ' ' as timefield, count(1) as numberofevents FROM event event0 WHERE event0.website_id=:websiteId and event0.created_at between :dateFrom and :dateTo group by 1 order by 1 desc", 
			nativeQuery=true)
	List<EventsCountDTO> findAllByYear(@Param("websiteId") UUID websiteId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
