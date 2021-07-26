package ru.yakunin.efrsbhtmlparser.service;

import org.springframework.stereotype.Service;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgi;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgiDetails;
import ru.yakunin.efrsbhtmlparser.repository.MessageTorgiDetailsRepository;
import ru.yakunin.efrsbhtmlparser.repository.MessageTorgiRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageTorgiService {
    private final MessageTorgiRepository messageTorgiRepository;
    private final MessageTorgiDetailsRepository messageTorgiDetailsRepository;

    public MessageTorgiService(MessageTorgiRepository messageTorgiRepository,
                               MessageTorgiDetailsRepository messageTorgiDetailsRepository) {
        this.messageTorgiRepository = messageTorgiRepository;
        this.messageTorgiDetailsRepository = messageTorgiDetailsRepository;
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

    public List<MessageTorgiDetails> getAllMessageTorgiDetails(String searchByLotDesWord,
                                                               String town, String region) {
        List<MessageTorgiDetails> origialMessageToriesDetails = null;
        if (searchByLotDesWord.equals("") && town.equals("") && region.equals(""))
            origialMessageToriesDetails = messageTorgiDetailsRepository.findAll();
        if (!town.equals("")) {
            origialMessageToriesDetails.removeIf(m -> !m.getTown().equals(town));
        }
        if (!region.equals("")) {
            origialMessageToriesDetails.removeIf(m -> !m.getRegion().equals(region));
        }
        if (!searchByLotDesWord.equals("")) {
            origialMessageToriesDetails = searchByLotDes(searchByLotDesWord, messageTorgiDetailsRepository.findAll());
        }

        List<MessageTorgiDetails> messageTorgiDetailsList = new ArrayList<>();
        for (MessageTorgiDetails msgTrDet : origialMessageToriesDetails) {
            char[] lotDesSym = msgTrDet.getLotDescription().toCharArray();
            char[] priceDecInSym = null;
            String priceDecInfo = "";
            try {
                priceDecInSym = msgTrDet.getPriceDecreasingInfo().toCharArray();
            } catch (NullPointerException e) {
                priceDecInfo = "-";
            }
            String lotDes = "";

            for (int i = 0; i < 107; i++) {
                if (lotDesSym.length < 110) {
                    lotDes = msgTrDet.getLotDescription();
                    break;
                }
                lotDes += lotDesSym[i];
            }
            if (!priceDecInfo.equals("-")) {
                for (int i = 0; i < 107; i++) {
                    if (priceDecInSym.length < 110) {
                        priceDecInfo = msgTrDet.getPriceDecreasingInfo();
                        break;
                    }
                    priceDecInfo += priceDecInSym[i];
                }
                priceDecInfo += "...";
            }
            lotDes += "...";
            msgTrDet.setPriceDecreasingInfo(priceDecInfo);
            msgTrDet.setLotDescription(lotDes);
            messageTorgiDetailsList.add(msgTrDet);
        }
        return messageTorgiDetailsList;
    }

    private List<MessageTorgiDetails> searchByLotDes(String searchByLotDesWord,
                                              List<MessageTorgiDetails> messageTorgiDetails) {
        List<MessageTorgiDetails> searchedMessageTorgiDetails = new ArrayList<>();
        String lowerSearchWord = makeStringToLowerCase(searchByLotDesWord);
        char[] searchWordToCharArray = lowerSearchWord.toCharArray();
        for (int a = 0; a < messageTorgiDetails.size(); a++) {
            String lowerLotDec = messageTorgiDetails.get(a).getLotDescription();
            char[] chars = lowerLotDec.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < searchWordToCharArray.length; j++) {
                    try {
                        if (chars[i] == searchWordToCharArray[j]) {
                            if (chars[i + 1] == searchWordToCharArray[j + 1]) {
                                if (chars[i + 2] == searchWordToCharArray[j + 2]) {
                                    if (chars[i + 3] == searchWordToCharArray[j + 3]) {
                                        if (!searchedMessageTorgiDetails.contains(messageTorgiDetails.get(a))) {
                                            searchedMessageTorgiDetails.add(messageTorgiDetails.get(a));
                                            break;
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
        return searchedMessageTorgiDetails;
    }
//
//    public List<MessageTorgiDetails> getMessagesByArbitrManager(ArbitrManager arbitrManager) {
//        List<MessageTorgiDetails> messageTorgiDetailsList = new ArrayList<>();
//        for (MessageTorgiDetails msgTrDet : messageTorgiDetailsRepository.findAllByArbitrManager(arbitrManager)) {
//            MessageTorgiDetails msgTrDet = msgTr.getMessageTorgiDetails();
//            char[] lotDesSym = msgTrDet.getLotDescription().toCharArray();
//            char[] priceDecInSym = msgTrDet.getPriceDecreasingInfo().toCharArray();
//            String lotDes = "";
//            String priceDecInfo = "";
//            for (int i = 0; i < 107; i++) {
//                lotDes += lotDesSym[i];
//            }
//
//            for (int i = 0; i < 107; i++) {
//                priceDecInfo += priceDecInSym[i];
//            }
//            lotDes += "...";
//            priceDecInfo += "...";
//            msgTrDet.setPriceDecreasingInfo(priceDecInfo);
//            msgTrDet.setLotDescription(lotDes);
//            msgTr.setMessageTorgiDetails(msgTrDet);
//            messageTorgiDetailsList.add(msgTr);
//        }
//        return messageTorgiDetailsList;
//    }
}
