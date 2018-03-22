package com.example.listview;

/**
 * See builderpattern example project for how to do builders
 * they are essential when constructing complicated objects and
 * with many optional fields
 */
public class BikeData {
    String COMPANY = "";
    String MODEL = "";
    Double PRICE;
    String LOCATION = "";
    String DESCRIPTION = "";
    String DATE = "";
    String PICTURE = "";
    String LINK = "";

    //TODO make all BikeData fields final

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO figure out how to print all bikedata out for dialogs

        String a = "Company: "+ this.COMPANY;
        String b = "Model: "+ this.MODEL;
        String c = "Price: $"+ this.PRICE;
        String d = "Location: "+ this.LOCATION;
        String e = "Date Listed: "+ this.DATE;
        String f = "Description "+ this.DESCRIPTION;
        String g = "Link: "+ this.LINK;

        String last = a + "\n" +b+ "\n" + c +"\n"+d+"\n"+e+"\n"+f+"\n"+"g";

        return last;
    }

    private BikeData(Builder b) {
        //TODO
        this.COMPANY = b.Company;
        this.MODEL = b.Model;
        this.PRICE = b.Price;
        this.LOCATION = b.Location;
        this.DESCRIPTION = b.Description;
        this.DATE = b.Date;
        this.PICTURE = b.Picture;
        this.LINK = b.Link;




    }

    /**
     * @author lynn builder pattern, see page 11 Effective Java UserData mydata
     *         = new
     *         UserData.Builder(first,last).addProject(proj1).addProject(proj2
     *         ).build()
     */
    public static class Builder {
        final String Company;
        final String Model;
        final Double Price;
        String Description;
        String Location;
        String Date;
        String Picture;
        String Link;

        // Model and price required
        Builder(String Company, String Model, Double Price,String Description,String Location, String Date, String Picture, String Link) {
            this.Company = Company;
            this.Model = Model;
            this.Price = Price;
            this.Description = Description;
            this.Location = Location;
            this.Date = Date;
            this.Picture = Picture;
            this.Link = Link;
        }

        // the following are setters
        // notice it returns this bulder
        // makes it suitable for chaining
        Builder setDescription(String Description) {
            //TODO manage this
            this.Description = Description;
            return this;
        }

        Builder setLocation(String Location) {
            this.Location = Location;
            return this;
        }

        Builder setDate(String Date) {
            this.Date = Date;
            return this;
        }

        Builder setPicture(String Picture) {
            this.Picture = Picture;
            return this;
        }

        Builder setLink(String Link) {
            this.Link = Link;
            return this;
        }

        // use this to actually construct Bikedata
        // without fear of partial construction
        public BikeData build() {
            return new BikeData(this);
        }
    }
}
