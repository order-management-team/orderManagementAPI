package com.group05.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.group05.model.Product;
import com.group05.model.ProductPrice;
import com.group05.service.use_cases.ProductPriceUseCase;

@Service
public class ProductPriceServiceImpl implements ProductPriceUseCase{

    @Override
    public BigDecimal getCurrentPrice(Product product) {
        ProductPrice active = getActivePrice(product);
        return active != null ? active.getPrice() : null;
    }

    @Override
    public ProductPrice getActivePrice(Product product) {
        if (product.getPrices() == null) return null;
        
        return product.getPrices().stream()
            .filter(ProductPrice::isActivePrice)
            .findFirst()
            .orElse(null);
    }

}
