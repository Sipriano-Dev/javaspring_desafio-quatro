package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.dto.SummarySellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSellerDTO(obj.id, obj.amount, obj.date, obj.seller.name) "
            + "FROM Sale obj "
            + "WHERE obj.date BETWEEN :minDate AND :maxDate "
            + "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<SaleSellerDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    //Necessário apelido se não a projection não se encaixa com mesmo nome do database, retornando null
//    @Query(nativeQuery = true, value = "SELECT tb_seller.name AS sellerName, SUM(tb_sales.amount) AS total FROM tb_seller " +
//            "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id " +
//            "WHERE tb_sales.date BETWEEN :min AND :max " +
//            "GROUP BY tb_seller.name")
//    Page<SummaryProjection> searchSummary(LocalDate min, LocalDate max, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SummarySellerDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :min AND :max " +
            "GROUP BY obj.seller.name")
    Page<SummarySellerDTO> searchSummary(LocalDate min, LocalDate max, Pageable pageable);
}
