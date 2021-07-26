<#import "bloks/common.ftlh" as c>
<@c.page true>
    <div class="container mt-5">
        <h2 style="text-align: center">Сообщения</h2>
        <hr>
        <div class="form-group mt-3">
            <form method="get" action="/messages">
                <div class="form-group">
                    <label for="region" class="form-label">Сортировка по региону</label>
                    <select name="region"
                            id="region" class="form-select form-select-sm" style="width: 100%">
                        <option value="">Регион не выбран</option>
                        <#list regions as r>
                            <option <#if region == r>selected</#if> value="${r}">${r}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <label for="town" class="form-label">Сортировка по городу</label>
                    <select name="town"
                            id="town" class="form-select form-select-sm" style="width: 100%">
                        <option value="">Город не выбран</option>
                        <#list towns as t>
                            <option <#if town == t>selected</#if> value="${t}">${t}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <input class="form-control form-control-sm"
                           type="text" value="${searchByLotDesWord?if_exists}" placeholder="Поиск по описанию лота"
                           name="searchByLotDesWord">
                </div>
                <div class="form-group">
                    <button type="submit" style="width: 100%" class="btn btn-outline-dark btn-sm">
                        Поиск
                    </button>
                </div>
            </form>
        </div>
        <hr>
    </div>
    <table class="table table-secondary table-striped" border="1">
        <tr>
            <th>Описание лота</th>
            <th>Классификация лота</th>
            <th>Тип аукциона</th>
            <th>Дата начала приёма заявок</th>
            <th>Дата конца приёма заявок</th>
            <th>Начальная цена</th>
            <th>Шаг аукциона</th>
            <th>Задаток</th>
            <th>Условия снижения цены</th>
            <th>Площадка</th>
            <th>Локация</th>
            <th>Регион</th>
            <th>Город</th>
            <th>Площадь</th>
            <th>Подробная информация</th>
        </tr>
        <#list messages as msg>
            <tr>
                <td>${msg.messageTorgiDetails.lotDescription}</td>
                <td>${msg.messageTorgiDetails.lotClassification}</td>
                <td>${msg.auctionType}</td>
                <td>${msg.auctionStartDate}</td>
                <td>${msg.auctionFinishedDate}</td>
                <td>${msg.messageTorgiDetails.startPrice}</td>
                <td>${msg.messageTorgiDetails.step}</td>
                <td>${msg.messageTorgiDetails.deposit}</td>
                <td>${msg.messageTorgiDetails.priceDecreasingInfo}</td>
                <td>${msg.marketPlace}</td>
                <td>${msg.location?if_exists}</td>
                <td>${msg.region}</td>
                <td>${msg.town}</td>
                <td>${msg.area}</td>
                <td>
                    <a href="/messages/${msg.id}">
                        Подробная информация
                    </a>
                </td>
            </tr>
        <#else>
            <h3>Данные не найдены</h3>
        </#list>
    </table>

    <#include "bloks/footer.ftlh">
</@c.page>


