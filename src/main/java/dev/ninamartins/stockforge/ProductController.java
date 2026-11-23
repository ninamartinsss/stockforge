package dev.ninamartins.stockforge;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
@RestController @RequestMapping("/api/products")
public class ProductController {
 private final ProductRepository repository;
 public ProductController(ProductRepository repository){this.repository=repository;}
 public record NewProduct(@NotBlank String name,@NotBlank String sku,@NotNull @DecimalMin("0.00") BigDecimal price,@Min(0) int quantity){}
 public record UpdateProduct(@NotBlank String name,@NotNull @DecimalMin("0.00") BigDecimal price){}
 public record StockChange(@NotNull Integer delta){}
 @GetMapping public List<Product> list(){return repository.findAll();}
 @GetMapping("/{id}") public Product get(@PathVariable Long id){return find(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional
 public Product create(@Valid @RequestBody NewProduct input){
  if(repository.existsBySku(input.sku()))throw new ResponseStatusException(HttpStatus.CONFLICT,"SKU already exists");
  return repository.save(new Product(input.name(),input.sku(),input.price(),input.quantity()));
 }
 @PutMapping("/{id}") @Transactional public Product update(@PathVariable Long id,@Valid @RequestBody UpdateProduct input){
  Product product=find(id);product.update(input.name(),input.price());return repository.save(product);
 }
 @PatchMapping("/{id}/stock") @Transactional public Product stock(@PathVariable Long id,@Valid @RequestBody StockChange input){
  Product product=find(id);
  try{product.adjust(input.delta());}catch(IllegalArgumentException ex){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());}
  return repository.save(product);
 }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional
 public void delete(@PathVariable Long id){Product product=find(id);repository.delete(product);}
 private Product find(Long id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));}
}