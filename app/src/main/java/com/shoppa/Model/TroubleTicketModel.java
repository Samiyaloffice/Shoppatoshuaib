package com.shoppa.Model;

public class TroubleTicketModel {

    String mTicketNumber, mTicketTitle, mTicketDescription, mTicketDate;

    public TroubleTicketModel(String mTicketNumber,
                              String mTicketTitle,
                              String mTicketDescription,
                              String mTicketDate) {
        this.mTicketNumber = mTicketNumber;
        this.mTicketTitle = mTicketTitle;
        this.mTicketDescription = mTicketDescription;
        this.mTicketDate = mTicketDate;
    }

    public String getmTicketNumber() {
        return mTicketNumber;
    }

    public void setmTicketNumber(String mTicketNumber) {
        this.mTicketNumber = mTicketNumber;
    }

    public String getmTicketTitle() {
        return mTicketTitle;
    }

    public void setmTicketTitle(String mTicketTitle) {
        this.mTicketTitle = mTicketTitle;
    }

    public String getmTicketDescription() {
        return mTicketDescription;
    }

    public void setmTicketDescription(String mTicketDescription) {
        this.mTicketDescription = mTicketDescription;
    }

    public String getmTicketDate() {
        return mTicketDate;
    }

    public void setmTicketDate(String mTicketDate) {
        this.mTicketDate = mTicketDate;
    }
}
