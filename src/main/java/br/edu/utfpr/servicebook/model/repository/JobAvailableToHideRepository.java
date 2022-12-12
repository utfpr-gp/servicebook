package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Date;

public interface JobAvailableToHideRepository extends JpaRepository<JobAvailableToHide, JobRequestUserPK> {
    @Query("select j from JobAvailableToHide j where j.date <= ?1")
    List<JobAvailableToHide> findAllByDateLessThan(Date date);

    @Modifying
    @Query("delete from JobAvailableToHide j where j.id = ?1")
    void deleteByJobAvailableId(JobRequestUserPK id);

    @Query("SELECT jr FROM JobRequest jr JOIN JobAvailableToHide ja on ja.jobRequest.id=:id")
    List<JobRequest> findAllByJobRequest(Long id);
}