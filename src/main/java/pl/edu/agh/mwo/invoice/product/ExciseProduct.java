package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class ExciseProduct extends Product{

    public static final String TaxProcentage = "0.23";
    public static final String ExcisePrice = "5.56";
    BigDecimal excise;

    public ExciseProduct(String name, BigDecimal price, BigDecimal excise) {
        super(name, price, new BigDecimal(TaxProcentage)); new BigDecimal(ExcisePrice);
        this.excise = excise;
    }
}
