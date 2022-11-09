package org.dandelion.excel.poi;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/06/23 14:45
 */
public class InspectionItem {

    private String item;

    private String method;

    private String mode;

    private String standardValue;

    private String deviationValue;

    private Integer frequency;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public String getDeviationValue() {
        return deviationValue;
    }

    public void setDeviationValue(String deviationValue) {
        this.deviationValue = deviationValue;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "InspectionItem{" +
            "item='" + item + '\'' +
            ", method='" + method + '\'' +
            ", mode='" + mode + '\'' +
            ", standardValue='" + standardValue + '\'' +
            ", deviationValue='" + deviationValue + '\'' +
            ", frequency=" + frequency +
            '}';
    }
}
