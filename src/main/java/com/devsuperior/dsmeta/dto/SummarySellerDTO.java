package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SummaryProjection;

public class SummarySellerDTO {

    private String sellerName;
    private Double total;

    public SummarySellerDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummarySellerDTO(SummaryProjection sumaryProjection) {
        sellerName = sumaryProjection.getSellerName();
        total = sumaryProjection.getTotal();
    }

    public String getName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
