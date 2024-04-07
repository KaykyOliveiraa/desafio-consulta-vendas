package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> searchSummary(String dateMin, String dateMax){
		LocalDate maxDate = dateMin.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(dateMax);
		LocalDate minDate = dateMax.equals("") ? maxDate.minusYears(1L) : LocalDate.parse(dateMin);
		List<SaleSummaryProjection> result = repository.searchSummary(minDate, maxDate);
		return result.stream().map(x -> new SaleSummaryDTO(x)).collect(Collectors.toList());
	}

	public Page<SaleReportDTO> searchReport(String name, String dateMin, String dateMax, Pageable pageable){
		LocalDate maxDate = dateMin.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(dateMax);
		LocalDate minDate = dateMax.equals("") ? maxDate.minusYears(1L) : LocalDate.parse(dateMin);
		Page<Sale> result = repository.searchReport(name, minDate, maxDate, pageable);
		return result.map(x -> new SaleReportDTO(x));

	}
}
