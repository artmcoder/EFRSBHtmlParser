<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h2>Подробная информация об арбитражном управляющие</h2>
    <hr>
    <p>ФИО: <strong>${mng.fullName}</strong></p>
    <p>Номер регистрации: <strong>${mng.regNumber}</strong></p>
    <p>Дата регистрации: <strong>${mng.regDate}</strong></p>
    <p>СРО: <strong>${mng.sro}</strong></p>
    <p>Количество сообщений: <strong>${mng.messagesQuantity}</strong></p>
    <hr>
    <a href="/messages/arbitr/manager/${mng.id}">
        Просмотр сообщений арбитражного управляющего
    </a>
</@c.page>