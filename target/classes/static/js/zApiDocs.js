var apiList;
var apis = {};
var apiMethods = {};
var apiEntities = {};

/**
 * 初始化API文档
 */
function initApiDocs() {
    var controllersHtml = '';
    $(apiList).each(function (i, e) {
        var id = uuid();
        e.id = id;

        controllersHtml +=
            '<li>' +
            '   <div class="collapsible-header" style="text-align: left">' +
            '   <a href="javascript:;">' + e.name + '&nbsp;&nbsp;<span style="font-size: 0.8rem;">version ' + e.version + '</span></a>' +
            '   </div>' +
            '<div class="collapsible-body">' +
            '<ul class="collection">';

        $(e.apiMethods).each(function (j, m) {
            var methodId = uuid();
            m.id = methodId;
            apiMethods[methodId] = m;
            controllersHtml +=
                '<li class="collection-item">' +
                '<div style="text-align: center">' +
                '<a href="javascript:openMethod(\'' + id + '\',\'' + methodId + '\')">' + m.name + '&nbsp;&nbsp;' +
                '</a></div>' +
                '</li>';
        });
        controllersHtml +=
            '   </ul></div>' +
            '</li>';
        apis[id] = e;
    });
    $('#controllers').html(controllersHtml);
    $('#controllers').collapsible();

    var temp = apiEntities;
    apiEntities = {};
    $(temp).each(function (i, e) {
        apiEntities[e.name] = e;
    });
}


function openMethod(apiId, methodId) {
    var api = apis[apiId];
    var apiMethod = apiMethods[methodId];
    var apiMethodName = api.name + ' > ' + apiMethod.name;
    $('#apiMethodName').html(apiMethodName);
    var apiMethodMethods = '';
    $(apiMethod.method).each(function (i, e) {
        apiMethodMethods += '<p>' + e + '</p>';
    });
    $('#apiMethodMethods').html(apiMethodMethods);
    var apiMethodUrls = '';
    $(apiMethod.url).each(function (i, e) {
        apiMethodUrls += '<p>' + e + '</p>';
    });
    $('#apiMethodUrls').html(apiMethodUrls);
    $('#apiMethodDescription').html(apiMethod.description);
    var apiParams = apiMethod.apiParams;
    var apiParamsHtml = '';
    $(apiParams).each(function (i, e) {
        apiParamsHtml +=
            '<tr>' +
            '    <td>' + e.name + '</td>';
        var dataType = JSON.parse(e.dataType);
        if (!dataType.defined) {
            //不是自定义类
            apiParamsHtml +=
                '    <td>' + dataType.type + '</td>';
        } else {
            apiParamsHtml +=
                '    <td><a href="javascript:openClass(\'' + dataType.type + '\');">' + dataType.type + '</a></td>';
        }
        apiParamsHtml +=
            '    <td>' + e.required + '</td>' +
            '    <td>' + e.paramType + '</td>' +
            '    <td>' + e.description + '</td>' +
            '</tr>';
    });
    $('#apiMethodParams').html(apiParamsHtml);
    var apiResponses = apiMethod.apiResponses;

    if (typeof apiResponses.apiResponseHeaders === 'undefined') {
        $('#apiMethodResponseHeaders').html('<tr><td colspan="3">无</td></tr>');
    } else {
        if (apiResponses.apiResponseHeaders.length === 0) {
            $('#apiMethodResponseHeaders').html('<tr><td colspan="3">无</td></tr>');
        } else {
            var apiResponseHeadersHtml = '';
            $(apiResponses.apiResponseHeaders).each(function (i, e) {
                apiResponseHeadersHtml +=
                    '<tr>' +
                    '    <td>' + e.name + '</td>' +
                    '    <td>' + e.value + '</td>' +
                    '    <td>' + e.description + '</td>' +
                    '</tr>';
            });
            $('#apiMethodResponseHeaders').html(apiResponseHeadersHtml);
        }
    }

    if (typeof apiResponses.apiResponses === 'undefined') {
        $('#apiMethodResponseBodies').html('<tr><td colspan="3">无</td></tr>');
    } else {
        if (apiResponses.apiResponses.length === 0) {
            $('#apiMethodResponseBodies').html('<tr><td colspan="3">无</td></tr>');
        } else {
            var apiResponsesHtml = '';
            $(apiResponses.apiResponses).each(function (i, e) {
                apiResponsesHtml +=
                    '<tr>' +
                    '    <td>' + e.code + '</td>' +
                    '    <td>' + e.message + '</td>' +
                    '    <td><a href="javascript:openClass(\'' + e.responseBody + '\');">' + e.responseBody + '</a></td>' +
                    '</tr>';
            });
            $('#apiMethodResponseBodies').html(apiResponsesHtml);
        }
    }
}

function openClass(className) {
    if (typeof apiEntities[className] !== 'undefined') {
        var apiEntity = apiEntities[className];
        console.log(apiEntity);
        $('#classInformationClassName').html(className);
        $('#classInformationName').html(apiEntity.name);
        $('#classInformationDescription').html(apiEntity.description);
        $('#classInformationAuthor').html(apiEntity.author);
        $('#classInformationVersion').html(apiEntity.version);

        var apiEntityFields = apiEntity.apiEntityFields;
        var fieldsHtml = '';
        $(apiEntityFields).each(function (i, e) {
            fieldsHtml +=
                '<tr>' +
                '   <td>' + e.name + '</td>';
            fieldsHtml += '<td>';
            if (e.defined) {
                fieldsHtml += '<a href="javascript:openClass(\'' + e.fieldType + '\')">';
                if (e.collection) {
                    fieldsHtml += e.collectionType + '&lt; ' + e.fieldType + ' &gt;';
                } else {
                    fieldsHtml += e.fieldType;
                }
                fieldsHtml +=
                    '</a>';
            } else {
                fieldsHtml += e.fieldType;
            }
            fieldsHtml += '</td>';
            fieldsHtml += '   <td>' + e.description + '</td>' +
                '</tr>';
        });
        $('#classInformationClassFields').html(fieldsHtml);
        $('#classInformation').modal('open');
    } else {
        console.log('当前类不存在');
        $('#noClassName').html(className);
        $('#noClass').modal('open');
    }
}

function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
    return s.join("");
}