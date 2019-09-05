package org.grant.utils.invoice;

/**
 * grant
 * 2/9/2019 3:26 PM
 * 描述：
 */
public class InvoiceSize {
    //长 厘米
    private final Double height;
    //宽 厘米
    private final Double width;

    public InvoiceSize(Double height, Double width) {
        this.height = height;
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "InvoiceSize{" +
                "height=" + height +
                "cm, width=" + width +
                "cm}";
    }


    public static final InvoiceSize CM_22_13$97 = new InvoiceSize(22d, 13.97d);
    public static final InvoiceSize PIXEL_22_13$97 = new InvoiceSize(396d, 623.583d);
    public static final InvoiceSize PIXEL_x300_22_13$973 = new InvoiceSize(1649d, 2598d);

    public static final InvoiceSize CM_21$5_13$9 = new InvoiceSize(21.5d, 13.9d);
    public static final InvoiceSize PIXEL_21$5_13$9 = new InvoiceSize(394.01d, 609.44d);
    public static final InvoiceSize PIXEL_x300_21$5_13$9 = new InvoiceSize(1641d, 2539d);

}
