package com.epam.training.test;

import com.epam.training.data.Category;
import com.epam.training.data.Product;
import com.epam.training.repositories.interfaces.ICategoryRepository;
import com.epam.training.repositories.interfaces.IProductRepository;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

@TestComponent
public class TestInitializer {

    public static final int INIT_COUNT = 100;

    private final ICategoryRepository categoryRepository;

    private final IProductRepository productRepository;

    @Autowired
    public TestInitializer(final ICategoryRepository categoryRepository,
                           final IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        List<Product> products = TestDataGeneratorUtil.objects(Product.class, INIT_COUNT);
        products.forEach(
            product -> {
                categoryRepository.save(product.getCategory());
                productRepository.save(product);
            }
        );
        RestAssured.config = newConfig().jsonConfig(jsonConfig().numberReturnType(
                JsonPathConfig.NumberReturnType.DOUBLE));

    }

    public Product addNewProduct() {
        var product = TestDataGeneratorUtil.nextObject(Product.class);
        categoryRepository.save(product.getCategory());
        return productRepository.save(product);
    }


    public void deleteProduct(final Product product) {
        productRepository.delete(product);
        categoryRepository.delete(product.getCategory());
    }


    public Category addNewCategory() {
        var category = TestDataGeneratorUtil.nextObject(Category.class);
        return categoryRepository.save(category);
    }

    public void deleteCategory(final Category category) {
        categoryRepository.delete(category);
    }

}
