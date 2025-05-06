package com.group05.service.use_cases;

import java.math.BigDecimal;

import com.group05.model.Product;
import com.group05.model.ProductPrice;

public interface ProductPriceUseCase {
    ProductPrice getActivePrice(Product product);
    BigDecimal getCurrentPrice(Product product); 
}
