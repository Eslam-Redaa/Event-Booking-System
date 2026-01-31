package com.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.demo.Entities.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity , Long> {
	
	Optional<BookingEntity> findByBookingId(String id);
	
	boolean existsByBookingId(String id);
	
}
