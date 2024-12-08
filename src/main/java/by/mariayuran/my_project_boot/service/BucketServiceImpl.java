package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.dao.BucketRepository;
import by.mariayuran.my_project_boot.dao.ProductRepository;
import by.mariayuran.my_project_boot.dto.BucketDetailsDTO;
import by.mariayuran.my_project_boot.dto.BucketDto;
import by.mariayuran.my_project_boot.model.Bucket;
import by.mariayuran.my_project_boot.model.Product;
import by.mariayuran.my_project_boot.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Integer> productsId) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productsList = getCollectRefProductsByIds(productsId);
        bucket.setProducts(productsList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Integer> productsId) {
       return productsId.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Bucket bucket, List<Integer> productsId) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productsId));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDto getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user== null || user.getBucket() == null) {
            return new BucketDto();
        }
        BucketDto bucketDto = new BucketDto();
        Map<Integer, BucketDetailsDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for(Product product: products) {
            BucketDetailsDTO detail = mapByProductId.get(product.getId());
            if(detail==null) {
                mapByProductId.put(product.getId(), new BucketDetailsDTO(product));
            }else{
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDto.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDto.aggregate();
        return bucketDto;
    }
}
