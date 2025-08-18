package com.mydashboard.repo;

import com.mydashboard.entity.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDetailsRepository extends JpaRepository<EmailDetails, Long> {
    // Additional query methods can be added if needed, e.g. findByCompanyName or findByGotReply
}
