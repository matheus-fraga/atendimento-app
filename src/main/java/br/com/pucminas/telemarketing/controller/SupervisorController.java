package br.com.pucminas.telemarketing.controller;

import br.com.pucminas.telemarketing.entity.Report;
import br.com.pucminas.telemarketing.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    private ReportService service;

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getReports(){
        var list = service.getReports();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<Report> getReport(@PathVariable(value = "id") Long id){
        var report = service.getReport(id);
        return report.map(
                reportResponse -> ResponseEntity.status(HttpStatus.OK).body(reportResponse))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
