package ke.unify.ecommerce.product;

import jakarta.persistence.EntityNotFoundException;
import ke.unify.ecommerce.product.exception.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Long createProduct(ProductRequest request) {

        var product = productRepository.save(productMapper.toProduct(request));
        return product.getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

        var productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = productRepository.findAllByIdInOrderByIdDesc(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exists");
        }

        var storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

        List<ProductPurchaseResponse> purchaseResponses = new ArrayList<>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var requestedProduct = storedRequest.get(i);

            if (product.getAvailableQuantity() < requestedProduct.quantity()) {
                throw new ProductPurchaseException("Insufficient quantity for product with name:: "+ product.getName());
            }

            var newAvailableQty =  product.getAvailableQuantity() - requestedProduct.quantity();
            product.setAvailableQuantity(newAvailableQty);
            productRepository.save(product);
            purchaseResponses.add(productMapper.productToPurchasedProductResponse(product, requestedProduct.quantity()));
        }


        return purchaseResponses;
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId).map(productMapper::fromProduct).orElseThrow(()-> new EntityNotFoundException("Product not found with the ID:: "+ productId));
    }

    public List<ProductResponse> findAll() {

        return productRepository.findAll().stream().map(productMapper::fromProduct).collect(Collectors.toList());
    }
}
