package ru.yakunin.efrsbhtmlparser.service;

import org.springframework.stereotype.Service;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgi;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgiDetails;
import ru.yakunin.efrsbhtmlparser.repository.MessageTorgiRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MessageTorgiService {
    private final MessageTorgiRepository messageTorgiRepository;

    public MessageTorgiService(MessageTorgiRepository messageTorgiRepository) {
        this.messageTorgiRepository = messageTorgiRepository;
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
        List<MessageTorgiDetails> origialMessageTories;
        if (searchByLotDesWord.equals("") && town.equals("") && region.equals(""))
            origialMessageTories = messageTorgiRepository.findAll();
        else origialMessageTories = searchByLotDes(searchByLotDesWord,
                messageTorgiRepository.findAll());
        if (!town.equals("")) {
            origialMessageTories.removeIf(m -> !m.getTown().equals(town));
        }
        if (!region.equals("")) {
            origialMessageTories.removeIf(m -> !m.getRegion().equals(region));
        }

        List<MessageTorgi> messageTorgiList = new ArrayList<>();
        for (MessageTorgi msgTr : origialMessageTories) {
            MessageTorgiDetails msgTrDet = msgTr.getMessageTorgiDetails();
            char[] lotDesSym = msgTrDet.getLotDescription().toCharArray();
            char[] priceDecInSym = msgTrDet.getPriceDecreasingInfo().toCharArray();
            String lotDes = "";
            String priceDecInfo = "";
            for (int i = 0; i < 107; i++) {
                if (lotDesSym.length < 110) {
                    lotDes = msgTrDet.getLotDescription();
                    break;
                }
                lotDes += lotDesSym[i];
            }

            for (int i = 0; i < 107; i++) {
                if (priceDecInSym.length < 110) {
                    priceDecInfo = msgTrDet.getPriceDecreasingInfo();
                    break;
                }
                priceDecInfo += priceDecInSym[i];
            }
            lotDes += "...";
            priceDecInfo += "...";
            msgTrDet.setPriceDecreasingInfo(priceDecInfo);
            msgTrDet.setLotDescription(lotDes);
            msgTr.setMessageTorgiDetails(msgTrDet);
            messageTorgiList.add(msgTr);
        }
        return messageTorgiList;
    }

    private List<MessageTorgi> searchByLotDes(String searchByLotDesWord,
                                              List<MessageTorgi> messageTorgis) {
        List<MessageTorgi> searchedMessageTorgies = new ArrayList<>();
        String lowerSearchWord = makeStringToLowerCase(searchByLotDesWord);
        char[] searchWordToCharArray = lowerSearchWord.toCharArray();
        for (int a = 0; a < messageTorgis.size(); a++) {
            String lowerLotDec = makeStringToLowerCase(messageTorgis.get(a)
                    .getMessageTorgiDetails().getLotDescription());
            char[] chars = lowerLotDec.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < searchWordToCharArray.length; j++) {
                    try {
                        if (chars[i] == searchWordToCharArray[j]) {
                            if (chars[i + 1] == searchWordToCharArray[j + 1]) {
                                if (chars[i + 2] == searchWordToCharArray[j + 2]) {
                                    if (chars[i + 3] == searchWordToCharArray[j + 3]) {
                                        if (!searchedMessageTorgies.contains(messageTorgis.get(a))) {
                                            searchedMessageTorgies.add(messageTorgis.get(a));
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
        return searchedMessageTorgies;
    }

    public List<MessageTorgi> getMessagesByArbitrManager(ArbitrManager arbitrManager) {
        List<MessageTorgi> messageTorgiList = new ArrayList<>();
        for (MessageTorgi msgTr : messageTorgiRepository.findAllByArbitrManager(arbitrManager)) {
            MessageTorgiDetails msgTrDet = msgTr.getMessageTorgiDetails();
            char[] lotDesSym = msgTrDet.getLotDescription().toCharArray();
            char[] priceDecInSym = msgTrDet.getPriceDecreasingInfo().toCharArray();
            String lotDes = "";
            String priceDecInfo = "";
            for (int i = 0; i < 107; i++) {
                lotDes += lotDesSym[i];
            }

            for (int i = 0; i < 107; i++) {
                priceDecInfo += priceDecInSym[i];
            }
            lotDes += "...";
            priceDecInfo += "...";
            msgTrDet.setPriceDecreasingInfo(priceDecInfo);
            msgTrDet.setLotDescription(lotDes);
            msgTr.setMessageTorgiDetails(msgTrDet);
            messageTorgiList.add(msgTr);
        }
        return messageTorgiList;
    }
}
