<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h1>Подробная информация о торгах</h1>
    <hr>
    <p>Дата публикации: <strong>${msgTr.dateOfPublication}</strong></p>
    <p>Наименование должника: <strong>${msgTr.debtorName}</strong></p>
    <p>Адрес должника: <strong>${msgTr.debtorAdres}</strong></p>
    <p>Наименование должника: <strong>${msgTr.debtorName}</strong></p>
    <p>ИНН: <strong>${msgTr.inn}</strong></p>
    <p>Номер дела: <strong>${msgTr.caseNumber}</strong></p>
    <p>СРО: <strong>${msgTr.sro}</strong></p>
    <p>Тип аукциона: <strong>${msgTr.auctionType}</strong></p>
    <p>Дата начала приёма заявок: <strong>${msgTr.auctionStartDate}</strong></p>
    <p>Дата конца приёма заявок: <strong>${msgTr.auctionFinishedDate}</strong></p>
    <p>Условия участия в торгах: <strong>${msgTr.auctionRules}</strong></p>
    <p>Дата и время торгов: <strong>${msgTr.dateTimeAuction}</strong></p>
    <p>Форма предложения о цене: <strong>${msgTr.priceOfferForm}</strong></p>
    <p>Электронная площадка: <strong>${msgTr.marketPlace}</strong></p>
    <p>Текст сообщения: <strong>${msgTr.publicationText}</strong></p>
    <p>Номер лота: <strong>${msgTrDet.lotNumber}</strong></p>
    <p>Описание лота: <strong>${msgTrDet.lotDescription}</strong></p>
    <p>Начальная цена: <strong>${msgTrDet.startPrice}</strong></p>
    <p>Шаг торгов: <strong>${msgTrDet.step}</strong></p>
    <p>Задаток: <strong>${msgTrDet.deposit}</strong></p>
    <p>Информация о снижении цены: <strong>${msgTrDet.priceDecreasingInfo}</strong></p>
    <p>Классификация лота: <strong>${msgTrDet.lotClassification}</strong></p>
    <hr>
    <a href="/arbitr/manager/${mngId}">Информация об арбитражном управляющие</a>



</@c.page>