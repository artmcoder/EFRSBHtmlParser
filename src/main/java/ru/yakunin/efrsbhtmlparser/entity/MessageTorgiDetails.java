package ru.yakunin.efrsbhtmlparser.entity;

import javax.persistence.*;

@Entity
@Table(name="messagetorgi_details")
public class MessageTorgiDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lotNumber;
    @Column(columnDefinition = "text")
    private String lotDescription;
    private String startPrice;
    private String step;
    private String deposit;
    @Column(columnDefinition = "text")
    private String priceDecreasingInfo;
    private String lotClassification;

    public MessageTorgiDetails(String lotNumber, String lotDescription,
                               String startPrice, String step, String deposit,
                               String priceDecreasingInfo, String lotClassification) {
        this.lotNumber = lotNumber;
        this.lotDescription = lotDescription;
        this.startPrice = startPrice;
        this.step = step;
        this.deposit = deposit;
        this.priceDecreasingInfo = priceDecreasingInfo;
        this.lotClassification = lotClassification;
    }

    public MessageTorgiDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLotDescription() {
        return lotDescription;
    }

    public void setLotDescription(String lotDescription) {
        this.lotDescription = lotDescription;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getPriceDecreasingInfo() {
        return priceDecreasingInfo;
    }

    public void setPriceDecreasingInfo(String priceDecreasingInfo) {
        this.priceDecreasingInfo = priceDecreasingInfo;
    }

    public String getLotClassification() {
        return lotClassification;
    }

    public void setLotClassification(String lotClassification) {
        this.lotClassification = lotClassification;
    }

    // we need an annotation @One-to-one here to the table MessageTorgi
    //uni-directional relations

}
