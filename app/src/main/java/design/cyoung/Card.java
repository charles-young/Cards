package design.cyoung;

import java.util.ArrayList;

public class Card {
    private String displayTitle, displaySubtitle = "";
    private ArrayList<Integer> selectedIndicis;

    public Card(String displayTitle, ArrayList<Integer> selectedIndicis) {
        this.displayTitle = displayTitle;
        this.selectedIndicis = selectedIndicis;
    }

    public Card(String displayTitle, String displaySubtitle, ArrayList<Integer> selectedIndicis) {
        this.displayTitle = displayTitle;
        this.displaySubtitle = displaySubtitle;
        this.selectedIndicis = selectedIndicis;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public String getDisplaySubtitle() {
        return displaySubtitle;
    }

    public ArrayList<Integer> getSelectedIndicis() {
        return selectedIndicis;
    }
}
