package br.com.pucminas.telemarketing.service;

import br.com.pucminas.telemarketing.entity.Report;
import br.com.pucminas.telemarketing.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportService {

    @Autowired
    private ReportRepository repository;

    public List<Report> getReports() {
        return repository.findAll();
    }

    public Optional<Report> getReport(Long id) {
        return repository.findReportById(id);
    }
}
