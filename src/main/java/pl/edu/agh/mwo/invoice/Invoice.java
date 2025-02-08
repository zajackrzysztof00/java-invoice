package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products = new ArrayList<Product>();
    BigDecimal subtotal = BigDecimal.ZERO;
    BigDecimal tax = BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        for (int i = 0; i < quantity; i++) {
            addProduct(product);
        }
    }

    public BigDecimal getSubtotal() {
        for (Product product : this.products) {
            this.subtotal.add(product.getPrice());
        }
        return this.subtotal;
    }

    public BigDecimal getTax() {
        for (Product product : this.products) {
            this.tax.add(product.getPrice().multiply(product.getTaxPercent()));
        }
        return this.tax;
    }

    public BigDecimal getTotal() {
        return this.subtotal.add(this.tax);
    }
}
