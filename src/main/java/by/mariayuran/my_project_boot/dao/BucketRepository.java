package by.mariayuran.my_project_boot.dao;

import by.mariayuran.my_project_boot.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Integer> {
}
