package org.sensorsink.sink.grovestream;

/*
{
   "message":"",
   "organization":{
      "uid":"d251a8f2-f7b9-4df7-886d-b24c7f4929d4
      "signupDate":0,
      "address":{
         "postalCode":"",
         "street":"",
         "stateOrProvince":"",
         "country":"",
         "city":""
      },
      "name":"",
      "contact":[ ]
   },
   "success":true
}
 */
public class Organization
{
    private String uid;
    private long signupDate;
    private Address address;
    private String name;
    private String[] contact;

    public String getUid()
    {
        return uid;
    }

    public void setUid( String uid )
    {
        this.uid = uid;
    }

    public long getSignupDate()
    {
        return signupDate;
    }

    public void setSignupDate( long signupDate )
    {
        this.signupDate = signupDate;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress( Address address )
    {
        this.address = address;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String[] getContact()
    {
        return contact;
    }

    public void setContact( String[] contact )
    {
        this.contact = contact;
    }

    public static class Address
    {
        private String postalCode;
        private String street;
        private String stateOrProvince;
        private String country;
        private String city;

        public String getPostalCode()
        {
            return postalCode;
        }

        public void setPostalCode( String postalCode )
        {
            this.postalCode = postalCode;
        }

        public String getStreet()
        {
            return street;
        }

        public void setStreet( String street )
        {
            this.street = street;
        }

        public String getStateOrProvince()
        {
            return stateOrProvince;
        }

        public void setStateOrProvince( String stateOrProvince )
        {
            this.stateOrProvince = stateOrProvince;
        }

        public String getCountry()
        {
            return country;
        }

        public void setCountry( String country )
        {
            this.country = country;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity( String city )
        {
            this.city = city;
        }
    }
}
