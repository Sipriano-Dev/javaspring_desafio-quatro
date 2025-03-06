package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.dto.SummarySellerDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleSellerDTO>> getReport(
			@RequestParam(name= "minDate", defaultValue = "") String minDate,
			@RequestParam(name= "maxDate", defaultValue = "") String maxDate,
			@RequestParam(name= "name", defaultValue = "") String name,
			Pageable pageable) throws ParseException {
		Page<SaleSellerDTO> result = service.searchReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SummarySellerDTO>> getSummary(
			@RequestParam(name= "minDate", defaultValue = "") String minDate,
			@RequestParam(name= "maxDate", defaultValue = "") String maxDate,
			Pageable pageable) throws ParseException {
		Page<SummarySellerDTO> result = service.searchSummary(minDate, maxDate, pageable);

		return ResponseEntity.ok(result);
	}
}
