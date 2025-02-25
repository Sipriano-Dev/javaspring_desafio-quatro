package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
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
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleMinDTO> searchAll(String minDate, String maxDate, String name, Pageable pageable) throws ParseException {
		LocalDate minDate2;
		LocalDate maxDate2;
		if ("".equals(maxDate)) {
			maxDate2 = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			maxDate2 = LocalDate.parse(maxDate, format);
		}
		if ("".equals(minDate)) {
			minDate2 = maxDate2.minusYears(1L);
		} else {
			minDate2 = LocalDate.parse(minDate, format);
		}
		Page<SaleMinDTO> page = repository.searchAll(minDate2, maxDate2, name, pageable);
		return page;
	}

}
