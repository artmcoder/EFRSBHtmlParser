package ru.yakunin.efrsbhtmlparser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgi;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgiDetails;
import ru.yakunin.efrsbhtmlparser.repository.ArbitrManagerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


@Service
public class ArbitrManagerService {
    public static final Logger log = LoggerFactory.getLogger(ArbitrManagerService.class);
    private final ArbitrManagerRepository arbitrManagerRepository;

    public ArbitrManagerService(ArbitrManagerRepository arbitrManagerRepository) {
        this.arbitrManagerRepository = arbitrManagerRepository;
    }

    public static String makeStringToLowerCase(String word) {
        String lowerString = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            lowerString += Character.toLowerCase(c);
        }
        return lowerString;
    }

    public List<ArbitrManager> getAll(String sortedType, String searchWord) {
        List<ArbitrManager> arbitrManagers;
        List<ArbitrManager> arbitrManagersFromDb;
        if (searchWord.equals("")) arbitrManagersFromDb = arbitrManagerRepository.findAll();
        else arbitrManagersFromDb = searchByFullName(arbitrManagerRepository.findAll(), searchWord);
        switch (sortedType) {
            case "msg-high-to-low":
                arbitrManagers = sortedByNumberOfMessages(arbitrManagersFromDb,
                        "high-to-low");
                break;
            case "msg-low-to-high":
                arbitrManagers = sortedByNumberOfMessages(arbitrManagersFromDb,
                        "low-to-high");
                break;
            default:
                return arbitrManagersFromDb;
        }
        return arbitrManagers;
    }

    private List<ArbitrManager> sortedByNumberOfMessages(List<ArbitrManager> arbitrManagers,
                                                         String sortedType) {
        boolean isSorted = false;
        ArbitrManager buf;
        List<Integer> sortedAttempts = new ArrayList<>();
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < arbitrManagers.size() - 1; i++) {
                if (sortedType.equals("high-to-low")) {
                    if (arbitrManagers.get(i).getMessagesQuantity()
                            < arbitrManagers.get(i + 1).getMessagesQuantity()) {
                        isSorted = false;
                        sortedAttempts.add(1);
                        buf = arbitrManagers.get(i);
                        arbitrManagers.set(i, arbitrManagers.get(i + 1));
                        arbitrManagers.set(i + 1, buf);
                    }
                } else if (sortedType.equals("low-to-high")) {
                    if (arbitrManagers.get(i).getMessagesQuantity()
                            > arbitrManagers.get(i + 1).getMessagesQuantity()) {
                        isSorted = false;
                        sortedAttempts.add(1);
                        buf = arbitrManagers.get(i);
                        arbitrManagers.set(i, arbitrManagers.get(i + 1));
                        arbitrManagers.set(i + 1, buf);
                    }
                }
            }
        }
        log.info("Sorting was successful in {} attempts", sortedAttempts.size());
        return arbitrManagers;
    }

    public List<ArbitrManager> searchByFullName(List<ArbitrManager> arbitrManagers,
                                                String searchWord) {
        List<ArbitrManager> searchedArbitrManagers = new ArrayList<>();
        String lowerSearchWord = makeStringToLowerCase(searchWord);
        char[] searchWordToCharArray = lowerSearchWord.toCharArray();
        for (int a = 0; a < arbitrManagers.size(); a++) {
            String lowerFullName = makeStringToLowerCase(arbitrManagers.get(a).getFullName());
            char[] chars = lowerFullName.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < searchWordToCharArray.length; j++) {
                    try {
                        if (chars[i] == searchWordToCharArray[j]) {
                            if (chars[i + 1] == searchWordToCharArray[j + 1]) {
                                if (chars[i + 2] == searchWordToCharArray[j + 2]) {
                                    if (chars[i + 3] == searchWordToCharArray[j + 3]) {
                                        if (!searchedArbitrManagers.contains(arbitrManagers.get(a))) {
                                            searchedArbitrManagers.add(arbitrManagers.get(a));
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
        return searchedArbitrManagers;
    }



    public void saving100RandomArbitrManagers() {
        final int minRegNumber = 10000;
        final int maxRegNumber = 99999;
        final int minSro = 1000;
        final int maxSco = 9999;
        final int minMessageQuantity = 0;
        final int maxMessageQuantity = 30000;
        Random random = new Random();
        List<String> names = new ArrayList<>();
        names.add("Пётр");
        names.add("Даниил");
        names.add("Артём");
        names.add("Александр");
        names.add("Богдан");
        names.add("Роман");
        names.add("Николай");
        names.add("Иван");
        List<String> surnames = new ArrayList<>();
        surnames.add("Бахтияров");
        surnames.add("Рогов");
        surnames.add("Иванов");
        surnames.add("Петров");
        surnames.add("Сидоров");
        surnames.add("Некрасов");
        surnames.add("Валеев");
        surnames.add("Лехачёв");
        surnames.add("Матвеев");
        surnames.add("Рудской");
        surnames.add("Якунин");
        surnames.add("Зубарев");
        surnames.add("Коконов");
        surnames.add("Бривнев");
        surnames.add("Шелягин");
        List<String> debtorAdreses = new ArrayList<>();
        debtorAdreses.add("Устиновича");
        debtorAdreses.add("Водопьяного");
        debtorAdreses.add("Тельмана");
        debtorAdreses.add("Карла-Маркса");
        debtorAdreses.add("Центральная");
        List<String> patronymics = new ArrayList<>();
        patronymics.add("Артёмович");
        patronymics.add("Александрович");
        patronymics.add("Михайлович");
        patronymics.add("Николаевич");
        patronymics.add("Семёнович");
        patronymics.add("Петрович");
        patronymics.add("Дмитреивич");
        List<String> regDates = new ArrayList<>();
        regDates.add("12.04.2009");
        regDates.add("02.02.2008");
        regDates.add("22.08.2011");
        regDates.add("14.09.2012");
        regDates.add("25.12.2013");
        regDates.add("16.11.2014");
        regDates.add("31.10.2015");
        regDates.add("05.09.2008");
        regDates.add("06.08.2015");
        regDates.add("03.07.2016");
        regDates.add("01.06.2014");
        regDates.add("02.05.2019");
        regDates.add("06.04.2018");
        regDates.add("12.03.2020");
        regDates.add("17.02.2012");
        regDates.add("07.01.2008");
        regDates.add("08.11.2009");
        regDates.add("09.10.2014");
        regDates.add("19.09.2007");
        regDates.add("27.04.2009");
        List<String> lotClassifications = new ArrayList<>();
        lotClassifications.add("Недвизимость");
        lotClassifications.add("Авто");
        lotClassifications.add("Оборудование");
        lotClassifications.add("Земельные участки");
        lotClassifications.add("Ценные бумаги");
        List<String> auctionRules = new ArrayList<>();
        auctionRules.add("Для участия в торгах необходимо зарегистрироваться на Электронной " +
                "площадке ЭСП по адресу: http://el-torg.com/ и предоставить заявку" +
                " на участие в торгах в срок с 05.06.2021 по 12.07.2021 г. в режиме работы ЭТП.");
        auctionRules.add("К участию в торгах допускаются физ. и юр. лица, своевременно подавшие " +
                "надлежащим образом оформленную заявку на участие в торгах и оплатившие задаток.");
        auctionRules.add("К заявке должны прилагаться: 1) выписка из ЕГРЮЛ (для юр. лица); выписка из " +
                "ЕГРИП (для ИП); копии документов, удостоверяющих личность (для физ. лица); ");
        List<String> marketPlases = new ArrayList<>();
        marketPlases.add("METS");
        marketPlases.add("SELT");
        marketPlases.add("ETPRF");
        marketPlases.add("VETP");
        marketPlases.add("YTENDER");
        int[] auctionType = {1, 0, 1};
        List<Double> startPrices = new ArrayList<>();
        startPrices.add(3000000.00);
        startPrices.add(4000000.00);
        startPrices.add(3500000.00);
        startPrices.add(1200000.00);
        startPrices.add(1800000.00);
        startPrices.add(2000000.00);
        startPrices.add(2200000.00);
        startPrices.add(3100000.00);
        List<String> steps = new ArrayList<>();
        steps.add("10%");
        steps.add("15%");
        steps.add("5%");
        steps.add("20%");
        List<String> lotDescriptions = new ArrayList<>();
        lotDescriptions.add("Нежилое помещение, к/н 42:15:0101001:288, право аренды на Земельный участок, к/н 42:15:0103006:0051, расположенные по адресу: Кемеровская область-Кузбасс, р-н Тяжинский, пгт Тяжинский, ул. Новогаражная, д 1, стоимость 1 732 000 руб.");
        lotDescriptions.add("Здание, к/н 42:13:0109003:1093, Здание, к/н 42:13:0109003:1095, Право аренды на Земельный участок, к/н 42:13:0109003:0112, расположенные по адресу: Кемеровская область – Кузбасс, р-н Тисульский, пгт Тисуль, ул. Октябрьская, д 44");
        lotDescriptions.add("квартира № 1 по адресу: Вологодская обл., Шекснинский р-н., п. Шексна, ул. Труда, д. 35а; кадастровый № 35:23:0205019:595, площадь 19,8 кв. м.");
        lotDescriptions.add("Нежилое помещение; цокольный этаж; площадь 186,20 кв.м; адрес: Ярославская область, г. Ярославль, ул. Республиканская, д. 6, пом. 1-17, кад.№ 76:23:050209:299");
        for (int i = 0; i < 50; i++) {
            String fullName = surnames.get(random.nextInt(surnames.size())) + " " +
                    names.get(random.nextInt(names.size())) + " "
                    + patronymics.get(random.nextInt(patronymics.size()));
            String regNumber = String.valueOf((int) (Math.random() *
                    (maxRegNumber - minRegNumber + 1) + minRegNumber));
            String regDate = regDates.get(random.nextInt(regDates.size()));
            String sco = String.valueOf((int) (Math.random() * (maxSco - minSro + 1) + minSro));
            int messageQuantity = (int) (Math.random() *
                    (maxMessageQuantity - minMessageQuantity + 1) + minMessageQuantity);

            ArbitrManager arbitrManager = new ArbitrManager(fullName,
                    regNumber, regDate, sco, messageQuantity); //*


            for (int j = 0; j < 4; j++) {
                String[] openOrClose = {"Open", "Close"};
                String debtorName;
                if (random.nextInt(2) == 1) {
                    debtorName = "ИП " + surnames.get(random.nextInt(surnames.size()));
                } else {
                    debtorName = surnames.get(random.nextInt(surnames.size())) + " " +
                            names.get(random.nextInt(names.size())) + " "
                            + patronymics.get(random.nextInt(patronymics.size()));
                }

                String debtorAdres = debtorAdreses.get(random.nextInt(debtorAdreses.size())) + " " +
                        random.nextInt(100);
                String inn = String.valueOf((int) (Math.random() * (maxSco - minSro + 1) + minSro));
                String messageSco = String.valueOf((int) (Math.random() *
                        (maxSco - minSro + 1) + minSro));
                MessageTorgi messageTorgi = new MessageTorgi();
                messageTorgi.setDateOfPublication(regDates.get(random.nextInt(regDates.size())));
                messageTorgi.setDebtorName(debtorName);
                messageTorgi.setDebtorAdres(debtorAdres);
                messageTorgi.setInn(inn);
                messageTorgi.setPublicationText("Организатор торгов – финансовый управляющий" +
                        " Джабарова Вугара Мехйеддин оглы (дата рождения: 10.12.1974 г., место рождения: " +
                        "Азербайджанская ССР гор. Хачмас, СНИЛС: 104-303-929-06, ИНН: 861704950000, мест" +
                        "о жительства: 628456, Ханты-Мансийский автономный округ-Югра, Сургутский район, пгт." +
                        " Федоровский, пер. Центральный, д. 13, кв. 107, решением Арбитражного суда Ханты-Мансийского" +
                        " автономного округа-Югры от 08.12.2020 г. по делу № А75-16912/2020 введена процедура" +
                        " реализации имущества) – Воробей Ольга Владимировна (ИНН 860223384449, СНИЛС 133-239-018-26," +
                        "почтовый адрес: 628402, Ханты-Мансийский автономный округ - Югра, г. Сургут, а/я 1736, адрес " +
                        "электронной почты: vorobey-olga@bk.ru, контактный номер 8 982 514 8559) - член ААУ \"Солидарность\" " +
                        "(ОГРН 1138600001737, ИНН 8604999157, адрес: 628305, Ханты-Мансийский автономный округ - Югра, г. Нефтеюганск, " +
                        "ул. Жилая, промзона Пионерная, строение 13, оф. 205), сообщает о продаже имущества Джабарова Вугара Мехйеддин" +
                        " оглы на электронных торгах в форме публичного предложения с открытой формой представления предложений о цене." +
                        " Предметом торгов является следующее имущество: лот № 1: Легковой автомобиль, ХЕНДЭ ELANTRA 1,6 GLS, 2004 года " +
                        "выпуска, VIN: KMHDN41BP5U012726, цвет: синий, СТС 99 22 833875, ПТС 86 РМ 321646, начальная цена продажи 171" +
                        " 000 руб. Аукцион проводится на электронной площадке ООО «МЭТС», размещенной в сети Интернет по адресу:" +
                        " www.m-ets.ru. Срок действия публичного предложения 35 календарных дней. Приём заявок производятся с " +
                        "00:00 час. 03.06.2021 г. по 00:00 час. 08.07.2021 г. (здесь и далее по тексту – время московское)." +
                        " Заявка на участие в торгах направляется с помощью программно-аппаратных средств сайта электронной " +
                        "площадки, в форме электронного сообщения, подписанного квалифицированной электронной подписью заявителя" +
                        "и должна содержать предложение о цене имущества Должника, которая не ниже установленной начальной цены" +
                        " продажи. Заявка на участие в торгах составляется в произвольной форме на русском языке и должна содержать " +
                        "следующие сведения: 1) наименование, организационно-правовую форму, место нахождения, почтовый адрес" +
                        " заявителя (для юридического лица), фамилию, имя, отчество, паспортные данные, сведения о месте жительства" +
                        " заявителя (для физического лица); 2) номер контактного телефона, адрес электронной почты заявителя; 3) " +
                        "сведения о наличии или об отсутствии заинтересованности заявителя по отношению к должнику, кредиторам, а" +
                        "рбитражному управляющему и о характере этой заинтересованности, сведения об участии в капитале заявителя " +
                        "арбитражного управляющего, а также саморегулируемой организации арбитражных управляющих, членом или руководи" +
                        "телем которой является арбитражный управляющий. В целях участия в торгах заявитель должен перечислить задато" +
                        "к по лоту № 1 в размере 10 % начальной цены продажи лота установленной для определенного периода проведения" +
                        " торгов, который должен поступить на счет не позднее даты окончания приема заявок определенного периода п" +
                        "роведения торгов на расчетный счет Оператора электронной площадки по следующим реквизитам: ООО «МЭТС», юр." +
                        " адрес: 302030, г. Орел, ул. Новосильская, д. 11, помещение 4; ИНН 5751039346; КПП 575101001; ОГРН 1105742" +
                        "000858; р/счет 40702810900047305402; Банк: ф-л Банка ГПБ (АО) «Среднерусский», г. Тула, 300026, г. Тула, пр-т" +
                        " Ленина, 106, ОГРН 1027700167110, к/счет 30101810700000000716, БИК 047003716, ИНН 7744001497, КПП 710402001. " +
                        "Назначение платежа: «Задаток для участия в торгах (пополнение лицевого счета) (ID___)», где после ID указывае" +
                        "тся номер лицевого счета участника на площадке. Решение организатора торгов о допуске заявителей к участию в " +
                        "торгах принимается по результатам рассмотрения представленных заявок на участие в торгах и оформляется прото" +
                        "колом об определении участников торгов. К участию в торгах допускаются заявители, представившие заявки на уч" +
                        "астие в торгах, которые соответствуют требованиям, установленным Федеральным законом о банкротстве и указанны" +
                        "м в сообщении о проведении торгов. Заявители, допущенные к участию в торгах, признаются участниками торгов. Тор" +
                        "ги проводятся путем понижения начальной цены продажи имущества на «шаг аукциона», который составляет 10 % от " +
                        "начальной цены продажи лота № 1. При отсутствии в течение 7 календарных дней заявки на участие в торгах путем п" +
                        "убличного предложения, содержащей предложение о цене имущества Должника, которая не ниже установленной начально" +
                        "й цены продажи, цена продажи подлежит снижению каждые 7 календарных дней. Право приобретения имущества должник" +
                        "а принадлежит участнику торгов по продаже имущества должника посредством публичного предложения, который предст" +
                        "авил в установленный срок заявку на участие в торгах, содержащую предложение о цене имущества должника, которая" +
                        " не ниже начальной цены продажи имущества должника, установленной для определенного периода проведения торгов, " +
                        "при отсутствии предложений других участников торгов по продаже имущества должника посредством публичного \n" +
                        "\n" +
                        "АО «Российский аукционный дом» (ОГРН 1097847233351, ИНН 7838430413, 190000, Санкт-Петербург, пер. Гривцова," +
                        " д. 5, лит.В, (812)334-26-04, 8(800) 777-57-57, malkova@auction-house.ru) (далее - Организатор торгов, ОТ)," +
                        " действующее на основании договора поручения с Государственной корпорацией «Агентство по страхованию вкладов» " +
                        "(109240, г. Москва, ул. Высоцкого, д. 4), являющейся на основании Решения Арбитражного суда г. Москвы от 14 ию" +
                        "ня 2016 г. по делу №А40-66603/16-44-111 Б конкурсным управляющим (ликвидатором) «Национальным Корпоративным Б" +
                        "анком» (акционерное общество) («НАЦКОРПБАНК» (АО)), адрес регистрации: 123056, Москва, Малый Тишинский переулок" +
                        ", дом 23, строение 1, ОГРН: 1027744002989, ИНН: 7744002821, КПП: 771001001) (далее – КУ) (далее – финансовая ор" +
                        "ганизация), проводит электронные торги имуществом финансовой организации:\n" +
                        "в форме открытого аукциона с открытой формой представления предложений по цене приобретения по лоту 2 (далее - Торги);\n" +
                        "посредством публичного предложения по лотам 1,2 (далее - Торги ППП).\n" +
                        "Предметом Торгов/Торгов ППП является следующее имущество:\n" +
                        "Недвижимое имущество:\n" +
                        "Лот 1 - Земельные участки - 12 028 +/- 960 кв. м, 17 732 +/- 1 165 кв. м, 48 038 +/- 1 918 кв. м, адрес: См" +
                        "оленская обл., Гагаринский р-н, Баскаковское с/п, в границах ТСОО \"Баскаково\", с правой стороны а/д Гага" +
                        "рин-Баскаково, восточнее д. Плоское, кадастровые номера 67:03:0020201:979, 67:03:0020201:980, земли с/х назна" +
                        "чения - производство с/х продукции, кадастровый номер 67:03:0020201:981, земли промышленности, энергетики, тра" +
                        "нспорта, связи, радиовещания, телевидения, информатики, земли для обеспечения космической деятельности, земли " +
                        "обороны, безопасности и земли иного специального назначения - для разведки и добычи песчаных отложений, произво" +
                        "дства строительных материалов - 6 592 770,00 руб.\n" +
                        "Основные средства:\n" +
                        "Лот 2 - Архивный комплекс, cтойка операционистов, Московская обл., г. Видное - 416 083,04 руб.\n" +
                        "Лот 1 реализуется с учетом ограничений, установленных Федеральным законом от 24.07.2002 г. №101-ФЗ «Об оборот" +
                        "е земель сельскохозяйственного назначения», в соответствии с которым высший исполнительный орган государственн" +
                        "ой власти субъекта РФ, орган местного самоуправления по месту нахождения земельных участков обладает преимуще" +
                        "ственным правом приобретения и, в случае его участия в торгах, земельные участки будут проданы с учетом данного преимуще" +
                        "ственного права.\n" +
                        "С подробной информацией о составе лотов финансовой организации можно ознакомиться на сайте ОТ http://www.auct" +
                        "ion-house.ru/, также www.asv.org.ru, www.torgiasv.ru в разделах «Ликвидация Банков» и «Продажа имущества».\n" +
                        "Торги проводятся путем повышения начальной цены продажи предмета Торгов (лота) на величину, кратную величине шага а" +
                        "укциона. Шаг аукциона –5 (Пять) процентов от начальной цены продажи предмета Торгов (лота).\n" +
                        "Торги имуществом финансовой организации будут проведены в 14:00 часов по московскому времени 12 апреля 2021 г. н" +
                        "а электронной площадке АО «Российский аукционный дом» по адресу: http://lot-online.ru (далее – ЭТП).\n" +
                        "Время окончания Торгов:\n" +

                        "- по истечении 1 часа с начала Торгов, если не поступило ни одного предложения о цене предмета Торгов (лота) после " +
                        "начала Торгов;\n" +
                        "- по истечении 30 минут, если после представления последнего предложения о цене предмета Торгов (лота) не поступило с" +
                        "ледующее предложение о цене предмета Торгов (лота).\n" +
                        "В случае, если по итогам Торгов, назначенных на 12 апреля 2021 г., лоты не реализованы, то в 14:00 часов по московско" +
                        "му времени 31 мая 2021 г. на ЭТП будут проведены повторные Торги нереализованными лотами со снижением начальной цены " +
                        "лотов на 10 (Десять) процентов.\n" +
                        "Оператор ЭТП (далее – Оператор) обеспечивает проведение Торгов.\n" +
                        "Прием Оператором заявок и предложений о цене приобретения имущества финансовой организации на участие в первых Торга" +
                        "х начинается в 00:00 часов по московскому времени 02 марта 2021 г., а на участие в повторных Торгах начинается в 00:0" +
                        "0 часов по московскому времени 19 апреля 2021 г. Прием заявок на участие в Торгах и задатков прекращается в 14:00 ч" +
                        "асов по московскому времени за 5 (Пять) календарных дней до даты проведения соответствующих Торгов.\n" +
                        "На основании п. 4 ст. 139 Федерального закона № 127-ФЗ «О несостоятельности (банкротстве)» лот 2, не реализо" +
                        "ванный на повторных Торгах, а также лот 1 выставляются на Торги ");
                messageTorgi.setCaseNumber("A14-542864/13");
                messageTorgi.setSro(messageSco);
                messageTorgi.setAuctionType(String.valueOf(auctionType[random.nextInt(auctionType.length)]));
                messageTorgi.setAuctionStartDate(regDates.get(random.nextInt(regDates.size())));
                messageTorgi.setAuctionFinishedDate(regDates.get(random.nextInt(regDates.size())));
                messageTorgi.setAuctionRules(auctionRules.get(random.nextInt(auctionRules.size())));
                messageTorgi.setDateTimeAuction(regDates.get(random.nextInt(regDates.size())));
                messageTorgi.setPriceOfferForm(openOrClose[random.nextInt(openOrClose.length)]);
                messageTorgi.setMarketPlace(marketPlases.get(random.nextInt(marketPlases.size())));

                MessageTorgiDetails messageTorgiDetails = new MessageTorgiDetails();
                messageTorgiDetails.setLotNumber(String.valueOf(random.nextInt(100)));
                messageTorgiDetails.setLotDescription(lotDescriptions.get(random.nextInt(lotDescriptions.size())));
                messageTorgiDetails.setStartPrice(String.valueOf(startPrices.get(random.nextInt(startPrices.size()))));
                if (random.nextInt(3) == 1) {
                    messageTorgiDetails.setStep("-");
                } else {
                    messageTorgiDetails.setStep(steps.get(random.nextInt(steps.size())));
                }
                messageTorgiDetails.setDeposit(steps.get(random.nextInt(steps.size())));
                messageTorgiDetails.setPriceDecreasingInfo("адресу: Кировская обл., р-н Омутнинский, " +
                        "пгт Песковка, ул. Ленина, д. 73, кадастровый номер 43:22:350319:18," +
                        " площадью 2 854 кв.м, категория земель: земли населенного пункта, разрешенное " +
                        "использование: для эксплуатации зданий; Земельный участок по адресу: Кировская обл.," +
                        " р-н Омутнинский, пгт Песковка, ул. Ленина, д. 73, кадастровый номер 43:22:350319:25, " +
                        "площадью 18 900 кв.м, категория земель: земли населенного пункта, разрешенное использование:" +
                        " для эксплуатации зданий; адресу: Кировская обл., р-н Омутнинский, пгт Песковка, ул. Ленина," +
                        " д. 73, кадастровый номер 43:22:350319:18, площадью 2 854 кв.м, категория земель:" +
                        " земли населенного пункта, разрешенное использование: для эксплуатации зданий; Земельный " +
                        "участок по адресу: Кировская обл., р-н Омутнинский, пгт Песковка, ул. Ленина, д. 73," +
                        " кадастровый номер 43:22:350319:25, площадью 18 900 кв.м, категория земель: земли" +
                        " населенного пункта, разрешенное использование: для эксплуатации зданий; адресу: Кировская обл.," +
                        " р-н Омутнинский, пгт Песковка, ул. Ленина, д. 73, кадастровый номер 43:22:350319:18, площадью" +
                        " 2 854 кв.м, категория земель: земли населенного пункта, разрешенное использование: для эксплуатации" +
                        " зданий; Земельный участок по адресу: Кировская обл., р-н Омутнинский, пгт Песковка, ул." +
                        "Ленина, д. 73, кадастровый номер 43:22:350319:25, площадью 18 900 кв.м, категория земель:" +
                        " земли населенного пункта, разрешенное использование: для эксплуатации зданий; адресу: " +
                        "Кировская обл., р-н Омутнинский, пгт Песковка, ул. Ленина, д. 73, кадастровый номер" +
                        "43:22:350319:18, площадью 2 854 кв.м, категория земель: земли населенного пункта," +
                        " разрешенное использование: для эксплуатации зданий; Земельный участок по адресу: " +
                        "Кировская обл., р-н Омутнинский, пгт Песковка, ул. Ленина, д. 73, кадастровый номер" +
                        " 43:22:350319:25, площадью 18 900 кв.м, категория земель: земли населенного пункта," +
                        " разрешенное использование: для эксплуатации зданий; ");
                messageTorgiDetails.setLotClassification(lotClassifications
                        .get(random.nextInt(lotClassifications.size())));


//                messageTorgi.setMessageTorgiDetails(messageTorgiDetails);
                arbitrManager.addMessageTorgiToArbitrManager(messageTorgi);
                log.info("Added new Message torgi to Arbitr Manager");
            }
            log.info("Saving arbitr manager");
            arbitrManagerRepository.save(arbitrManager);
        }

    }
}
