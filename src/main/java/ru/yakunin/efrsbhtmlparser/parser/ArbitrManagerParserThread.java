package ru.yakunin.efrsbhtmlparser.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgi;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgiDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArbitrManagerParserThread extends Thread {
    private static final String MARKET_PLACE = "Место проведения:";
    private static final String PRICE_OFFER_FORM = "Форма подачи предложения о цене:";
    private static final String DATE_TIME_AUCTION = "Дата и время торгов:";
    private static final String AUCTION_RULES = "Правила подачи заявок:";
    private static final String AUCTION_START_DATE = "Дата и время начала подачи заявок:";
    private static final String AUCTION_FINISHED_DATE = "Дата и время окончания подачи заявок:";
    private static final String AUCTION_TYPE = "Вид торгов:";
    private static final String CASE_NUMBER = "№ дела";
    private static final String ADRES_FOR_KORESPONDATION = "Адрес для корреспонденции";
    private static final String DEBTOR_ADRES_SRO_AY = "Адрес СРО АУ";
    private static final String DEBTOR_LEGAL_ADRES = "Адрес";
    private static final String DEBTOR_PERSON_ADRES = "Место жительства";
    private static final String INN = "ИНН";
    private static final String DEBTOR_FULL_NAME = "ФИО должника";
    private static final String DEBTOR_NAME = "Наименование должника";
    private final static String MESSAGE_TORGI_TEXT = "Объявление о проведении торгов";
    private final static String MESSAGE_INVENTORY_TEXT = "Сведения о результатах инвентаризации имущества должника";
    private final static String MESSAGE_VALUATION_TEXT = "Отчет оценщика об оценке имущества должника";
    private final static String MESSAGE_PROCEDURE_TEXT = "Сообщение о судебном акте";
    private static final String PARSE_ARBITR_MANAGER_URL = "https://bankrot.fedresurs.ru/ArbitrManagersList.aspx";
    private static final String ORIGINAL_PARSE_URL = "https://bankrot.fedresurs.ru";
    private static final Logger log = LoggerFactory.getLogger(ArbitrManagerParser.class);
    private int startParserRegNumber;
    private int finishParserRegNumber;
    private volatile List<ArbitrManager> arbitrManagers = new ArrayList<>();

    public ArbitrManagerParserThread(int startParserRegNumber, int finishParserRegNumber) {
        this.startParserRegNumber = startParserRegNumber;
        this.finishParserRegNumber = finishParserRegNumber;

    }


    @Override
    public void run() {
        log.info("starting do his work");
        long start = System.currentTimeMillis();
        for (int i = startParserRegNumber; i < finishParserRegNumber; i++) {
            ArbitrManager arbitrManager = new ArbitrManager();
            arbitrManager.setRegNumber(String.valueOf(i));
            String cookieVar = "AmListSearch=SroId=&SroName=&FirstName=&LastName=&MiddleName=&RegNumber=" + i + "&PageNumber=0&WithPublicatedMessages=False; SroList=Name=&RegNumber=&PageNumber=0; bankrotcookie=c5f7cb1791b4afe112f1578e83c9c350; debtorsearch=typeofsearch=Organizations&orgname=%d0%9c%d0%b8%d1%85%d0%b5%d0%b5%d0%b2&orgaddress=&orgregionid=&orgogrn=&orginn=&orgokpo=&OrgCategory=&prslastname=&prsfirstname=&prsmiddlename=&prsaddress=&prsregionid=&prsinn=&prsogrn=&prssnils=&PrsCategory=&pagenumber=0; _ym_d=1543514069; _ym_uid=1543514069537615901;";
            Document arbitrManagersDoc = null;
            try {
                arbitrManagersDoc =
                        Jsoup.connect(PARSE_ARBITR_MANAGER_URL)
                                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; ru-RU; rv1.8.1.6) " +
                                        "Gecko/20070725 Firefox/2.0.0.6")
                                .referrer("https://bankrot.fedresurs.ru/ArbitrManagersList.aspx?attempt=1")
                                .cookie(cookieVar, "ASP.NET_SessionId=zsfwlja2xwy0agqqmgigg3lu")
                                .post();
                log.info("Connect to: {}. This html page have arbitr managers", PARSE_ARBITR_MANAGER_URL);
            } catch (IOException e) {
                log.error("Error connect to {}. Exception: {}. This html page have arbitr managers",
                        PARSE_ARBITR_MANAGER_URL, e);

            }
            Element arbitrManagersName = arbitrManagersDoc.getElementsByAttributeValue("title",
                    "Карточка арбитражного управляющего")
                    .first();
            if (arbitrManagersName == null) continue;
            arbitrManager.setFullName(arbitrManagersName.text());
            arbitrManager.setArbitrManagersDetailsLink(ORIGINAL_PARSE_URL + arbitrManagersName.attr("href"));
            String arbitrManagerDetailsLink = arbitrManager.getArbitrManagersDetailsLink();
            Element arbitrManagerSro =
                    arbitrManagersDoc.getElementsByAttributeValue("title", "Карточка саморегулируемой организации")
                            .first();
            if (arbitrManagerSro == null) arbitrManager.setSro("Не состоит в СРО");
            else arbitrManager.setSro(arbitrManagerSro.text());

            Document arbitrManagerDetailsDoc = null;
            try {
                arbitrManagerDetailsDoc =
                        Jsoup.connect(arbitrManagerDetailsLink)
                                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; ru-RU; rv1.8.1.6) Gecko/20070725" +
                                        " Firefox/2.0.0.6")
                                .post();
                log.info("Arbitr manager name: {}. Connect to: {}. To parse arbitr manager details",
                        arbitrManager.getFullName(), arbitrManagerDetailsLink);
            } catch (IOException e) {
                log.error("Error connect to {}. Exception: {}. This html page have arbitr manager details. His name: {}",
                        arbitrManagerDetailsLink, e, arbitrManager.getFullName());
            }
            Element arbitrManagerRegDate = null;
            try {
                arbitrManagerRegDate = arbitrManagerDetailsDoc
                        .getElementById("ctl00_cphBody_trRegDate").child(1);
            } catch (NullPointerException e) {
                arbitrManager.setRegDate("Даты регистрации нет");
            }
            if (arbitrManagerRegDate != null) arbitrManager.setRegDate(arbitrManagerRegDate.text());


            Elements arbitrManagerMessages = arbitrManagerDetailsDoc.getElementsByAttributeValue("style",
                    "width:27%;");
            for (Element msgEl : arbitrManagerMessages) {
                Element element = msgEl.child(0);
                String messageType = element.text();
                switch (messageType) {
                    case MESSAGE_TORGI_TEXT:
                        MessageTorgi messageTorgi = new MessageTorgi();
                        messageTorgi.setDetailsLink(ORIGINAL_PARSE_URL + getPayloadFromLazyLoad(element.attr("onclick")));
                        String messageTorgiDetailsParseUrl = messageTorgi.getDetailsLink();
                        Document messageTorgiDetailsDoc = null;
                        try {
                            messageTorgiDetailsDoc = Jsoup.connect(messageTorgiDetailsParseUrl)
                                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; ru-RU; " +
                                            "rv1.8.1.6) Gecko/20070725" +
                                            " Firefox/2.0.0.6")
                                    .post();
                            log.info("Arbitr manager name: {}. Connect to: {}. To parse arbitr" +
                                            " manager torgi message details",
                                    arbitrManager.getFullName(), messageTorgiDetailsParseUrl);
                        } catch (IOException e) {
                            log.error("Error connect to {}. Exception: {}. This html page have" +
                                            " arbitr manager torgi message details. Arbitr manager name: {}",
                                    messageTorgiDetailsParseUrl, e, arbitrManager.getFullName());
                        }
                        Element numberOfMessageTorgi = messageTorgiDetailsDoc
                                .getElementsByAttributeValue("class", "even").first().child(1);
                        try {
                            messageTorgi.setNumberOfMessage(Long.parseLong(numberOfMessageTorgi.text()));
                        } catch (NumberFormatException e) {
                            log.info("Cannot to parse number of message long: {}", numberOfMessageTorgi.text());
                        }
                        Element dateOfPublicationMessageTorgi = messageTorgiDetailsDoc
                                .getElementsByAttributeValue("class", "odd").first().child(1);
                        Pattern pattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4}");
                        Matcher matcher = pattern.matcher(dateOfPublicationMessageTorgi.text());
                        if (matcher.matches()) {
                            messageTorgi.setDateOfPublication(dateOfPublicationMessageTorgi.text());
                        } else {
                            log.error("Date of publication string {}. Don't look at pattern",
                                    dateOfPublicationMessageTorgi.text());
                        }
                        Elements publicationTextElements = messageTorgiDetailsDoc
                                .getElementsByAttributeValue("class", "msg");
                        for (Element publicationTextEl : publicationTextElements) {
                            String publicationText = publicationTextEl.text();
                            String[] publicationTextWithoutDoublePoints = publicationText.split(":");
                            if (publicationTextWithoutDoublePoints[0].equals("Текст")) {
                                messageTorgi.setPublicationText(publicationText);
                            }
                        }


                        String debtorName = "";
                        String priceOfferForm = "";
                        String inn = "";
                        String dateTimeAuction = "";
                        String auctionStartDate = "";
                        String auctionFinishedDate = "";
                        String auctionType = "";
                        String debtorAdres = "";
                        String caseNumber = "";
                        String auctionRules = "";
                        String marketPlace = "";
                        Elements evenElements = messageTorgiDetailsDoc.getElementsByAttributeValue("class", "even");
                        Elements oddElements = messageTorgiDetailsDoc.getElementsByAttributeValue("class", "odd");
                        for (Element evenElement : evenElements) {
                            String evenElementText = evenElement.text();
                            if (evenElementText.contains(DEBTOR_FULL_NAME) | evenElementText.contains(DEBTOR_NAME)) {
                                if (evenElementText.contains(DEBTOR_FULL_NAME)) {
                                    for (int j = 1; j < evenElementText.replace(DEBTOR_FULL_NAME, "").toCharArray().length; j++) {
                                        debtorName += evenElementText.replace(DEBTOR_FULL_NAME, "").toCharArray()[j];
                                    }
                                } else if (evenElementText.contains(DEBTOR_NAME)) {
                                    for (int j = 1; j < evenElementText.replace(DEBTOR_NAME, "").toCharArray().length; j++) {
                                        debtorName += evenElementText.replace(DEBTOR_NAME, "").toCharArray()[j];
                                    }
                                }
                            } else if (evenElementText.contains(INN)) {
                                if (evenElementText.split(" ")[0].equals(INN)) {
                                    for (int j = 1; j < evenElementText.replace(INN, "").toCharArray().length; j++) {
                                        inn += evenElementText.replace(INN, "").toCharArray()[j];
                                    }
                                }
                            } else if ((evenElementText.contains(DEBTOR_LEGAL_ADRES) | evenElementText.contains(DEBTOR_PERSON_ADRES))) {
                                if (!evenElementText.contains(DEBTOR_ADRES_SRO_AY) && !evenElementText.contains(ADRES_FOR_KORESPONDATION)) {
                                    if (evenElementText.contains(DEBTOR_LEGAL_ADRES)) {
                                        for (int j = 1; j < evenElementText.replace(DEBTOR_LEGAL_ADRES, "")
                                                .toCharArray().length; j++) {
                                            debtorAdres += evenElementText.replace(DEBTOR_LEGAL_ADRES, "").toCharArray()[j];
                                        }
                                    } else if (evenElementText.contains(DEBTOR_PERSON_ADRES)) {
                                        for (int j = 1; j < evenElementText.replace(DEBTOR_PERSON_ADRES, "")
                                                .toCharArray().length; j++) {
                                            debtorAdres += evenElementText.replace(DEBTOR_PERSON_ADRES, "").toCharArray()[j];
                                        }
                                    }
                                }
                            } else if (evenElementText.contains(CASE_NUMBER)) {

                                for (int j = 1; j < evenElementText.replace(CASE_NUMBER, "").toCharArray().length; j++) {
                                    caseNumber += evenElementText.replace(CASE_NUMBER, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(AUCTION_TYPE)) {
                                for (int j = 1; j < evenElementText.replace(AUCTION_TYPE, "").toCharArray().length; j++) {
                                    auctionType += evenElementText.replace(AUCTION_TYPE, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(AUCTION_START_DATE)) {
                                for (int j = 1; j < evenElementText.replace(AUCTION_START_DATE, "").toCharArray().length; j++) {
                                    auctionStartDate += evenElementText.replace(AUCTION_START_DATE, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(AUCTION_FINISHED_DATE)) {
                                for (int j = 1; j < evenElementText.replace(AUCTION_FINISHED_DATE, "").toCharArray().length; j++) {
                                    auctionFinishedDate += evenElementText.replace(AUCTION_FINISHED_DATE, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(AUCTION_RULES)) {

                                for (int j = 1; j < evenElementText.replace(AUCTION_RULES, "").toCharArray().length; j++) {
                                    auctionRules += evenElementText.replace(AUCTION_RULES, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(DATE_TIME_AUCTION)) {

                                for (int j = 1; j < evenElementText.replace(DATE_TIME_AUCTION, "").toCharArray().length; j++) {
                                    dateTimeAuction += evenElementText.replace(DATE_TIME_AUCTION, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(PRICE_OFFER_FORM)) {

                                for (int j = 1; j < evenElementText.replace(PRICE_OFFER_FORM, "").toCharArray().length; j++) {
                                    priceOfferForm += evenElementText.replace(PRICE_OFFER_FORM, "").toCharArray()[j];
                                }

                            } else if (evenElementText.contains(MARKET_PLACE)) {
                                for (int j = 1; j < evenElementText.replace(MARKET_PLACE, "").toCharArray().length; j++) {
                                    marketPlace += evenElementText.replace(MARKET_PLACE, "").toCharArray()[j];
                                }

                            }
                        }

                        for (Element oddElement : oddElements) {
                            String oddElementText = oddElement.text();
                            if (oddElementText.contains(DEBTOR_FULL_NAME) | oddElementText.contains(DEBTOR_NAME)) {
                                if (oddElementText.contains(DEBTOR_FULL_NAME)) {
                                    for (int j = 1; j < oddElementText.replace(DEBTOR_FULL_NAME, "").toCharArray().length; j++) {
                                        debtorName += oddElementText.replace(DEBTOR_FULL_NAME, "").toCharArray()[j];
                                    }
                                } else if (oddElementText.contains(DEBTOR_NAME)) {
                                    for (int j = 1; j < oddElementText.replace(DEBTOR_NAME, "").toCharArray().length; j++) {
                                        debtorName += oddElementText.replace(DEBTOR_NAME, "").toCharArray()[j];
                                    }
                                }
                            } else if (oddElementText.contains(INN)) {
                                if (oddElementText.split(" ")[0].equals(INN)) {
                                    for (int j = 1; j < oddElementText.replace(INN, "").toCharArray().length; j++) {
                                        inn += oddElementText.replace(INN, "").toCharArray()[j];
                                    }
                                }
                            } else if ((oddElementText.contains(DEBTOR_LEGAL_ADRES) | oddElementText.contains(DEBTOR_PERSON_ADRES))) {
                                if (!oddElementText.contains(DEBTOR_ADRES_SRO_AY) && !oddElementText.contains(ADRES_FOR_KORESPONDATION)) {
                                    if (oddElementText.contains(DEBTOR_LEGAL_ADRES)) {
                                        for (int j = 1; j < oddElementText.replace(DEBTOR_LEGAL_ADRES, "")
                                                .toCharArray().length; j++) {
                                            debtorAdres += oddElementText.replace(DEBTOR_LEGAL_ADRES, "").toCharArray()[j];
                                        }
                                    } else if (oddElementText.contains(DEBTOR_PERSON_ADRES)) {
                                        for (int j = 1; j < oddElementText.replace(DEBTOR_PERSON_ADRES, "")
                                                .toCharArray().length; j++) {
                                            debtorAdres += oddElementText.replace(DEBTOR_PERSON_ADRES, "").toCharArray()[j];
                                        }
                                    }
                                }
                            } else if (oddElementText.contains(CASE_NUMBER)) {
                                for (int j = 1; j < oddElementText.replace(CASE_NUMBER, "").toCharArray().length; j++) {
                                    caseNumber += oddElementText.replace(CASE_NUMBER, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(AUCTION_TYPE)) {
                                for (int j = 1; j < oddElementText.replace(AUCTION_TYPE, "").toCharArray().length; j++) {
                                    auctionType += oddElementText.replace(AUCTION_TYPE, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(AUCTION_START_DATE)) {

                                for (int j = 1; j < oddElementText.replace(AUCTION_START_DATE, "").toCharArray().length; j++) {
                                    auctionStartDate += oddElementText.replace(AUCTION_START_DATE, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(AUCTION_FINISHED_DATE)) {
                                for (int j = 1; j < oddElementText.replace(AUCTION_FINISHED_DATE, "").toCharArray().length; j++) {
                                    auctionFinishedDate += oddElementText.replace(AUCTION_FINISHED_DATE, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(AUCTION_RULES)) {
                                for (int j = 1; j < oddElementText.replace(AUCTION_RULES, "").toCharArray().length; j++) {
                                    auctionRules += oddElementText.replace(AUCTION_RULES, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(DATE_TIME_AUCTION)) {

                                for (int j = 1; j < oddElementText.replace(DATE_TIME_AUCTION, "").toCharArray().length; j++) {
                                    dateTimeAuction += oddElementText.replace(DATE_TIME_AUCTION, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(PRICE_OFFER_FORM)) {

                                for (int j = 1; j < oddElementText.replace(PRICE_OFFER_FORM, "").toCharArray().length; j++) {
                                    priceOfferForm += oddElementText.replace(PRICE_OFFER_FORM, "").toCharArray()[j];
                                }

                            } else if (oddElementText.contains(MARKET_PLACE)) {
                                for (int j = 1; j < oddElementText.replace(MARKET_PLACE, "").toCharArray().length; j++) {
                                    marketPlace += oddElementText.replace(MARKET_PLACE, "").toCharArray()[j];
                                }

                            }
                        }

                        messageTorgi.setMarketPlace(marketPlace);
                        messageTorgi.setPriceOfferForm(priceOfferForm);
                        messageTorgi.setDateTimeAuction(dateTimeAuction);
                        messageTorgi.setAuctionRules(auctionRules);
                        messageTorgi.setAuctionStartDate(auctionStartDate);
                        messageTorgi.setAuctionFinishedDate(auctionFinishedDate);
                        messageTorgi.setAuctionType(auctionType);
                        messageTorgi.setSro(arbitrManager.getSro());
                        messageTorgi.setCaseNumber(caseNumber);
                        messageTorgi.setDebtorAdres(debtorAdres);
                        messageTorgi.setInn(inn);
                        messageTorgi.setDebtorName(debtorName);

                        Element lotDetailsTable = messageTorgiDetailsDoc.getElementsByAttributeValue("class", "lotInfo")
                                .first();

                        Elements rows = lotDetailsTable.select("tr");

                        for (int j = 1; j < rows.size(); j++) {
                            MessageTorgiDetails messageTorgiDetails = new MessageTorgiDetails();
                            Element row = rows.get(j);
                            Elements cols = row.select("td");
                            System.out.print(cols.get(0).text());
                            System.out.print(cols.get(1).text());
                            System.out.print(cols.get(2).text());
                            System.out.print(cols.get(3).text());
                            System.out.print(cols.get(4).text());
                            System.out.print(cols.get(5).text());
                            System.out.print(cols.get(6).text());
                            System.out.println();
                        }
                        arbitrManager.addMessageTorgiToArbitrManager(messageTorgi);
                        break;
                    case MESSAGE_INVENTORY_TEXT:
                        break;
                    case MESSAGE_VALUATION_TEXT:
                        break;
                    case MESSAGE_PROCEDURE_TEXT:
                        break;
                    default:
                        break;
                }
            }
            arbitrManagers.add(arbitrManager);
        }
        System.out.println(" finish his work in " + (System.currentTimeMillis() - start));

    }

    private String getPayloadFromLazyLoad(String jsActive) {
        String payload = "";
        char[] jsActiveChars = jsActive.toCharArray();
        for (int i = 12; i < jsActiveChars.length; i++) {
            if (jsActiveChars[i] == '\'') break;
            payload += jsActiveChars[i];
        }
        return payload;
    }

    public List<ArbitrManager> getArbitrManagers() {
        return arbitrManagers;
    }
}
