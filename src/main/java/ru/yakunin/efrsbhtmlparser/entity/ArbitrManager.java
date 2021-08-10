package ru.yakunin.efrsbhtmlparser.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "arbitr_managers")
public class ArbitrManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIO")
    private String fullName;
    private String regNumber;
    private String regDate;
    private String sro;
    private int messagesQuantity;
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST },
    mappedBy = "arbitrManager")
    private List<MessageTorgi> messageTorgis = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST },
            mappedBy = "arbitrManager")
    private List<MessageValuation> messageValuations = new ArrayList<>();
    private String arbitrManagersDetailsLink;

    public ArbitrManager() {

    }

    public ArbitrManager(String fullName, String regNumber, String regDate, String sro, Integer messagesQuantity) {
        this.fullName = fullName;
        this.regNumber = regNumber;
        this.regDate = regDate;
        this.sro = sro;
        this.messagesQuantity = messagesQuantity;
    }

    public void addMessageTorgiToArbitrManager(MessageTorgi messageTorgi) {
        messageTorgi.setArbitrManager(this);
        messageTorgis.add(messageTorgi);
    }

    public void addMessageValuationToArbitrManager(MessageValuation messageValuation) {
        messageValuation.setArbitrManager(this);
        messageValuations.add(messageValuation);
    }

    public String getArbitrManagersDetailsLink() {
        return arbitrManagersDetailsLink;
    }

    public void setArbitrManagersDetailsLink(String arbitrManagersDetailsLink) {
        this.arbitrManagersDetailsLink = arbitrManagersDetailsLink;
    }

    public List<MessageTorgi> getMessageTorgis() {
        return messageTorgis;
    }

    public void setMessageTorgis(List<MessageTorgi> messageTorgis) {
        this.messageTorgis = messageTorgis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSro() {
        return sro;
    }

    public void setSro(String sro) {
        this.sro = sro;
    }

    public int getMessagesQuantity() {
        return messagesQuantity;
    }

    public void setMessagesQuantity(Integer messagesQuantity) {
        this.messagesQuantity = messagesQuantity;
    }


    @Override
    public String toString() {
        return "ArbitrManager{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", regDate='" + regDate + '\'' +
                ", sro='" + sro + '\'' +
                ", messagesQuantity=" + messagesQuantity +
                ", messageTorgis=" + messageTorgis +
                ", messageValuations=" + messageValuations +
                ", arbitrManagersDetailsLink='" + arbitrManagersDetailsLink + '\'' +
                '}';
    }
}
