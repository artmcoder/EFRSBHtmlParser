package ru.yakunin.efrsbhtmlparser.entity;

import javax.persistence.*;

@Entity
@Table(name="messagevaluation_details")
public class MessageValuationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propertyType;
    private String propertyDescription;
    private String valuationDate;
    private String cost;
    private String balanceCost;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private MessageValuation messageValuation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(String valuationDate) {
        this.valuationDate = valuationDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBalanceCost() {
        return balanceCost;
    }

    public void setBalanceCost(String balanceCost) {
        this.balanceCost = balanceCost;
    }

    public MessageValuation getMessageValuation() {
        return messageValuation;
    }

    public void setMessageValuation(MessageValuation messageValuation) {
        this.messageValuation = messageValuation;
    }

    @Override
    public String toString() {
        return "MessageValuationDetails{" +
                "propertyType='" + propertyType + '\'' +
                ", propertyDescription='" + propertyDescription + '\'' +
                ", valuationDate='" + valuationDate + '\'' +
                ", cost='" + cost + '\'' +
                ", balanceCost='" + balanceCost + '\'' +
                '}';
    }

    //here we need the method of downloading attached files in pdf, word formats
    // we need an annotation @One-to-one here to the table MessageValuation
    //uni-directional relations
}
