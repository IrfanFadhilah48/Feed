package net.windowsv8.mycat;

/**
 * Created by Windowsv8 on 12/5/2015.
 */

public class SuperHero {

    //Data Variables
    private String id;
    private String imageUrl;
    private String name;
    private String publisher;
    private String description;
    private String telephone;
    private String address;
    private String jenis;
    private String username;
    private SuperHeroUser superHeroUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getTelephone(){
        return telephone;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SuperHeroUser getSuperHeroUser() {
        return superHeroUser;
    }

    public void setSuperHeroUser(SuperHeroUser superHeroUser) {
        this.superHeroUser = superHeroUser;
    }
}
