package by.mariayuran.my_project_boot.dao;

import by.mariayuran.my_project_boot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {


}
