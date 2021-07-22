<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h2 style="text-align: center">Арбитражные управляющие</h2>
    <hr>
    <div class="form-group mt-3">
        <form method="get" action="/">
            <div class="form-group">
                <label for="sortedType" class="form-label">Выбирите тип сортировки</label>
                <select name="sortedType"
                        id="sortedType" class="form-select form-select-sm" style="width: 100%">
                    <option <#if sortedType == "msg-standart">selected</#if>
                            value="msg-standart">
                        Классический
                    </option>
                    <option <#if sortedType == "msg-high-to-low">selected</#if>
                            value="msg-high-to-low">
                        По количеству сообщений (от большего к меньшему)
                    </option>
                    <option <#if sortedType == "msg-low-to-high">selected</#if>
                            value="msg-low-to-high">
                        По количеству сообщений (от меньшего к большему)
                    </option>
                </select>
            </div>
            <div class="form-group">
                <input class="form-control form-control-sm"
                       type="text" value="${searchWord}" placeholder="Поиск по ФИО" name="searchWord">
            </div>
            <div class="form-group">
                <button type="submit" style="width: 100%" class="btn btn-outline-dark btn-sm">
                    Поиск</button>
            </div>

        </form>
    </div>
    <hr>
    <table class="table table-secondary table-striped" border="1">
        <tr>
            <th>ФИО</th>
            <th>Регистрационный номер</th>
            <th>СРО</th>
            <th>Количество сообщений</th>
            <th>Подробная информация</th>
        </tr>
        <#list arbitrManagers as mgr>
            <tr>
                <td>${mgr.fullName}</td>
                <td>${mgr.regNumber}</td>
                <td>${mgr.sro}</td>
                <td>${mgr.messagesQuantity}</td>
                <td>
                    <a href="/arbitr/manager/${mgr.id}">
                        Посмотреть подробную информацию
                    </a>
                </td>
            </tr>
        <#else>
            <h3>Даннык не найдены</h3>
        </#list>
    </table>
</@c.page>