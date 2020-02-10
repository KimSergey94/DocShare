var tmp_files = [];
function isHidden(el) {
    var width = el.offsetWidth, height = el.offsetHeight,
        tr = el.nodeName.toLowerCase() === "tr";
    return width === 0 && height === 0 && !tr ?
        true : width > 0 && height > 0 && !tr ? false :	getRealDisplay(el)
}
function hide(el) {
    if (!el.getAttribute('displayOld')) {
        el.setAttribute("displayOld", el.style.display)
    }
    el.style.display = "none"
}
$(document).on('click', '.download-attachment', function(e){
    var attachment_id = this.value;
    window.location = 'download?id=' + attachment_id;
    e.stopPropagation();
});
function dropdownSelectBox(element) {
    removeAllZ3();
    $(element).parent().toggleClass('z-index3');
    isHidden(element.children[2]) ? show(element.children[2]) : hide(element.children[2]);
    $("body").click(function() {
        element.parentNode.classList.remove('z-index3');
        hide(element.children[2]);
    });
    $(".z-index3").click(function(e) {
        e.stopPropagation();
    });
}
function removeAllZ3(){
    var x = document.getElementsByClassName("z-index3");
    var i;
    for (i = 0; i < x.length; i++) {
        hide(x[i].children[2].children[2]);
        x[i].classList.remove("z-index3");
    }
}
function removeAllZ4(){
    var x = document.getElementsByClassName("z-index4");
    var i;
    for (i = 0; i < x.length; i++) {
        x[i].classList.remove("z-index3");
    }
}
displayCache = {};
function show(el) {
    if (getRealDisplay(el) !== 'none') return;
    var old = el.getAttribute("displayOld");
    el.style.display = old || "";
    if ( getRealDisplay(el) === "none" ) {
        var nodeName = el.nodeName, body = document.body, display;
        if ( displayCache[nodeName] ) {
            display = displayCache[nodeName]
        } else {
            var testElem = document.createElement(nodeName);
            body.appendChild(testElem);
            display = getRealDisplay(testElem);
            if (display === "none" ) {
                display = "block"
            }
            body.removeChild(testElem);
            displayCache[nodeName] = display
        }
        el.setAttribute('displayOld', display);
        el.style.display = display
    }
}
function getRealDisplay(elem) {
    if (elem.currentStyle) {
        return elem.currentStyle.display
    } else if (window.getComputedStyle) {
        var computedStyle = window.getComputedStyle(elem, null )
        return computedStyle.getPropertyValue('display')
    }
}
function select(element){
    element.parentNode.parentNode.parentNode.classList.remove("active");
    element.parentNode.parentNode.parentNode.parentNode.classList.remove("z-index3");
    element.parentNode.parentNode.parentNode.classList.remove("z-index3");
    element.parentNode.parentNode.parentNode.children[0].innerHTML = element.textContent;
    element.parentNode.parentNode.parentNode.parentNode.children[1].value = element.value;
}
$(document).ready(function(){
        $("#login-form").on("submit", function () {
            var password = $("#password", $("#login-form")).val();
            var login = $("#login", $("#login-form")).val();
            var loginhash = CryptoJS.MD5(login);
            var passwordhash = CryptoJS.MD5(password);
            var hashedlogpass = passwordhash + loginhash;
            var logpasshash = CryptoJS.MD5(hashedlogpass);
            $("#password", $("#login-form")).val(logpasshash);
        });
});

function newXMLHttpRequest() {
    var xmlreq = false;
    if (window.XMLHttpRequest) {
        xmlreq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {
            try {
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
            }
        }
    }
    return xmlreq;
}
function loadForm(documentTypeID) {
    var req = newXMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState===4 && req.status===200) {
            document.getElementsByClassName("document-creation")[0].innerHTML = req.response;
        }
    };
    req.open("POST", getContextPath() + "/getform", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    req.send("documentTypeID=" + documentTypeID);
}
function validate() {
    return confirm("Вы действительно хотите создать документ ?");
}
function validateDraft() {
    return confirm("Вы действительно хотите сохранить документ ?");
}
function validateEdit() {
    return confirm("Вы действительно хотите повторно отправить документ ?");
}
function validateRegistration() {
    return confirm("Вы действительно хотите добавить нового пользователя ?");
}
function submitform() {
    var form = document.getElementById("new-doc-form");
    if(validate() === true){
        form.submit();
    }
}
function submitEditForm() {
    var form = document.getElementById("req-doc-form");
    form.submit();
}
function submitRegForm() {
    var form = document.getElementById("registration-form");
    var login = $(document.getElementById("reg-user-login")).val();
    var password1 = $(document.getElementById("reg-user-password1")).val();
    var password2 = $(document.getElementById("reg-user-password2")).val();
    if(validateRegistration() === true){
        var loginhash = CryptoJS.MD5(login);
        var password1hash = CryptoJS.MD5(password1);
        var password2hash = CryptoJS.MD5(password2);
        var hashedlogpass1 = password1hash + loginhash;
        var hashedlogpass2 = password2hash + loginhash;
        var logpass1hash = CryptoJS.MD5(hashedlogpass1);
        var logpass2hash = CryptoJS.MD5(hashedlogpass2);
        $(document.getElementById("reg-user-password1")).val(logpass1hash);
        $(document.getElementById("reg-user-password2")).val(logpass2hash);
        form.submit();
    }
}
function createDraft() {
    var form = document.getElementById("new-doc-form");
    var action = './newdraft';
    $(form).attr('action', action);
    if(validateDraft() === true){
        form.submit();
    }
}
function createReport() {
    var form = document.getElementById("create-report-form");
    form.submit();
}
function editDocument() {
    var form = document.getElementById("new-doc-form");
    var report_type_id = document.getElementById("report-type-id").value;
    var action = './edit';
    $(form).attr('action', action);
    if(validateEdit() === true){
        form.submit();
    }
}
function searchSelect(element) {
    var valueEl = element.parentNode.parentNode.parentNode.parentNode.children[1];
    var textEl = element.parentNode.parentNode.parentNode.children[0];
    var suggestion = element.parentNode.parentNode;
    valueEl.value = element.value;
    textEl.value = element.textContent;
    element.parentNode.parentNode.parentNode.parentNode.classList.remove("z-index4");
    $(suggestion).hide();
}
function selectLegalEntity(element) {
    var valueEl = element.parentNode.parentNode.parentNode.parentNode.children[1];
    var textEl = element.parentNode.parentNode.parentNode.children[0];
    var suggestion = element.parentNode.parentNode;
    valueEl.value = element.value;
    textEl.value = element.textContent;
    element.parentNode.parentNode.parentNode.parentNode.classList.remove("z-index4");
    $(suggestion).hide();
    fillLegalEntityForm(element.value);
}
function addFile(sel){
    var element = document.getElementById("attached-files");
    var input1 = document.getElementById("attachments-input");
    for (var i = 0; i < sel.files.length; i++) {
        var input = document.createElement("input");
        var deleteButton = document.createElement("div");
        var div = document.createElement("div");
        var radio = document.createElement("input");
        radio.type = 'radio';
        radio.name = 'main_attachment';
        radio.title = 'Основной документ';
        radio.className = "main-attachment-radio";
        radio.value = sel.files[i].name;
        deleteButton.id = "delete-attachment";
        input.className = "attachment";
        input.type = 'text';
        input.id = 'attachment'+ i;
        input.name = 'attachment'+ i;
        input.readOnly = true;
        input.value = sel.files[i].name;
        div.appendChild(radio);
        div.appendChild(input);
        div.appendChild(deleteButton);
        element.appendChild(div);
        tmp_files.push(sel.files[i]);
    }
    input1.files = new FileListItem(tmp_files);
}
function FileListItem(a) {
    a = [].slice.call(Array.isArray(a) ? a : arguments);
    for (var c, b = c = a.length, d = !0; b-- && d;) d = a[b] instanceof File;
    if (!d) throw new TypeError("expected argument to FileList is File or array of File objects");
    for (b = (new ClipboardEvent("")).clipboardData || new DataTransfer; c--;) b.items.add(a[c]);
    return b.files
}
function selectLinkedDocument(sel){
    var element = document.getElementById("linked-docs");
    var inputsCount = document.getElementById("linked-documents-count");
    var input = document.createElement("input");
    var hiddenInput = document.createElement("input");
    var suggestion = sel.parentNode.parentNode;
    var div = document.createElement("div");
    var deleteButton = document.createElement("div");
    for (var i =0; i < inputsCount.value; i++){
        if(document.getElementById('linked-document' + i).value === sel.textContent){
            $(suggestion).hide();
            sel.parentNode.parentNode.parentNode.children[0].value = '';
            return;
        }
    }
    deleteButton.id = "delete-linked-document";
    hiddenInput.type = 'text';
    hiddenInput.name = 'linked-document' + inputsCount.value;
    hiddenInput.hidden = true;
    hiddenInput.value = sel.value;
    input.type = 'text';
    input.id = 'linked-document'+ inputsCount.value;
    input.readOnly = true;
    input.value = sel.textContent;
    div.appendChild(hiddenInput);
    div.appendChild(input);
    div.appendChild(deleteButton);
    element.appendChild(div);
    inputsCount.value++;
    $(suggestion).hide();
    sel.parentNode.parentNode.parentNode.children[0].value = '';
}
$(document).on('click', '#delete-linked-document', function(){
    this.parentNode.parentNode.removeChild(this.parentNode);
    var inputsCount = document.getElementById("linked-documents-count");
    inputsCount.value--;
});
$(document).on('click', '#delete-matcher', function(){
    this.parentNode.parentNode.removeChild(this.parentNode);
    var inputsCount = document.getElementById("selected-matchers-count");
    inputsCount.value--;
});
$(document).on('click', '#delete-attachment', function(){
    this.parentNode.parentNode.removeChild(this.parentNode);
    var value = this.parentNode.children[0].value.trim();
    var input = document.getElementById("attachments-input");
    var new_tmp_files = [];
    for (var i = 0; i < tmp_files.length; i++) {
        if(tmp_files[i].name.trim() !== value){
            new_tmp_files.push(tmp_files[i]);
        }
    }
    tmp_files = new_tmp_files;
    input.files = new FileListItem(tmp_files);
});
$(document).on('click', '#delete-executer', function(){
    this.parentNode.parentNode.removeChild(this.parentNode);
    var inputsCount = document.getElementById("selected-executers-count");
    inputsCount.value--;
});
$(document).on('keyup', '#entity-street-search', function(){
    this.parentNode.parentNode.children[1].value = "0";
    removeAllZ4();
    var element = this;
    var suggestion = this.parentElement.children[1];
    var cityID = document.getElementById("entity-city-value").value;
    $.ajax({
        type: "POST",
        url: "getstreets",
        data:'keyword=' + $(this).val() + '&cityID=' + cityID,
        success: function(data){
            $(suggestion).show();
            element.parentNode.parentNode.classList.add("z-index4");
            suggestion.innerHTML = data;
        }
    });
    $("body").click(function() {
        element.parentNode.parentNode.classList.remove('z-index4');
        $(suggestion).hide();
    });
    $(".z-index4").click(function(e) {
        e.stopPropagation();
    });
});

function selectMatcher(sel){
    var element = document.getElementById("selected-matchers");
    var inputsCount = document.getElementById("selected-matchers-count");
    var input = document.createElement("input");
    var hiddenInput = document.createElement("input");
    var suggestion = sel.parentNode.parentNode;
    var div = document.createElement("div");
    var deleteButton = document.createElement("div");
    if(inputsCount.value > 0 ){
        for (var i = 0; i < document.getElementsByClassName('matcher').length; i++) {
            if(parseInt(document.getElementsByClassName('matcher')[i].value) === parseInt(sel.value)){
                $(suggestion).hide();
                sel.parentNode.parentNode.parentNode.children[0].value = '';
                return;
            }
        }
    }
    deleteButton.id = "delete-matcher";
    hiddenInput.type = 'text';
    hiddenInput.name = 'matcher' + inputsCount.value;
    hiddenInput.className = 'matcher';
    hiddenInput.hidden = true;
    hiddenInput.value = sel.value;
    input.type = 'text';
    input.id = 'matcher'+ inputsCount.value;
    input.readOnly = true;
    input.value = sel.textContent.trim();
    div.appendChild(hiddenInput);
    div.appendChild(input);
    div.appendChild(deleteButton);
    element.appendChild(div);
    inputsCount.value++;
    $(suggestion).hide();
    sel.parentNode.parentNode.parentNode.children[0].value = '';
}
$(document).on('click', '.preloaded-document', function(){
    $(".preloaded-document").each(function () {
        $(this).removeClass("document-opened");
    });
    $(this).addClass("document-opened");
    var documentId = this.children[0].children[0].value;
    var documentBox = document.getElementsByClassName("content-box")[0];
    $.ajax({
        type: "POST",
        url: "document",
        data:'id=' + documentId,
        success: function(data){
            documentBox.innerHTML = data;
        }
    });
});
$(document).on('click', '.attachment-item', function(){
    $(".attachment-item").each(function () {
        $(this).removeClass("attachment-opened");
    });
    $(this).addClass("attachment-opened");
    var attachmentId = this.children[0].value;
    var attachmentBox = this.parentNode.parentNode.children[2];
    attachmentBox.innerHTML = "<iframe src=\"./media/attachment?id=" + attachmentId + "#scrollbar=0&toolbar=0&navpanes=0\" width=\"100%\" height=\"750px\" >\n" +
                "    <p class=\"file-preview-error\">Ошибка</p>\n" +
                "    <p class=\"file-preview-error\">Не удалось загрузить файл для предпросмотра.</p>\n" +
                "</iframe>";
});
function redirectToInWork() {
    window.location.replace("./inwork");
}
$(document).on('click', '#content-button-registration', function(){
    $('#rn-overlay').modal('show');
    document.getElementById("reg-number-overlay").value = document.getElementById("reg-number").innerText;
});
$(document).on('click', '#content-button-reject', function(){
    $('#reject-overlay').modal('show');
});

$(document).on('click', '#match-document', function(){
    $('#subscribe-overlay').modal('show');
});



$(document).on('click', '#execution-overlay-submit', function(){
    var form = $(document.getElementById("execution-box-form"));
    var url = form.attr('action');
    $.ajax({
        type: "POST",
        url: url,
        data: form.serialize(),
        success: function(data) {
            if(data.error != null){
                document.getElementById("execution-error-msg").innerText = data.error;
            } else if(data.message != null){
                $('#execution-overlay').modal('hide');
                document.getElementById("popUp-info").innerText = data.message;
                $('#popUp-overlay').modal('show');
                setTimeout(closePopUp, 3 * 1000);
                setTimeout(redirectToInWork, 3 * 1000);
            }
        }
    });
});
$(document).ready(function(){
    if(window.location.pathname.substring(0, window.location.pathname.length - 1) !== getContextPath() && window.location.pathname.indexOf("/login") === -1 ){
        setInterval(checkIfSessionExists, 10 * 1000);
    }
});
$(document).ready(function(){
    getUnreadDocumentsCount();
    setInterval(getUnreadDocumentsCount, 5 * 1000);
});
function checkIfSessionExists() {
    $.ajax({
        type: "POST",
        url: "session-check",
        success: function (data) {
            if(data.error != null){
                alert(data.error);
                window.location.href = getContextPath() + "/inwork";
            } else if (data.message == null && data.error == null){
                window.location.href = getContextPath() + "/inwork";
            }
        },
        error: function () {
            window.location.href = getContextPath() + "/inwork";
        }
    });
}
function getUnreadDocumentsCount() {
    $.ajax({
        type: "POST",
        url: "unread-documents",
        success: function (data) {
            if (data.message != null) {
                if(data.message !== '0'){
                    var inwork = document.getElementById("unread-counter-inwork");
                    var mydocs = document.getElementById("unread-counter-mydocs");
                    inwork.innerText = data.message;
                    mydocs.innerText = data.message;
                    $(inwork).show();
                    $(mydocs).show();
                } else {
                    $(document.getElementById("unread-counter-mydocs")).hide();
                    $(document.getElementById("unread-counter-inwork")).hide();
                }
            }
        }
    });
}
$(document).on('click', '.close-overlay', function(){
    var overlay = this.parentNode.parentNode;
    $(overlay).modal('hide');
});
$(document).on('click', '.navigation > div', function(){
    var opened_menu_id = this.children[0].value;
    var action = "";
    if(this.classList.contains("opened")){
        action = "add";
    } else {
        action = "delete";
    }
    $.ajax({
        type: "POST",
        url: "left-menu",
        data: "id=" + opened_menu_id +"&action=" + action,
        success: function(data) {}
    });
});
$(document).on('mouseover', '#dragndrop', function(){
    var holder = document.getElementById('dragndrop');
    holder.ondragenter = function (e) { e.preventDefault(); return false; };
    holder.ondragleave = function (e) { e.preventDefault(); this.className = 'newdoc-attachments'; return false; };
    holder.ondragover = function (e) { e.preventDefault(); this.className = 'newdoc-attachments drag-n-drop'; return false; };
    holder.ondrop = function (e) {
        e.preventDefault();
        this.className = 'newdoc-attachments';
        addFile(e.dataTransfer);
        return false;
    };
});


