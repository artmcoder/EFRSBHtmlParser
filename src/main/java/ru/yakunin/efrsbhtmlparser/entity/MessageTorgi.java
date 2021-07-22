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
    private String location;
    private String town;
    private String region;
    private String area;
    private String detailsLink;
    private LocalDateTime dateOfCreated;

    public static String makeStringToLowerCase(String word) {
        String lowerString = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            lowerString += Character.toLowerCase(c);
        }
        return lowerString;
    }

    @PrePersist
    private void onCreate() {
//        long timeStart = System.currentTimeMillis();
//        String lotDescription = getMessageTorgiDetails().getLotDescription();
//        String regions[] = {
//                "Орловская обл.", "Ярославская область", "Вологодская обл.", "Кемеровская область"
//        };
//        String location = "";
//        int numberOfSymbolToStartGetLocation = 0;
//        int numberOfSymbolToFinishGetLocation = 0;
//        char[] originalLotDesChars = lotDescription.toCharArray();
//        char[] lotDesChars = makeStringToLowerCase(lotDescription).toCharArray();
//        for (int i = 0; i < lotDesChars.length; i++) {
//            for (int j = 0; j < regions.length; j++) {
//                char[] regionChars = makeStringToLowerCase(regions[j]).toCharArray();
//                for (int k = 0; k < regionChars.length; k++) {
//                    try {
//                        if (lotDesChars[i] == regionChars[k]) {
//                            if (lotDesChars[i + 1] == regionChars[k + 1]) {
//                                if (lotDesChars[i + 2] == regionChars[k + 2]) {
//                                    if (lotDesChars[i + 3] == regionChars[k + 3]) {
//                                        if (lotDesChars[i + 4] == regionChars[k + 4]) {
//                                            if (numberOfSymbolToStartGetLocation == 0) {
//                                                numberOfSymbolToStartGetLocation = i;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } catch (IndexOutOfBoundsException e) {
//                        break;
//                    }
//                }
//            }
//        }
//        for (int i = numberOfSymbolToStartGetLocation; i < lotDesChars.length; i++) {
//            try {
//                if (numberOfSymbolToFinishGetLocation == 0) {
//                    if (isNumber(lotDesChars[i])) {
//                        numberOfSymbolToFinishGetLocation = i;
//                        if (isNumber(lotDesChars[i + 1])) {
//                            numberOfSymbolToFinishGetLocation = i + 1;
//                            if (isNumber(lotDesChars[i + 2])) {
//                                numberOfSymbolToFinishGetLocation = i + 2;
//                                if (isNumber(lotDesChars[i + 3])) {
//                                    numberOfSymbolToFinishGetLocation = i + 3;
//                                } else break;
//                            } else break;
//                        } else break;
//                    }
//                }
//            } catch (IndexOutOfBoundsException e) {
//                break;
//            }
//        }
//        for (int q = numberOfSymbolToStartGetLocation; q <= numberOfSymbolToFinishGetLocation; q++) {
//            location += originalLotDesChars[q];
//        }
//        this.location = location;
//        String beforeLocationWords[] = this.location.split(" ");
//        List<String> locationWords = new ArrayList<>();
//        for (int i = 0; i < beforeLocationWords.length; i++) {
//            String deleteCommaInLocationWords[] = beforeLocationWords[i].split(",");
//            for (int j = 0; j < deleteCommaInLocationWords.length; j++) {
//                locationWords.add(deleteCommaInLocationWords[j]);
//            }
//        }
//        String regionsForSearchRegion[] = {
//                "Орловская", "Ярославская", "Вологодская", "Кемеровская"
//        };
//        String towns[] = {
//                "Ярославль", "Тяжинский", "Шексна", "Тисуль"
//        };
//
//        String region = "";
//        String town = "";
//
//        for (String l : locationWords) {
//            for (int i = 0; i < regionsForSearchRegion.length; i++) {
//                if (l.equals(regionsForSearchRegion[i])) region = regionsForSearchRegion[i];
//            }
//        }
//        for (String l : locationWords) {
//            for (int i = 0; i < towns.length; i++) {
//                if (l.equals(towns[i])) town = towns[i];
//            }
//        }
//        this.region = region + " (обл., край)";
//        this.town = town;
//
//        String stringArea = "";
//        int startAreaNumber = 0;
//        char[] stopWordChars = {'п', 'л', 'о', 'щ', 'а', 'д'};
//        for (int i = 0; i < lotDesChars.length; i++) {
//            for (int j = 0; j < stopWordChars.length; j++) {
//                try {
//                    if (lotDesChars[i] == stopWordChars[j]) {
//                        if (lotDesChars[i + 1] == stopWordChars[j + 1]) {
//                            if (lotDesChars[i + 2] == stopWordChars[j + 2]) {
//                                if (lotDesChars[i + 3] == stopWordChars[j + 3]) {
//                                    if (lotDesChars[i + 4] == stopWordChars[j + 4]) {
//                                        if (startAreaNumber == 0) {
//                                            startAreaNumber = i;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch (IndexOutOfBoundsException e) {
//                    break;
//                }
//            }
//        }
//        for (int i = startAreaNumber; i < lotDesChars.length; i++) {
//            if (isNumber(lotDesChars[i])) {
//                startAreaNumber = i;
//                break;
//            }
//        }
//        for (int i = startAreaNumber; i < lotDesChars.length; i++) {
//            if (isAreaValue(lotDesChars[i])) {
//                stringArea += lotDesChars[i];
//            } else break;
//        }
//        this.area = stringArea;
//        System.out.println("Время работы алгоритма: " + (System.currentTimeMillis() - timeStart));
        this.dateOfCreated = LocalDateTime.now();
    }

    private static boolean isAreaValue(char lotDesChar) {
        boolean result = true;
        try {
            int someInt = Integer.parseInt(String.valueOf(lotDesChar));
        } catch (NumberFormatException e) {
            result = false;
        }
        if (lotDesChar == ',' | lotDesChar == '.') result = true;
        return result;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public static boolean isNumber(char s) {
        try {
            int someInt = Integer.parseInt(String.valueOf(s));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


//    public String getRegionFromLotDescription() {
//        String regions[] = {
//                "Орловская область", "Орловская обл.",
//                "Ярославская область", "Вологодская обл.", "Кемеровская область"
//        };
//
//        String towns[] = {
//                "Екатиринбург"
//        };
//
//        for (int j = 0; j < regions.length; j++) {
//            if (makeStringToLowerCase(messageTorgiDetails.getLotDescription())
//                    .contains(makeStringToLowerCase(regions[j]))) {
//                return regions[j];
//            }
//        }
//        return null;
//    }

//    public String getLocationFromLotDes() {
//        String lotDes =
//                "квартира № 1 по адресу: Вологодская обл.," +
//                        " Шекснинский р-н., п. Шексна, ул. Труда, д. " +
//                        "35а; кадастровый № 35:23:0205019:595, площадь 19,8 кв. м.";
//        String regions[] = {
//                "Орловская область", "Орловская обл.",
//                "Ярославская область", "Вологодская обл.", "Кемеровская область"
//        };
//        String lotDescription = getMessageTorgiDetails().getLotDescription();
//        char[] lotDesChars = makeStringToLowerCase(lotDescription).toCharArray();
//        String location = "";
//        int numberOfSymbolToStartGetLocation = 0;
//        for (int i = 0; i < lotDesChars.length; i++) {
//            for (int j = 0; j < regions.length; j++) {
//                char[] regionChars = makeStringToLowerCase(regions[i]).toCharArray();
//                for (int k = 0; k < regionChars.length; k++) {
//                    if (lotDesChars[i] == regionChars[k]) {
//                        if (lotDesChars[i + 1] == regionChars[k + 1]) {
//                            if (lotDesChars[i + 2] == regionChars[k + 2]) {
//                                if (lotDesChars[i + 3] == regionChars[k + 3]) {
//                                    if (lotDesChars[i + 4] == regionChars[k + 4]) {
//                                        numberOfSymbolToStartGetLocation = i;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (numberOfSymbolToStartGetLocation != 0) {
//                try {
//                    int simpleCheckNumberFormatException = Integer.parseInt(String.valueOf(lotDesChars[i]));
//                    location += lotDesChars[i];
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Cool everything!");
//                }
//            }
//        }
//        return location;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
                ", detailsLink='" + detailsLink + '\'' +
                '}';
    }
}
