package com.shoppa.Model;

public class ReportModel {

    String ComplaintTitle, ComplaintDescription, ComplaintPhone,ComplainScreenshotimage;

    public ReportModel(String complaintTitle, String complaintDescription, String complaintPhone/*,String*//* complainScreenshotimage*/) {
        ComplaintTitle = complaintTitle;
        ComplaintDescription = complaintDescription;
        ComplaintPhone = complaintPhone;
/*        ComplainScreenshotimage =complainScreenshotimage;*/
    }

    public String getComplaintTitle() {
        return ComplaintTitle;
    }
    public  String getComplainScreenshotimage()
    {
        return ComplainScreenshotimage;
    }
    public void SetComplaintScreenimage(String complainScreenshotimage)
    {
        ComplainScreenshotimage=complainScreenshotimage;

    }

    public void setComplaintTitle(String complaintTitle) {
        ComplaintTitle = complaintTitle;
    }

    public String getComplaintDescription() {
        return ComplaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        ComplaintDescription = complaintDescription;
    }

    public String getComplaintPhone() {
        return ComplaintPhone;
    }

    public void setComplaintPhone(String complaintPhone) {
        ComplaintPhone = complaintPhone;
    }
}
