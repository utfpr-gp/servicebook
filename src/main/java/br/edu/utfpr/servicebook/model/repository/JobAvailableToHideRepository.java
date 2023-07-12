package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


public interface JobAvailableToHideRepository extends JpaRepository<JobAvailableToHide, JobRequestUserPK> {

    @Query("select j from JobAvailableToHide j where j.date <= ?1")
    List<JobAvailableToHide> findAllByDateLessThan(LocalDate date);

    @Query("SELECT jr FROM JobRequest jr JOIN JobAvailableToHide ja on ja.jobRequest.id=:id")
    List<JobRequest> findAllByJobRequest(Long id);
}