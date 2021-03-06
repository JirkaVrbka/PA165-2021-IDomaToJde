package cz.idomatojde.entity;

import cz.idomatojde.entity.base.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing a user on web.
 *
 * @author Jiri Vrbka
 */
@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "\"User\"", indexes = {@Index(name = "tokens_index", columnList = "api_token")})
public class User implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".+@.+\\...+?")
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String phoneNumber;

    private int credits;

    private int bonusCredits;

    @Column(name = "api_token")
    private String token;

    private LocalDate tokenExpiration;

    @OneToMany
    private List<Offer> offers;

    @ManyToMany
    @JoinTable(name = "consumed_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<Offer> subscribedOffers = new ArrayList<>();

    @NotNull
    private boolean wantsAdvertisement;

    @NotNull
    private boolean isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passHash) {
        this.password = passHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getBonusCredits() {
        return bonusCredits;
    }

    public void setBonusCredits(int bonusCredits) {
        this.bonusCredits = bonusCredits;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(LocalDate tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Offer> getSubscribedOffers() {
        return subscribedOffers;
    }

    public void setSubscribedOffers(List<Offer> consumes) {
        this.subscribedOffers = consumes;
    }

    public boolean wantsAdvertisement() {
        return wantsAdvertisement;
    }

    public void setWantsAdvertisement(boolean wantsAdvertisement) {
        this.wantsAdvertisement = wantsAdvertisement;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (!(o instanceof User)) return false;

        User user = (User) o;
        return isAdmin() == user.isAdmin()
                && Objects.equals(getUsername(), user.getUsername())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getName(), user.getName())
                && Objects.equals(getSurname(), user.getSurname()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getEmail(),
                getName(),
                getSurname(),
                isAdmin()
        );
    }
}
