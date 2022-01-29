package com.helloworld.uifirs222;

public class revewmodel {

    String review;
    String phone;
    String rating;

    public revewmodel(String review, String phone, String rating) {
        this.review = review;
        this.phone = phone;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public String getPhone() {
        return phone;
    }

    public String getRating() {
        return rating;
    }
}
