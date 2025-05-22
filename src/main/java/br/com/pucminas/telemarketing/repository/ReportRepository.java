package br.com.pucminas.telemarketing.repository;

import br.com.pucminas.telemarketing.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Optional<Report> findReportById(Long id);
}
