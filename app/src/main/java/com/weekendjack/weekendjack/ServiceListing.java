package com.weekendjack.weekendjack;

public class ServiceListing {

    private int serviceId;
    private String serviceType;
    private String serviceTitle;
    private String serviceDescription;
    private String userNamePosted;

    public ServiceListing(int serviceId, String serviceType, String serviceTitle, String serviceDescription, String userNamePosted) {
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        this.serviceTitle = serviceTitle;
        this.serviceDescription = serviceDescription;
        this.userNamePosted = userNamePosted;
    }

    public int getServiceId() { return serviceId; }
    public String getServiceType() { return serviceType; }
    public String getServiceTitle() { return serviceTitle; }
    public String getServiceDescription() { return serviceDescription; }
    public String getUserNamePosted() { return userNamePosted; }

}
