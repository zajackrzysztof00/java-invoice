package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    int invoiceNumber = 1;
    private final HashMap<Product, Integer> products = new HashMap<Product, Integer>();
    BigDecimal subtotal;
    BigDecimal tax;
    BigDecimal total;

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (products.containsKey(product)) {
            int count = (Integer) products.get(product);
            products.replace(product, count + 1);
        } else {
            products.put(product, 1);
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }
        if (products.containsKey(product)) {
            int count = (Integer) products.get(product);
            products.replace(product, count + quantity);
        } else {
            products.put(product, quantity);
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
        for (Object p: products.keySet()) {
            Product product = (Product) p;
            int quantity = (Integer) products.get(product);
            product.getPrice().multiply(new BigDecimal(quantity));
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));
            this.subtotal = this.subtotal.add(subtotal);
        }
        return this.subtotal;
    }

    public BigDecimal getTax() {
        this.tax = BigDecimal.ZERO;
        for (Object p: products.keySet()) {
            Product product = (Product) p;
            int quantity = (Integer) products.get(product);
            BigDecimal tax = product.getPrice().multiply(product
                    .getTaxPercent().multiply(new BigDecimal(quantity)));
            this.tax = this.tax.add(tax);
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

        for (Object p: products.keySet()) {
            Product product = (Product) p;
            int count = (Integer) products.get(p);
            sb.append("\t" + product.getName() + ": " + count + "\n");
        }
        sb.append("Subtotal: " + subtotal + "\n");
        sb.append("Tax: " + tax + "\n");
        sb.append("Total: " + total + "\n");
        return sb.toString();
    }
}
