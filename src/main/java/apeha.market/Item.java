package apeha.market;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item implements Settable {
    private String name;
    private BigDecimal price;
    private BigDecimal priceBlue;
    private String info;

    public Item() {
        this.info = "";
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void addInfo(String info) {
        this.info = this.info + info;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(String price) {
        try {
            this.price = new BigDecimal(price).setScale(2);
        } catch (NumberFormatException e) {

        }

    }

    public BigDecimal getPriceBlue() {
        return priceBlue;
    }

    public void setPriceBlue(String priceBlue) {
        try {
            this.priceBlue = new BigDecimal(priceBlue).setScale(2);
        } catch (NumberFormatException e) {

        }
    }

    @Override
    public void setNameFromLine(String name) {
        Pattern p = Pattern
                .compile("(([а-яА-Яё\"]+[\\ ]*)+(\\(мод\\.\\))*(\\ \\(закл\\.\\))*)");
        Matcher m = p.matcher(name);
        if (m.find())
            this.setName(m.group());

    }

    @Override
    public String toString() {
        return this.getName() + "," + this.getPrice() + " + "
                + this.getPriceBlue() + ","
                + this.getInfo().replaceAll("\\n", "|");
    }

    @Override
    public void setPricesFromLine(String sPrice) {
        Pattern patternPrice = Pattern.compile("\\d+\\.\\d{2}");
        Matcher matcher = patternPrice.matcher(sPrice);
        if (matcher.find()) {
            this.price = new BigDecimal(matcher.group()).setScale(2);
            if (matcher.find()) {
                this.priceBlue = new BigDecimal(matcher.group()).setScale(2);
            } else {
                this.priceBlue = BigDecimal.ZERO;
            }
        }

    }

}
