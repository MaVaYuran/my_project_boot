package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    void addToUserBucket(Integer productId, String username);
}
