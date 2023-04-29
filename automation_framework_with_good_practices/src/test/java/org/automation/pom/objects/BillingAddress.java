package org.automation.pom.objects;

public class BillingAddress {
    public String firstName;
    public String lastName;
    public String streetAddress;
    public String city;
    public String zipcode;
    public String email;
    public String pageOrderNotes;

    public BillingAddress(){

    }

    public BillingAddress(String firstName, String lastName, String streetAddress, String city, String zipcode, String email, String pageOrderNotes){
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress=streetAddress;
        this.city = city;
        this.zipcode=zipcode;
        this.email=email;
        this.pageOrderNotes=pageOrderNotes;
    }
    
    public String getFirstname() {
        return firstName;
    }

    public BillingAddress setFirstname(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastname() {
        return lastName;
    }

    public BillingAddress setLastname(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getStreetaddress() {
        return streetAddress;
    }

    public BillingAddress setStreetaddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public String getCity() {
        return city;
    }

    public BillingAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZipcode() {
        return zipcode;
    }

    public BillingAddress setZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BillingAddress setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPageOrderNotes() {
        return pageOrderNotes;
    }

    public BillingAddress setPageOrderNotes(String pageOrderNotes) {
        this.pageOrderNotes = pageOrderNotes;
        return this;
    }
}
