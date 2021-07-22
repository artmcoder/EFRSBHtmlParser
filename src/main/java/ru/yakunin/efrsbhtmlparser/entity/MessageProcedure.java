package ru.yakunin.efrsbhtmlparser.entity;

import javax.persistence.*;

@Entity
@Table(name="message_procedures")
public class MessageProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dateOfPublication;
    private String debtorName;
    private String debtorAdres;
    @Column(name = "INN")
    private String inn;
    private String caseNumber;
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
            fetch = FetchType.LAZY)
    @JoinColumn
    private ArbitrManager arbitrManager;
    @Column(name = "SRO")
    private String sro;
    private String publicationText;

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

    public String getPublicationText() {
        return publicationText;
    }

    public void setPublicationText(String publicationText) {
        this.publicationText = publicationText;
    }
}
