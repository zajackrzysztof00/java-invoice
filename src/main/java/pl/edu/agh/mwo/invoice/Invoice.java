package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    int invoiceNumber;
    private final Collection<Product> products = new ArrayList<>();
    BigDecimal subtotal;
    BigDecimal tax;
    BigDecimal total;

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

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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
        this.total = BigDecimal.ZERO;
        BigDecimal subtotal = this.getSubtotal();
        BigDecimal tax = this.getTax();
        this.total = subtotal.add(tax);
        return this.total;
    }

    public String printInvoice() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice number: " + invoiceNumber + "\n");
        sb.append("Products:\n");

        HashMap countedProducts = new HashMap<Product, Integer>();
        for (Product product : this.products) {
            if (countedProducts.containsKey(product)) {
                int count = (Integer) countedProducts.get(product);
                countedProducts.replace(product, count + 1);
            } else {
                countedProducts.put(product, 1);
            }
        }
        for (Object p: countedProducts.keySet()) {
            Product product = (Product) p;
            int count = (Integer) countedProducts.get(p);
            sb.append("\t" + product.getName() + ": " + count + "\n");
        }
        sb.append("Subtotal: " + subtotal + "\n");
        sb.append("Tax: " + tax + "\n");
        sb.append("Total: " + total + "\n");
        return sb.toString();
    }
}
