package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.dao.ProductRepository;
import by.mariayuran.my_project_boot.dto.ProductDto;
import by.mariayuran.my_project_boot.mapper.ProductMapper;
import by.mariayuran.my_project_boot.model.Bucket;
import by.mariayuran.my_project_boot.model.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper = ProductMapper.MAPPER;

    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<ProductDto> getAll() {

        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public void addToUserBucket(Integer productId, String username) {
        User user = userService.findByName(username);
        if(user == null) {
            throw  new RuntimeException("user not found" + username);
        }
        Bucket bucket = user.getBucket();

        if(bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        }else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }

    }
}
