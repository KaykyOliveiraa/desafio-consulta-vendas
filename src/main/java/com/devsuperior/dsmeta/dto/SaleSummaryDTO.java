package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public class SaleSummaryDTO {
    public String sellerName;
    public Double total;

    public SaleSummaryDTO(SaleSummaryProjection entity){
        sellerName = entity.getSellerName();
        total = entity.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
