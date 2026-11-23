package dev.ninamartins.stockforge;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
 @Test void stockCannotBecomeNegative(){
  Product p=new Product("Notebook","NB-1",new BigDecimal("1500.00"),2);
  assertThrows(IllegalArgumentException.class,()->p.adjust(-3));
  assertEquals(2,p.getQuantity());
 }
 @Test void stockCanIncrease(){
  Product p=new Product("Mouse","M-1",BigDecimal.TEN,3);
  p.adjust(2);assertEquals(5,p.getQuantity());
 }
 @Test void negativePriceIsRejected(){
  assertThrows(IllegalArgumentException.class,()->new Product("Mouse","M-2",new BigDecimal("-1"),1));
 }
}