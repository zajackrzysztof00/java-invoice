package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private final Collection<Product> products = new ArrayList<>();
    BigDecimal subtotal = BigDecimal.ZERO;
    BigDecimal tax = BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }
        for (int i = 0; i < quantity; i++) {
            addProduct(product);
        }
    }

    public BigDecimal getSubtotal() {
        this.subtotal = BigDecimal.ZERO;
        for (Product product : this.products) {
            this.subtotal = this.subtotal.add(product.getPrice());
        }
        return this.subtotal;
    }

    public BigDecimal getTax() {
        this.tax = BigDecimal.ZERO;
        for (Product product : this.products) {
            this.tax = this.tax.add(product.getPrice().multiply(product.getTaxPercent()));
        }
        return this.tax;
    }

    public BigDecimal getTotal() {
        BigDecimal subtotal = this.getSubtotal();
        BigDecimal tax = this.getTax();
        this.total = subtotal.add(tax);
        return this.total;
    }
}
