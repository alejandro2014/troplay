package org.troplay.graphics;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckboxContainer {
    private List<CheckBox> checkBoxes;
    private String currentValue;

    /*public void add(String value, Rectangle rectangle) {
        CheckBox checkBox = new CheckBox(value, rectangle, this);

        if (checkBoxes.size() == 0) {
            checkBox.click();
        }

        checkBoxes.add(checkBox);
    }*/

    public CheckboxContainer() {
        this.checkBoxes = new ArrayList<>();
    }

    public void add(CheckBox checkBox) {
        if (checkBoxes.size() == 0) {
            checkBox.click();
        }

        checkBox.setContainer(this);

        checkBoxes.add(checkBox);
    }

    public void notifyChange(String value) {
        this.currentValue = value;

        checkBoxes.stream()
                .filter(ch -> ! ch.getValue().equals(value))
                .forEach(CheckBox::release);
    }
}
