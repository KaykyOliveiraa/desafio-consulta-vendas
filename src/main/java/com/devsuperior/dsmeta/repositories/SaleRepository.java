package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_seller.name AS sellerName, SUM(tb_sales.amount) AS total " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name" )
    List<SaleSummaryProjection> searchSummary(LocalDate minDate, LocalDate maxDate);

    @Query(value = "SELECT obj " +
            "FROM Sale obj " +
            "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
            "AND obj.date BETWEEN :minDate AND :maxDate")
    Page<Sale> searchReport(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);

}
