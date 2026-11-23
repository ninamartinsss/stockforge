package dev.ninamartins.stockforge;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name="products",uniqueConstraints=@UniqueConstraint(columnNames="sku"))
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String sku;
 @Column(nullable=false,precision=15,scale=2) private BigDecimal price;
 @Column(nullable=false) private int quantity;
 protected Product(){}
 public Product(String name,String sku,BigDecimal price,int quantity) {
  if(price==null||price.signum()<0||quantity<0)throw new IllegalArgumentException("Invalid product values");
  this.name=name;this.sku=sku;this.price=price;this.quantity=quantity;
 }
 public Long getId(){return id;}
 public String getName(){return name;}
 public String getSku(){return sku;}
 public BigDecimal getPrice(){return price;}
 public int getQuantity(){return quantity;}
 public void update(String name,BigDecimal price){if(price==null||price.signum()<0)throw new IllegalArgumentException("Invalid price");this.name=name;this.price=price;}
 public void adjust(int delta){long value=(long)quantity+delta;if(value<0||value>Integer.MAX_VALUE)throw new IllegalArgumentException("Insufficient stock or overflow");quantity=(int)value;}
}