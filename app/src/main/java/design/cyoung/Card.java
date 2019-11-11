package design.cyoung;

import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;

public class Card {
    private String displayTitle, displaySubtitle = "";
    private BarcodeFormat barcodeFormat;
    private String barcodeText;

    public Card(String displayTitle, String barcodeText, BarcodeFormat barcodeFormat) {
        this.displayTitle = displayTitle;
        this.barcodeText = barcodeText;
        this.barcodeFormat = barcodeFormat;
    }

    public Card(String displayTitle, String displaySubtitle, String barcodeText, BarcodeFormat barcodeFormat) {
        this.displayTitle = displayTitle;
        this.displaySubtitle = displaySubtitle;
        this.barcodeText = barcodeText;
        this.barcodeFormat = barcodeFormat;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public String getDisplaySubtitle() {
        return displaySubtitle;
    }

    public String getBarcodeText() {
        return barcodeText;
    }

    public BarcodeFormat getBarcodeFormat() {
        return barcodeFormat;
    }
}
