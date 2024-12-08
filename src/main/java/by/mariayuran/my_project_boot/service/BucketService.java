package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.dto.BucketDto;
import by.mariayuran.my_project_boot.model.Bucket;
import by.mariayuran.my_project_boot.model.User;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Integer> productsId);
    void addProducts(Bucket bucket, List<Integer> productsId);
    BucketDto getBucketByUser(String name);
}
