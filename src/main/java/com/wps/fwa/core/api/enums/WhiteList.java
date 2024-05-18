package com.wps.fwa.core.api.enums;

public enum WhiteList {

    U1("1111111111"),
    U2("222222222"),
    U3("333333333"),
    U4("4444444444"),
    INVALID("invalidnumber");

    private String telephoneNumber;

    WhiteList(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    private String getTelephoneNumber() {
        return telephoneNumber;
    }

    public static boolean isReceiverExist(String telephoneNumber){
        for (WhiteList receiver : WhiteList.values()){
            if (receiver.getTelephoneNumber().equals(telephoneNumber))
                return true;
        }
        return false;
    }

}
