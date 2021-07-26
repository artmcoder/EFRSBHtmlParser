<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h1>Подробная информация о торгах</h1>
    <hr>
    <p>Дата публикации: <strong>${msgTr.dateOfPublication?if_exists}</strong></p>
    <p>Наименование должника: <strong>${msgTr.debtorName?if_exists}</strong></p>
    <p>Адрес должника: <strong>${msgTr.debtorAdres?if_exists}</strong></p>
    <p>Наименование должника: <strong>${msgTr.debtorName?if_exists}</strong></p>
    <p>ИНН: <strong>${msgTr.inn?if_exists}</strong></p>
    <p>Номер дела: <strong>${msgTr.caseNumber?if_exists}</strong></p>
    <p>СРО: <strong>${msgTr.sro?if_exists}</strong></p>
    <p>Тип аукциона: <strong>${msgTr.auctionType?if_exists}</strong></p>
    <p>Дата начала приёма заявок: <strong>${msgTr.auctionStartDate?if_exists}</strong></p>
    <p>Дата конца приёма заявок: <strong>${msgTr.auctionFinishedDate?if_exists}</strong></p>
    <p>Условия участия в торгах: <strong>${msgTr.auctionRules?if_exists}</strong></p>
    <p>Дата и время торгов: <strong>${msgTr.dateTimeAuction?if_exists}</strong></p>
    <p>Форма предложения о цене: <strong>${msgTr.priceOfferForm?if_exists}</strong></p>
    <p>Электронная площадка: <strong>${msgTr.marketPlace?if_exists}</strong></p>
    <p>Текст сообщения: <strong>${msgTr.publicationText?if_exists}</strong></p>
    <hr>
    <h2 style="text-align: center;">Лоты сообщения</h2><br>
    <table class="table table-secondary table-striped" border="1">
        <tr>
            <th>Номер лота</th>
            <th>Описание лота</th>
            <th>Начальная цена</th>
            <th>Шаг торгов</th>
            <th>Задаток</th>
            <th>Начальная цена</th>
            <th>Шаг аукциона</th>
            <th>Задаток</th>
            <th>Информация о снижении цены</th>
            <th>Классификация лота</th>
        </tr>
        <#list msgTrDets as msgTrDet>
            <tr>
                <td>${msgTrDet.lotNumber?if_exists}</td>
                <td>${msgTrDet.lotDescription?if_exists}</td>
                <td>${msgTrDet.startPrice?if_exists}</td>
                <td>${msgTrDet.step?if_exists}</td>
                <td>${msgTrDet.deposit?if_exists}</td>
                <td>${msgTrDet.priceDecreasingInfo?if_exists}</td>
                <td>${msgTrDet.lotClassification?if_exists}</td>
            </tr>
        <#else>
            <h3>Лотов нет</h3>
        </#list>
    </table>
    <hr>
    <a href="/arbitr/manager/${mngId?if_exists}">Информация об арбитражном управляющие</a>



</@c.page>