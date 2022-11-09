package org.dandelion.excel.poi;

import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/06/23 14:45
 */
public class InspectionReport {

    private String reportName;

    private Integer shift;

    private String line;

    private String stationCode;

    private Integer area;

    private Integer reportStatus;

    private List<InspectionItem> items;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public List<InspectionItem> getItems() {
        return items;
    }

    public void setItems(List<InspectionItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "InspectionReport{" +
            "reportName='" + reportName + '\'' +
            ", shift=" + shift +
            ", line='" + line + '\'' +
            ", stationCode='" + stationCode + '\'' +
            ", area=" + area +
            ", reportStatus=" + reportStatus +
            ", items=" + items +
            '}';
    }
}
