package com.reporter.lab_reporter.controller;

import com.reporter.lab_reporter.repository.ReportRepository;
import com.reporter.lab_reporter.model.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportRepository reportRepository;

    @GetMapping
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportRepository.findById(id)
                .map(report -> ResponseEntity.ok().body(report))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportRepository.save(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        return reportRepository.findById(id)
                .map(report -> {
                    report.setFirstName(reportDetails.getFirstName());
                    report.setSecondName(reportDetails.getSecondName());
                    report.setIdNumber(reportDetails.getIdNumber());
                    report.setDiagnosisTitle(reportDetails.getDiagnosisTitle());
                    report.setDiagnosisDetails(reportDetails.getDiagnosisDetails());
                    report.setPhotoUrl(reportDetails.getPhotoUrl());
                    Report updatedReport = reportRepository.save(report);
                    return ResponseEntity.ok().body(updatedReport);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReport(@PathVariable Long id) {
        return reportRepository.findById(id)
                .map(report -> {
                    reportRepository.delete(report);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
