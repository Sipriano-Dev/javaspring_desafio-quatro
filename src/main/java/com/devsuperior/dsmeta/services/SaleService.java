package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.dto.SummarySellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSellerDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) throws ParseException {

		LocalDate max = "".equals(maxDate) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);
		LocalDate min = "".equals(minDate) ? max.minusYears(1L)
				: LocalDate.parse(minDate);

		Page<SaleSellerDTO> page = repository.searchReport(min, max, name, pageable);
		return page;
	}

	public Page<SummarySellerDTO> searchSummary(String minDate, String maxDate, Pageable pageable) throws ParseException {

		LocalDate max = "".equals(maxDate) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);
		LocalDate min = "".equals(minDate) ? max.minusYears(1L)
				: LocalDate.parse(minDate);

		Page<SummarySellerDTO> page = repository.searchSummary(min, max, pageable).map(SummarySellerDTO::new);

		return page;
	}


}
