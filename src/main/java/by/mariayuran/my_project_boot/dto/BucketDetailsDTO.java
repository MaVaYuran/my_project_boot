package by.mariayuran.my_project_boot.dto;

import by.mariayuran.my_project_boot.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailsDTO {
    private String title;
    private Integer productId;
    private BigDecimal price;
    private BigDecimal amount;
    private Double sum;

    public BucketDetailsDTO(Product product) {
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(product.getPrice().toString());
    }
}
