package gr.snika.diorasi.repositories;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.snika.diorasi.entities.Event;
import gr.snika.diorasi.entities.Website;

@Repository
public interface EventRepository extends CrudRepository<Event, UUID> {

	@Query("SELECT e FROM Event e WHERE e.website=:website and e.createdAt between :dateFrom and :dateTo")
	Iterable<Event> findAllByWebsiteAndDateRange(@Param("website") Website website, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
