package ru.yakunin.efrsbhtmlparser.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "torgi_messages")
public class MessageTorgi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long numberOfMessage;
    private String dateOfPublication;
    private String debtorName;
    @Column(columnDefinition = "text")
    private String debtorAdres;
    @Column(name = "INN")
    private String inn;
    private String caseNumber;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn
    private ArbitrManager arbitrManager;
    @Column(name = "SRO")
    private String sro;
    private String auctionType;
    private String auctionStartDate;
    private String auctionFinishedDate;
    @Column(columnDefinition = "text")
    private String auctionRules;
    private String dateTimeAuction;
    private String priceOfferForm;
    private String marketPlace;
    @Column(columnDefinition = "text")
    private String publicationText;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "messageTorgi")
    private List<MessageTorgiDetails> messageTorgiDetails = new ArrayList<>();
    private String detailsLink;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void onCreate() {
        this.dateOfCreated = LocalDateTime.now();
    }

    public void addMessageTorgiDetailsToMessageTorgi(MessageTorgiDetails msgTorgiDeetails) {
        msgTorgiDeetails.setMessageTorgi(this);
        messageTorgiDetails.add(msgTorgiDeetails);
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorAdres() {
        return debtorAdres;
    }

    public void setDebtorAdres(String debtorAdres) {
        this.debtorAdres = debtorAdres;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public ArbitrManager getArbitrManager() {
        return arbitrManager;
    }

    public void setArbitrManager(ArbitrManager arbitrManager) {
        this.arbitrManager = arbitrManager;
    }

    public String getSro() {
        return sro;
    }

    public void setSro(String sro) {
        this.sro = sro;
    }

    public String getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }

    public String getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(String auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }

    public String getAuctionFinishedDate() {
        return auctionFinishedDate;
    }

    public void setAuctionFinishedDate(String auctionFinishedDate) {
        this.auctionFinishedDate = auctionFinishedDate;
    }

    public String getAuctionRules() {
        return auctionRules;
    }

    public void setAuctionRules(String auctionRules) {
        this.auctionRules = auctionRules;
    }

    public String getDateTimeAuction() {
        return dateTimeAuction;
    }

    public void setDateTimeAuction(String dateTimeAuction) {
        this.dateTimeAuction = dateTimeAuction;
    }

    public String getPriceOfferForm() {
        return priceOfferForm;
    }

    public void setPriceOfferForm(String priceOfferForm) {
        this.priceOfferForm = priceOfferForm;
    }

    public String getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(String marketPlace) {
        this.marketPlace = marketPlace;
    }

    public String getPublicationText() {
        return publicationText;
    }

    public void setPublicationText(String publicationText) {
        this.publicationText = publicationText;
    }

    public List<MessageTorgiDetails> getMessageTorgiDetails() {
        return messageTorgiDetails;
    }

    public void setMessageTorgiDetails(List<MessageTorgiDetails> messageTorgiDetails) {
        this.messageTorgiDetails = messageTorgiDetails;
    }

    public long getNumberOfMessage() {
        return numberOfMessage;
    }

    public void setNumberOfMessage(long numberOfMessage) {
        this.numberOfMessage = numberOfMessage;
    }

    @Override
    public String toString() {
        return "MessageTorgi{" +
                "numberOfMessage=" + numberOfMessage +
                ", dateOfPublication='" + dateOfPublication + '\'' +
                ", debtorName='" + debtorName + '\'' +
                ", debtorAdres='" + debtorAdres + '\'' +
                ", inn='" + inn + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", sro='" + sro + '\'' +
                ", auctionType='" + auctionType + '\'' +
                ", auctionStartDate='" + auctionStartDate + '\'' +
                ", auctionFinishedDate='" + auctionFinishedDate + '\'' +
                ", dateTimeAuction='" + dateTimeAuction + '\'' +
                ", priceOfferForm='" + priceOfferForm + '\'' +
                ", marketPlace='" + marketPlace + '\'' +
                ", messageTorgiDetails=" + messageTorgiDetails +
                ", detailsLink='" + detailsLink + '\'' +
                '}';
    }


}
