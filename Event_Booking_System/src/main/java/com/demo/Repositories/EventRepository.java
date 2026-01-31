package com.demo.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.demo.Entities.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
	
	Optional<EventEntity> findByEventId(String id);
	
	boolean existsByEventId(String id);
	
	@Query("select e from EventEntity e WHERE e.status = :status")
	List<EventEntity> findByEventStatus(String status);
	
}
