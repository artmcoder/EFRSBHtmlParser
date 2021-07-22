package ru.yakunin.efrsbhtmlparser.entity;

import ru.yakunin.efrsbhtmlparser.controller.ControllerUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(columnDefinition = "text")
    private String lotClassification;
    @Column(columnDefinition = "text")
    private String location;
    private String town;
    private String region;
    private String area;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private MessageTorgi messageTorgi;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void onCreate() {
        String lotDescription = this.lotDescription;
        String regions[] = ControllerUtils.getAllRegions().toArray(new String[0]);
        String location = "";
        int numberOfSymbolToStartGetLocation = 0;
        int numberOfSymbolToFinishGetLocation = 0;
        char[] originalLotDesChars = lotDescription.toCharArray();
        char[] lotDesChars = makeStringToLowerCase(lotDescription).toCharArray();
        for (int i = 0; i < lotDesChars.length; i++) {
            for (int j = 0; j < regions.length; j++) {
                char[] regionChars = makeStringToLowerCase(regions[j]).toCharArray();
                for (int k = 0; k < regionChars.length; k++) {
                    try {
                        if (lotDesChars[i] == regionChars[k]) {
                            if (lotDesChars[i + 1] == regionChars[k + 1]) {
                                if (lotDesChars[i + 2] == regionChars[k + 2]) {
                                    if (lotDesChars[i + 3] == regionChars[k + 3]) {
                                        if (lotDesChars[i + 4] == regionChars[k + 4]) {
                                            if (numberOfSymbolToStartGetLocation == 0) {
                                                numberOfSymbolToStartGetLocation = i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
            }
        }
        for (int i = numberOfSymbolToStartGetLocation; i < lotDesChars.length; i++) {
            try {
                if (numberOfSymbolToFinishGetLocation == 0) {
                    if (isNumber(lotDesChars[i])) {
                        numberOfSymbolToFinishGetLocation = i;
                        if (isNumber(lotDesChars[i + 1])) {
                            numberOfSymbolToFinishGetLocation = i + 1;
                            if (isNumber(lotDesChars[i + 2])) {
                                numberOfSymbolToFinishGetLocation = i + 2;
                                if (isNumber(lotDesChars[i + 3])) {
                                    numberOfSymbolToFinishGetLocation = i + 3;
                                } else break;
                            } else break;
                        } else break;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        for (int q = numberOfSymbolToStartGetLocation; q <= numberOfSymbolToFinishGetLocation; q++) {
            location += originalLotDesChars[q];
        }
        this.location = location;
        String beforeLocationWords[] = this.location.split(" ");
        List<String> locationWords = new ArrayList<>();
        for (int i = 0; i < beforeLocationWords.length; i++) {
            String deleteCommaInLocationWords[] = beforeLocationWords[i].split(",");
            for (int j = 0; j < deleteCommaInLocationWords.length; j++) {
                locationWords.add(deleteCommaInLocationWords[j]);
            }
        }
        String regionsForSearchRegion[] = {
                "Орловская", "Ярославская", "Вологодская", "Кемеровская"
        };
        String towns[] = {
                "Ярославль", "Тяжинский", "Шексна", "Тисуль"
        };

        String region = "";
        String town = "";

        for (String l : locationWords) {
            for (int i = 0; i < regionsForSearchRegion.length; i++) {
                if (l.equals(regionsForSearchRegion[i])) region = regionsForSearchRegion[i];
            }
        }
        for (String l : locationWords) {
            for (int i = 0; i < towns.length; i++) {
                if (l.equals(towns[i])) town = towns[i];
            }
        }
        this.region = region + " (обл., край)";
        this.town = town;

        String stringArea = "";
        int startAreaNumber = 0;
        char[] stopWordChars = {'п', 'л', 'о', 'щ', 'а', 'д'};
        for (int i = 0; i < lotDesChars.length; i++) {
            for (int j = 0; j < stopWordChars.length; j++) {
                try {
                    if (lotDesChars[i] == stopWordChars[j]) {
                        if (lotDesChars[i + 1] == stopWordChars[j + 1]) {
                            if (lotDesChars[i + 2] == stopWordChars[j + 2]) {
                                if (lotDesChars[i + 3] == stopWordChars[j + 3]) {
                                    if (lotDesChars[i + 4] == stopWordChars[j + 4]) {
                                        if (startAreaNumber == 0) {
                                            startAreaNumber = i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }
        for (int i = startAreaNumber; i < lotDesChars.length; i++) {
            if (isNumber(lotDesChars[i])) {
                startAreaNumber = i;
                break;
            }
        }
        for (int i = startAreaNumber; i < lotDesChars.length; i++) {
            if (isAreaValue(lotDesChars[i])) {
                stringArea += lotDesChars[i];
            } else break;
        }
        this.area = stringArea;
        this.dateOfCreated = LocalDateTime.now();
    }

    public static String makeStringToLowerCase(String word) {
        String lowerString = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            lowerString += Character.toLowerCase(c);
        }
        return lowerString;
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

    public static boolean isNumber(char s) {
        try {
            int someInt = Integer.parseInt(String.valueOf(s));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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

    public MessageTorgi getMessageTorgi() {
        return messageTorgi;
    }

    public void setMessageTorgi(MessageTorgi messageTorgi) {
        this.messageTorgi = messageTorgi;
    }

    @Override
    public String toString() {
        return "MessageTorgiDetails{" +
                "lotNumber='" + lotNumber + '\'' +
                ", lotDescription='" + lotDescription + '\'' +
                ", startPrice='" + startPrice + '\'' +
                ", step='" + step + '\'' +
                ", deposit='" + deposit + '\'' +
                ", priceDecreasingInfo='" + priceDecreasingInfo + '\'' +
                ", lotClassification='" + lotClassification + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }
}
