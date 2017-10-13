package com.example.marko.proba1;

/**
 * Created by marko on 11.10.2017.
 */

public class Komitent {

        String Id;
        String CompanyName;
        String ContactTitle;
        String Address;
        String City;
        String PostalCode;
        String Country;
        String Phone;
        String Fax;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getContactTitle() {
        return ContactTitle;
    }

    public void setContactTitle(String contactTitle) {
        ContactTitle = contactTitle;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public Komitent(String _id, String _Naziv, String _Titula, String _adresa, String _grad, String _postanskiBroj, String _zemlja, String _telefon, String _fax){
            Id=_id;
            CompanyName=_Naziv;
            ContactTitle=_Titula;
            Address=_adresa;
            City=_grad;
            PostalCode=_postanskiBroj;
            Country=_zemlja;
            Phone=_telefon;
            Fax=_fax;
        }

}
