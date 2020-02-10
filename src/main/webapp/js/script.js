function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
$(document).on('click', '.navigation > div > div', function(e){
    $(this).parent().toggleClass('opened');
});
$(document).ready(function(){
    $(".user-info").click(function(e) {
        $(".user-menu").toggleClass("user-menu-opened");
        e.stopPropagation();
    });
});
$(document).on('click', '.documents-sort', function(e) {
    e.stopPropagation();
    e.preventDefault();
    if ($(this).hasClass("active")) {
        $(this).removeClass('active');
    } else {
        $(".active").each(function () {
            $(this).removeClass("active");
        });
        $(this).toggleClass('active');
    }
});
$(document).on('click', '.files-sort', function(e) {
    e.stopPropagation();
    e.preventDefault();
    if ($(this).hasClass("active")) {
        $(this).removeClass('active');
    } else {
        $(".active").each(function () {
            $(this).removeClass("active");
        });
        $(this).toggleClass('active');
    }
});

$(document).on('click', '.files-view-btn', function(e) {
    e.stopPropagation();
    e.preventDefault();
    if ($(this).hasClass("active")) {
        $(this).removeClass('active');
    } else {
        $(".active").each(function () {
            $(this).removeClass("active");
        });
        $(this).toggleClass('active');
    }
});

$(document).on('click', '.preloaded-document', function(e){
    var folderId = this.children[0].value;
    var documentBox = document.getElementById("content-box");
    var filesBoxHeader = document.getElementById("files-box-header");
    var isTrash = window.location.pathname.replace(getContextPath(), "").replace("/","");
    e.preventDefault();

    $(".preloaded-document").each(function () {
        $(this).removeClass("document-opened");
    });
    $(this).addClass("document-opened");
    $.ajax({
        type: "POST",
        url: "openfolder",
        data: "isTrash="+isTrash+"&id=" + folderId,
        success: function (data) {
            documentBox.innerHTML = data;
        }
    });

    e.stopPropagation();

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
$(document).on('click', '#content-button-download-file', function(e){
    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("download-file-id").value;
    window.location = "download-file?id="+file_id;
});
/*
$(document).on('click', '#content-button-download-files', function(e){
    e.stopPropagation();
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "download-files",
        data: "opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {

        }
    });
});
*/


$(document).on('click', '#content-button-delete-file', function(e){
    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("delete-file-id").value;
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "delete-file",
        data: "id=" + file_id,
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: "id=" + folderId,
                    success: function(data){
                        documentBox.innerHTML = data;
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-delete-files', function(e){
    e.stopPropagation();
    e.preventDefault();

    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "delete-files",
        data: "opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: "id=" + folderId,
                    success: function(data){
                        documentBox.innerHTML = data;
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-erase-file', function(e){
    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("erase-file-id").value;
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var files = document.getElementsByClassName("file");
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "erase-file",
        data: "id=" + file_id,
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                if(files.length > 1){
                    $.ajax({
                        type: "POST",
                        url: "openfolder",
                        data: "id=" + folderId,
                        success: function(data){
                            documentBox.innerHTML = data;
                        }
                    });
                } else{
                    $.ajax({
                        type: "POST",
                        url: window.location,
                        success: function(data){
                            document.open();
                            document.write(data);
                            document.close();
                        }
                    });
                }
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-erase-files', function(e){
    e.stopPropagation();
    e.preventDefault();
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    var selected_files = document.getElementsByClassName("file-opened");
    var files = document.getElementsByClassName("file");

    $.ajax({
        type: "POST",
        url: "erase-files",
        data: "opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                if(files.length !== selected_files.length){
                    $.ajax({
                        type: "POST",
                        url: "openfolder",
                        data: "id=" + folderId,
                        success: function(data){
                            documentBox.innerHTML = data;
                        }
                    });
                } else{
                    $.ajax({
                        type: "POST",
                        url: window.location,
                        success: function(data){
                            document.open();
                            document.write(data);
                            document.close();
                        }
                    });
                }
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-restore-file', function(e){
    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("restore-file-id").value;
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "restore-file",
        data: "id=" + file_id,
        success: function(data) {
            var filesDivLength = document.getElementsByClassName("file").length;
            if (filesDivLength === 1 && document.getElementsByClassName("document-opened")[0] != null) {
                $.ajax({
                    type: "POST",
                    url: "main",
                    success: function (data) {
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else {
                if (document.getElementsByClassName("document-opened")[0] != null) {
                    $.ajax({
                        type: "POST",
                        url: "openfolder",
                        data: "id=" + folderId,
                        success: function (data) {
                            documentBox.innerHTML = data;
                        }
                    });
                } else{
                    instantsearch(search_box.value);
                    content_buttons.innerHTML = null;
                }
            }
        }
    });
});

$(document).on('click', '#content-button-restore-files', function(e){
    e.stopPropagation();
    e.preventDefault();
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "restore-files",
        data: "opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {
            var filesDivLength = document.getElementsByClassName("file").length;
            if (filesDivLength === 1 && document.getElementsByClassName("document-opened")[0] != null) {
                $.ajax({
                    type: "POST",
                    url: "main",
                    success: function (data) {
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else {
                if (document.getElementsByClassName("document-opened")[0] != null) {
                    $.ajax({
                        type: "POST",
                        url: "openfolder",
                        data: "id=" + folderId,
                        success: function (data) {
                            documentBox.innerHTML = data;
                        }
                    });
                } else{
                    instantsearch(search_box.value);
                    content_buttons.innerHTML = null;
                }
            }
        }
    });
});
$(document).on('click', '#content-button-hide-files', function(e){
    e.stopPropagation();
    e.preventDefault();
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "hide-files",
        data: "toHide="+true+"&opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: "id=" + folderId,
                    success: function(data){
                        documentBox.innerHTML = data;
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-hide-file', function(e){
    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("hide-file-id").value;
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "hide-file",
        data: "id=" + file_id,
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: "id=" + folderId,
                    success: function(data){
                        documentBox.innerHTML = data;
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-unhide-file', function(e){

    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("unhide-file-id").value;
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "unhide-file",
        data: "id=" + file_id,
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: "id=" + folderId,
                    success: function(data){
                        documentBox.innerHTML = data;
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-unhide-files', function(e){
    e.stopPropagation();
    e.preventDefault();
    if(document.getElementsByClassName("document-opened")[0] != null){
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    }
    var documentBox = document.getElementById("content-box");
    var search_box = document.getElementById("search-box");
    var content_buttons = document.getElementById("content-buttons");
    $.ajax({
        type: "POST",
        url: "unhide-files",
        data: "toHide="+false+"&opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {
            if(document.getElementsByClassName("document-opened")[0] != null){
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: "id=" + folderId,
                    success: function(data){
                        documentBox.innerHTML = data;
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-delete-folder', function(e){
    e.stopPropagation();
    e.preventDefault();
    var content_buttons = document.getElementById("content-buttons");
    var search_box = document.getElementById("search-box");
    var folder_id =  document.getElementById("delete-folder-id").value;
    $.ajax({
        type: "POST",
        url: "delete-folder",
        data: "id=" + folder_id,
        success: function(data) {
            if(search_box.value === ""){
                $.ajax({
                    type: "POST",
                    url: window.location,
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else{
                $.ajax({
                    type: "POST",
                    url: "delete-folder",
                    data: "id=" + folder_id,
                    success: function(data) {
                        instantsearch(search_box.value);
                        content_buttons.innerHTML = null;
                    }
                });
            }
        }
    });
});
$(document).on('click', '#content-button-erase-folder', function(e){
    e.stopPropagation();
    e.preventDefault();
    var content_buttons = document.getElementById("content-buttons");
    var search_box = document.getElementById("search-box");
    var folder_id =  document.getElementById("erase-folder-id").value;
    $.ajax({
        type: "POST",
        url: "erase-folder",
        data: "id=" + folder_id,
        success: function(data) {
            if(search_box.value === ""){
                $.ajax({
                    type: "POST",
                    url: window.location,
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else{
                $.ajax({
                    type: "POST",
                    url: "erase-folder",
                    data: "id=" + folder_id,
                    success: function(data) {
                        instantsearch(search_box.value);
                        content_buttons.innerHTML = null;
                    }
                });
            }
        }
    });
});

$(document).on('click', '#content-button-restore-folder', function(e){
    e.stopPropagation();
    e.preventDefault();
    var content_buttons = document.getElementById("content-buttons");
    var search_box = document.getElementById("search-box");
    var folder_id =  document.getElementById("restore-folder-id").value;
    $.ajax({
        type: "POST",
        url: "restore-folder",
        data: "id=" + folder_id,
        success: function(data) {
            if(search_box.value === ""){
                $.ajax({
                    type: "POST",
                    url: window.location,
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else{
                $.ajax({
                    type: "POST",
                    url: "restore-folder",
                    data: "id=" + folder_id,
                    success: function(data) {
                        instantsearch(search_box.value);
                        content_buttons.innerHTML = null;
                    }
                });
            }
        }
    });
});
$(document).on('click', '#content-button-hide-folder', function(e){
    e.stopPropagation();
    e.preventDefault();
    var content_buttons = document.getElementById("content-buttons");
    var search_box = document.getElementById("search-box");
    var folder_id =  document.getElementById("hide-folder-id").value;
    $.ajax({
        type: "POST",
        url: "hide-folder",
        data: "id=" + folder_id,
        success: function(data) {
            if(search_box.value === ""){
                $.ajax({
                    type: "POST",
                    url: window.location,
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-unhide-folder', function(e){
    e.stopPropagation();
    e.preventDefault();
    var content_buttons = document.getElementById("content-buttons");
    var search_box = document.getElementById("search-box");
    var folder_id =  document.getElementById("unhide-folder-id").value;
    $.ajax({
        type: "POST",
        url: "unhide-folder",
        data: "id=" + folder_id,
        success: function(data) {
            if(search_box.value === ""){
                $.ajax({
                    type: "POST",
                    url: window.location,
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            } else{
                instantsearch(search_box.value);
                content_buttons.innerHTML = null;
            }
        }
    });
});
$(document).on('click', '#content-button-share-file', function(e){
    e.stopPropagation();
    e.preventDefault();
    var file_id =  document.getElementById("share-file-id").value;
    $.ajax({
        type: "POST",
        url: "get_file_share_overlay",
        data: "id=" + file_id,
        success: function(data) {
            $("body").append(data);
            $('#file-share-overlay').modal('show');
        }
    });
});
$(document).on('click', '#content-button-share-files', function(e){
    e.stopPropagation();
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "get_files_share_overlay",
        data: "opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data) {
            $("body").append(data);
            $('#files-share-overlay').modal('show');
        }
    });
});
$(document).on('click', '#content-button-share-folder', function(e){
    e.stopPropagation();
    e.preventDefault();
    var folder_id =  document.getElementById("share-folder-id").value;
    $.ajax({
        type: "POST",
        url: "get_folder_share_overlay",
        data: "id=" + folder_id,
        success: function(data) {
            $("body").append(data);
            $('#folder-share-overlay').modal('show');
        }
    });
});
$(document).on('click', '#create-document-link', function(e){
    $.ajax({
        type: "POST",
        url: "create_folder_overlay",
        success: function(data) {
            $("body").append(data);
            $('#folder-create-overlay').modal('show');
        }
    });
});
$(document).on('click', '#content-button-create-folder', function(e){
    $.ajax({
        type: "POST",
        url: "create_department_folder_overlay",
        success: function(data) {
            $("body").append(data);
            $('#department-folder-create-overlay').modal('show');
        }
    });
});
$(document).on('click', '#content-button-upload-file', function(e){
    e.preventDefault();
    e.stopPropagation();
    var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;

    $.ajax({
        type: "POST",
        url: "upload_file_overlay",
        data: "folder_id="+folderId,
        success: function(data) {
            $("body").append(data);
            $('#file-upload-overlay').modal('show');
        }
    });
});
function instantsearch(keyword) {                                                                                                               // AJAX?
    var preloadDocsBox = document.getElementById("preload-documents");
    var foldersHeader = document.getElementById("preload-box-header-name");
    var contentDiv = document.getElementById("content");
    var searchBoxValue = document.getElementById("search-box").value;

    if (searchBoxValue === ""){
        keyword = "keywordisnullreturnnothing";
    }
    $.ajax({
        type: "POST",
        url: "searchresults",
        data:'keyword=' + keyword,
        success: function(data){
            preloadDocsBox.innerHTML = data;

            $.ajax({
                type: "POST",
                url: "filessearchresults",
                data:'keyword=' + keyword,
                success: function(data){
                    var currentViewID = document.getElementById("current-view-id");

                    if(currentViewID != null){
                        if(currentViewID.value === 3){
                            var fileInfoBox = document.getElementById("file-info-box");
                            if(fileInfoBox != null){
                                fileInfoBox.className = "file-info-box-list";
                                fileInfoBox.id = "file-info-box-list";
                            }
                        } else{
                            var fileInfoBox = document.getElementById("file-info-box-list");
                            if(fileInfoBox != null){
                                fileInfoBox.className = "file-info-box";
                                fileInfoBox.id = "file-info-box";
                            }
                        }
                    } else{

                    }

                   /* if (parseInt(currentViewID.value) === 2) {
                        var files = document.getElementsByClassName("file-details");
                        var file_info = document.getElementById("file-info-details");
                        var fileInfoBox = document.getElementById("file-info-box");
                    } else if (parseInt(currentViewID.value) === 3) {
                        var files = document.getElementsByClassName("file-list");
                        var file_info = document.getElementById("file-info-list");
                        var fileInfoBox = document.getElementById("file-info-box-list");
                    } else {
                        var files = document.getElementsByClassName("file");
                        var file_info = document.getElementById("file-info");
                    }*/

/*

                    currentViewID.value = parseInt(fileViewID);
                    if(parseInt(fileViewID) === 3){
                        fileInfoBox.className = "file-info-box-list";
                        fileInfoBox.id = "file-info-box-list";
                    } else {
                        fileInfoBox.className = "file-info-box";
                        fileInfoBox.id = "file-info-box";
                    }
*/



                    contentDiv.innerHTML = data;
                    if(foldersHeader != null){
                        foldersHeader.innerText = "Результаты поиска папок";
                    }
                }
            });
        }
    });
}
$(document).on('keyup', '#search-box', function()
{
    var keyword = this.value;                                                                                           // AJAX => files_box_header
    instantsearch(keyword);

});
$(document).on('dblclick', '.preloaded-document-title > span', function(e) {
    var parent = this.parentNode;
    var folder_name = this.innerText;
    this.style.display = "none";
    var input = document.createElement("input");
    input.type = "text";
    input.id = "rename-folder";
    input.value = folder_name;
    parent.appendChild(input);
    $(input).select();
});

$(document).on('click', 'body', function() {
    $(".active").each(function () {
        $(this).removeClass("active");
    });

    var rename_input = document.getElementById("rename-folder");
    if (document.getElementsByClassName("document-opened").length > 0) {
        var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
        if (rename_input != null) {
            var span = rename_input.parentNode.children[0];
            if (span.innerText !== rename_input.value) {
                $.ajax({
                    type: "POST",
                    url: "rename-folder",
                    data: 'folder-name=' + rename_input.value + "&id=" + folderId,
                    success: function (data) {
                        if (data.message != null) {
                            span.innerText = rename_input.value;
                        }
                    }
                });
            }
            rename_input.parentNode.removeChild(rename_input);
            span.style.display = "block";
        }
    }

});
$(document).on('keyup', '#rename-folder', function(e) {
    var span = this.parentNode.children[0];
    var _this = this;
    var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    if(e.keyCode === 13){
        if(span.innerText !== this.value){
            //
            $.ajax({
                type: "POST",
                url: "rename-folder",
                data:'folder-name=' + this.value +"&id=" + folderId,
                success: function(data){
                    if(data.message != null){
                        span.innerText = _this.value;
                    }
                }
            });
        }
        this.parentNode.removeChild(this);
        span.style.display = "block";
    }
});
$(document).on('click', '#rename-folder', function(e) {
    e.stopPropagation();
    e.preventDefault();
});

$(document).on('dblclick', '.file-name > span', function(e) {
    var rename_input = document.getElementById("rename-file");
    if(rename_input != null) {
        rename_input.parentNode.children[0].style.display = "block";
        rename_input.parentNode.removeChild(rename_input);
    }
    var parent = this.parentNode;
    var file_name = this.innerText;
    this.style.display = "none";
    var input = document.createElement("input");
    input.type = "text";
    input.id = "rename-file";
    input.value = file_name;
    parent.appendChild(input);
    $(input).select();
});

$(document).on('click', 'body', function(e) {
    var rename_input = document.getElementById("rename-file");
    if(rename_input != null) {
        var fileId = rename_input.parentNode.parentNode.children[0].value;
        var span = rename_input.parentNode.children[0];
        if(span.innerText !== rename_input.value){
            $.ajax({
                type: "POST",
                url: "rename-file",
                data:'file-name=' + rename_input.value +"&id=" + fileId,
                success: function(data){
                    if(data.message != null){
                        span.innerText = rename_input.value;
                    }
                }
            });
        }
        rename_input.parentNode.removeChild(rename_input);
        span.style.display = "block";
    }
});
var cntrlIsPressed = false;
$(document).keydown(function(event){
    if(event.which === 17){
        cntrlIsPressed = true;
    }
});

$(document).keyup(function(event){
    if(event.which === 17){
        cntrlIsPressed = false;
    }
});
function isFileOpened(file_id) {
    var selected_files = document.getElementsByClassName("file-opened");
    var flag = false;
    for (var i = 0; i < selected_files.length ; i++) {
        if(file_id === selected_files[i].children[0].value){
            flag = true;
        }
    }
    return flag;
}
function isFileDetailsOpened(file_id) {
    var selected_files = document.getElementsByClassName("file-details-opened");
    var flag = false;
    for (var i = 0; i < selected_files.length ; i++) {
        if(file_id === selected_files[i].children[0].value){
            flag = true;
        }
    }
    return flag;
}
function isFileDetailsListOpened(file_id) {
    var selected_files = document.getElementsByClassName("file-list-opened");
    var flag = false;
    for (var i = 0; i < selected_files.length ; i++) {
        if(file_id === selected_files[i].children[0].value){
            flag = true;
        }
    }
    return flag;
}

function getOpenedFiles(){
    var opened_files = [];
    var currentViewID = document.getElementById("current-view-id");

    if(currentViewID != null && currentViewID.value == 2){
        var selected_files_details = document.getElementsByClassName("file-details-opened");
        for (var i = 0; i < selected_files_details.length ; i++) {
            opened_files.push(selected_files_details[i].children[0].value);
        }
        return opened_files;
    } else if(currentViewID != null && currentViewID.value == 3){
        var selected_files_details = document.getElementsByClassName("file-list-opened");
        for (var i = 0; i < selected_files_details.length ; i++) {
            opened_files.push(selected_files_details[i].children[0].value);
        }
        return opened_files;
    } else{
        var selected_files = document.getElementsByClassName("file-opened");
        for (var i = 0; i < selected_files.length ; i++) {
            opened_files.push(selected_files[i].children[0].value);
        }
        return opened_files;
    }
}


$(document).on('click', '.file', function(e){
    e.preventDefault();
    e.stopPropagation();
    var id = this.children[0].value;
    var file_divs = document.getElementsByClassName("file");
    var content_buttons = document.getElementById("content-buttons");
    var filesAttributesInfo = document.getElementById("files-attributes-info");
    var selected_files = document.getElementsByClassName("file-opened");
    var isTrash = window.location.pathname.replace(getContextPath(), "").replace("/","");

    if(cntrlIsPressed === true){
        if(isFileOpened(id)){
            $(this).removeClass("file-opened");
        } else {
            $(this).addClass("file-opened");
        }
        if(selected_files.length > 1){
            var selected_filesIDS = [];
                for (var i = 0; i < selected_files.length ; i++) {
                    selected_filesIDS.push(selected_files[i].children[0].value);
                }
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                data: "ids=" + JSON.stringify(selected_filesIDS),
                success: function(data){
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
        if(selected_files.length > 0){
            $.ajax({
                type: "POST",
                url: "loadmanyfilesmenu",
                data: "isTrash="+isTrash+"&files=" + JSON.stringify(getOpenedFiles()),
                success: function(data){
                    content_buttons.innerHTML = data;
                }
            });
        } else if(selected_files.length < 1){
            var openedDoc = document.getElementsByClassName("document-opened");
            if(openedDoc.length>1){
                var folderId = openedDoc[0].children[0].value;
                var documentBox = document.getElementById("content-box");
                $.ajax({
                    type: "POST",
                    url: "openfolder",
                    data: 'id=' + folderId,
                    success: function (data) {
                        documentBox.innerHTML = data;
                    }
                });
            }
            var filesAttributesInfo = document.getElementById("files-attributes-info");
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                success: function (data) {
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
        if(selected_files.length === 1){
            var id = selected_files[0].children[0].value;
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                data: "id=" + id,
                success: function (data) {
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
    } else {
        for (var i = 0; i < file_divs.length ; i++) {
            $(file_divs[i]).removeClass("file-opened");
        }
        $(this).addClass("file-opened");
        $.ajax({
            type: "POST",
            url: "loadfilesmenu",
            data: "isTrash="+isTrash+"&id=" + id,
            success: function(data) {
                content_buttons.innerHTML = data;
            }
        });
        $.ajax({
            type: "POST",
            url: "loadfilesattributesinfo",
            data: "id=" + id,
            success: function (data) {
                filesAttributesInfo.innerHTML = data;
            }
        });
    }
});
$(document).on('click', '.file-details', function(e){
    e.preventDefault();
    e.stopPropagation();
    var id = this.children[0].value;
    var file_divs = document.getElementsByClassName("file-details");
    var content_buttons = document.getElementById("content-buttons");
    var filesAttributesInfo = document.getElementById("files-attributes-info");
    var selected_files = document.getElementsByClassName("file-details-opened");
    var isTrash = window.location.pathname.replace(getContextPath(), "").replace("/","");

    if(cntrlIsPressed === true){
        if(isFileDetailsOpened(id)){
            $(this).removeClass("file-details-opened");
        } else {
            $(this).addClass("file-details-opened");
        }
        if(selected_files.length > 1){
            var selected_filesIDS = [];
            for (var i = 0; i < selected_files.length ; i++) {
                selected_filesIDS.push(selected_files[i].children[0].value);
            }
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                data: "ids=" + JSON.stringify(selected_filesIDS),
                success: function(data){
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
        if(selected_files.length > 0){
            $.ajax({
                type: "POST",
                url: "loadmanyfilesmenu",
                data: "isTrash="+isTrash+"&files=" + JSON.stringify(getOpenedFiles()),
                success: function(data){
                    content_buttons.innerHTML = data;
                }
            });
        } else if(selected_files.length < 1){
            var openedDoc = document.getElementsByClassName("document-opened");
            if(openedDoc !== null){
                if(openedDoc.length>1){
                    var folderId = openedDoc[0].children[0].value;
                    var documentBox = document.getElementById("content-box");
                    $.ajax({
                        type: "POST",
                        url: "openfolder",
                        data: 'id=' + folderId,
                        success: function (data) {
                            documentBox.innerHTML = data;
                        }
                    });
                }
            }
            var filesAttributesInfo = document.getElementById("files-attributes-info");
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                success: function (data) {
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
        if(selected_files.length === 1){
            var id = selected_files[0].children[0].value;
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                data: "id=" + id,
                success: function (data) {
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
    } else {
        for (var i = 0; i < file_divs.length ; i++) {
            $(file_divs[i]).removeClass("file-details-opened");
        }
        $(this).addClass("file-details-opened");
        $.ajax({
            type: "POST",
            url: "loadfilesmenu",
            data: "isTrash="+isTrash+"&id=" + id,
            success: function(data) {
                content_buttons.innerHTML = data;
            }
        });
        $.ajax({
            type: "POST",
            url: "loadfilesattributesinfo",
            data: "id=" + id,
            success: function (data) {
                filesAttributesInfo.innerHTML = data;
            }
        });
    }
});

$(document).on('click', '.file-list', function(e){
    e.preventDefault();
    e.stopPropagation();
    var id = this.children[0].value;
    var file_divs = document.getElementsByClassName("file-list");
    var content_buttons = document.getElementById("content-buttons");
    var filesAttributesInfo = document.getElementById("files-attributes-info");
    var selected_files = document.getElementsByClassName("file-list-opened");
    var isTrash = window.location.pathname.replace(getContextPath(), "").replace("/","");

    if(cntrlIsPressed === true){
        if(isFileDetailsListOpened(id)){
            $(this).removeClass("file-list-opened");
        } else {
            $(this).addClass("file-list-opened");
        }
        if(selected_files.length > 1){
            var selected_filesIDS = [];
            for (var i = 0; i < selected_files.length ; i++) {
                selected_filesIDS.push(selected_files[i].children[0].value);
            }
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                data: "ids=" + JSON.stringify(selected_filesIDS),
                success: function(data){
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
        if(selected_files.length > 0){
            $.ajax({
                type: "POST",
                url: "loadmanyfilesmenu",
                data: "isTrash="+isTrash+"&files=" + JSON.stringify(getOpenedFiles()),
                success: function(data){
                    content_buttons.innerHTML = data;
                }
            });
        } else if(selected_files.length < 1){
            var openedDoc = document.getElementsByClassName("document-opened");
            if(openedDoc !== null){
                if(openedDoc.length>1){
                    var folderId = openedDoc[0].children[0].value;
                    var documentBox = document.getElementById("content-box");
                    $.ajax({
                        type: "POST",
                        url: "openfolder",
                        data: 'id=' + folderId,
                        success: function (data) {
                            documentBox.innerHTML = data;
                        }
                    });
                }
            }
            var filesAttributesInfo = document.getElementById("files-attributes-info");
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                success: function (data) {
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
        if(selected_files.length === 1){
            var id = selected_files[0].children[0].value;
            $.ajax({
                type: "POST",
                url: "loadfilesattributesinfo",
                data: "id=" + id,
                success: function (data) {
                    filesAttributesInfo.innerHTML = data;
                }
            });
        }
    } else {
        for (var i = 0; i < file_divs.length ; i++) {
            $(file_divs[i]).removeClass("file-list-opened");
        }
        $(this).addClass("file-list-opened");
        $.ajax({
            type: "POST",
            url: "loadfilesmenu",
            data: "isTrash="+isTrash+"&id=" + id,
            success: function(data) {
                content_buttons.innerHTML = data;
            }
        });
        $.ajax({
            type: "POST",
            url: "loadfilesattributesinfo",
            data: "id=" + id,
            success: function (data) {
                filesAttributesInfo.innerHTML = data;
            }
        });
    }
});

$(document).on('dblclick', '.file', function(e) {
    e.preventDefault();
    e.stopPropagation();
    if(document.getElementById("download-file-id") != null) {
        var file_id = document.getElementById("download-file-id").value;
        window.location = "download-file?id=" + file_id;
    }
});
$(document).on('dblclick', '.file-details', function(e) {
    e.preventDefault();
    e.stopPropagation();
    if(document.getElementById("download-file-id") != null){
        var file_id =  document.getElementById("download-file-id").value;
        window.location = "download-file?id="+file_id;
    }
});
$(document).on('dblclick', '.file-list', function(e) {
    e.preventDefault();
    e.stopPropagation();
    if(document.getElementById("download-file-id") != null){
        var file_id =  document.getElementById("download-file-id").value;
        window.location = "download-file?id="+file_id;
    }
});
$(document).on('keyup', '#rename-file', function(e) {
    var span = this.parentNode.children[0];
    var _this = this;
    if(document.getElementsByClassName("file-opened")[0].children[0] != null) {
        var fileId = document.getElementsByClassName("file-opened")[0].children[0].value;
    }
    if(e.keyCode === 13){
        if(span.innerText !== this.value){
            //
            $.ajax({
                type: "POST",
                url: "rename-file",
                data:'file-name=' + this.value +"&id=" + fileId,
                success: function(data){
                    if(data.message != null){
                        span.innerText = _this.value;
                    }
                }
            });
        }
        this.parentNode.removeChild(this);
        span.style.display = "block";
    }
});
$(document).on('click', '#rename-file', function(e) {
    e.stopPropagation();
    e.preventDefault();
});

$(document).on('keyup', '#user-to-share-search', function(e) {
    var value = this.value;
    var sugg = document.getElementById("suggestion-box-file-share");
    var lang = document.getElementById("lang-js").value;
    if(value.trim() !== ""){
        $.ajax({
            type: "POST",
            url: "get_users",
            data:'keyword=' + value,
            success: function(data){
                if(data.length === 0) {
                    sugg.innerHTML = "";
                    sugg.style.display = "none";
                }else if(data.length > 0) {
                    sugg.innerHTML = "";
                    $.each(data, function (index, data) {
                        var name = data.lastName + " " + data.firstName;
                        var position = "";
                        if(data.middleName != null){
                            name += " " + data.middleName;
                        }
                        if(data.position != null){
                            if(lang === "kz"){
                                position = " (" +  data.position.localeKZ + ")";
                            } else {
                                position =" (" +  data.position.localeRU + ")";
                            }
                        }
                        $(sugg).append("<ul><li onclick=\"selectUserToShare(this);\" value=\"" + data.id_User + "\">" + name + position + "</li></ul>")
                        sugg.style.display = "block";
                    })
                }
            }
        });
    } else{
        sugg.innerHTML = "";
        sugg.style.display = "none";
    }
});
$(document).on('keyup', '#user-to-share-files-search', function(e) {
    var value = this.value;
    var sugg = document.getElementById("suggestion-box-files-share");
    var lang = document.getElementById("lang-js").value;
    if(value.trim() !== ""){
        $.ajax({
            type: "POST",
            url: "get_users",
            data:'keyword=' + value,
            success: function(data){
                if(data.length === 0) {
                    sugg.innerHTML = "";
                    sugg.style.display = "none";
                }else if(data.length > 0) {
                    sugg.innerHTML = "";
                    $.each(data, function (index, data) {
                        var name = data.lastName + " " + data.firstName;
                        var position = "";
                        if(data.middleName != null){
                            name += " " + data.middleName;
                        }
                        if(data.position != null){
                            if(lang === "kz"){
                                position = " (" +  data.position.localeKZ + ")";
                            } else {
                                position =" (" +  data.position.localeRU + ")";
                            }
                        }
                        $(sugg).append("<ul><li onclick=\"selectUserToShareFiles(this);\" value=\"" + data.id_User + "\">" + name + position + "</li></ul>")
                        sugg.style.display = "block";
                    })
                }
            }
        });
    } else{
        sugg.innerHTML = "";
        sugg.style.display = "none";
    }
});

function selectUserToShare(element){
    var search_user_input = document.getElementById("user-to-share-search");
    var sugg = document.getElementById("suggestion-box-file-share");
    var selected = document.getElementById("selected-users").children[1];
    var selected_count = document.getElementById("selected-users-count");
    var isExists = false;
    var selected_users = document.getElementsByClassName("selected-user");
    for (var i = 0; i < selected_users.length ; i++) {
        if(parseInt(element.value) === parseInt(selected_users[i].value)){
            isExists = true;
        }
    }
    if(!isExists){
        var main_div = document.createElement("div");
        var hidden_input =  document.createElement("input");
        var input =  document.createElement("input");
        var delete_div =  document.createElement("div");
        main_div.className = "selected-user-box";
        hidden_input.type = "hidden";
        hidden_input.className = "selected-user";
        hidden_input.value = element.value;
        input.type = "text";
        input.readOnly = true;
        input.className = "selected-user-name";
        input.value = element.innerText;
        delete_div.className = "delete-selected-user";
        main_div.append(hidden_input);
        main_div.append(input);
        main_div.append(delete_div);
        selected.append(main_div);
        selected_count.value = parseInt(selected_count.value) + 1;
    }
    sugg.innerHTML = "";
    sugg.style.display = "none";
    search_user_input.value = "";
}
function selectUserToShareFiles(element){
    var search_user_input = document.getElementById("user-to-share-files-search");
    var sugg = document.getElementById("suggestion-box-files-share");
    var selected = document.getElementById("selected-users-to-share-files").children[1];
    var selected_count = document.getElementById("selected-users-to-share-files-count");
    var isExists = false;
    var selected_users = document.getElementsByClassName("selected-user-to-share-files");
    for (var i = 0; i < selected_users.length ; i++) {
        if(parseInt(element.value) === parseInt(selected_users[i].value)){
            isExists = true;
        }
    }
    if(!isExists){
        var main_div = document.createElement("div");
        var hidden_input =  document.createElement("input");
        var input =  document.createElement("input");
        var delete_div =  document.createElement("div");
        main_div.className = "selected-user-to-share-files-box";
        hidden_input.type = "hidden";
        hidden_input.className = "selected-user-to-share-files";
        hidden_input.value = element.value;
        input.type = "text";
        input.readOnly = true;
        input.className = "selected-user-to-share-files-name";
        input.value = element.innerText;
        delete_div.className = "delete-selected-user-to-share-files";
        main_div.append(hidden_input);
        main_div.append(input);
        main_div.append(delete_div);
        selected.append(main_div);
        selected_count.value = parseInt(selected_count.value) + 1;
    }
    sugg.innerHTML = "";
    sugg.style.display = "none";
    search_user_input.value = "";
}



$(document).on('click', '.close-overlay', function(){
    var overlay = this.parentNode.parentNode;
    $(overlay).modal('hide');
});
$(document).on('click', '.delete-selected-user', function(){
    var parent = this.parentNode;
    var selected_users = document.getElementById("selected-users-box");
    selected_users.removeChild(parent);
});
$(document).on('click', '.delete-selected-user-to-share-folder', function(){
    var parent = this.parentNode;
    var selected_users = document.getElementById("selected-users-box-to-share-folder");
    selected_users.removeChild(parent);
});
$(document).on('click', '#overlay-share-with-users', function() {
    var users = [];
    var selected_users = document.getElementsByClassName("selected-user");
    var file_id = document.getElementById("sharing-file-id").value;
    for (var i = 0; i < selected_users.length ; i++) {
        users.push(selected_users[i].value);
    }
    var popUp_text = document.getElementById("popUp-info");
    var popUp_overlay = document.getElementById("popUp-overlay");
    var error_field = document.getElementById("file-share-error-msg");
    var file_share_overlay = document.getElementById("file-share-overlay");
    $.ajax({
        type: "POST",
        url: "share-file",
        data:'id=' + file_id + "&users=" + JSON.stringify(users),
        success: function(data){
            if(data.error != null){
                error_field.innerText = data.error;
            } else if(data.message != null){
                popUp_text.innerText = data.message;
                $(file_share_overlay).modal('hide');
                $(file_share_overlay).remove();
                $(".modal-backdrop").remove();
                $(popUp_overlay).modal('show');
                setTimeout(closePopUp, 1.5 * 1000);

            }
        }
    });
});
$(document).on('click', '#overlay-share-files-with-users', function() {
    var users = [];
    var selected_users = document.getElementsByClassName("selected-user-to-share-files");
    for (var i = 0; i < selected_users.length ; i++) {
        users.push(selected_users[i].value);
    }
    var popUp_text = document.getElementById("popUp-info");
    var popUp_overlay = document.getElementById("popUp-overlay");
    var error_field = document.getElementById("files-share-error-msg");
    var file_share_overlay = document.getElementById("files-share-overlay");
    $.ajax({
        type: "POST",
        url: "share-files",
        data: "users=" + JSON.stringify(users)+"&opened_files=" + JSON.stringify(getOpenedFiles()),
        success: function(data){
            if(data.error != null){
                error_field.innerText = data.error;
            } else if(data.message != null){
                popUp_text.innerText = data.message;
                $(file_share_overlay).modal('hide');
                $(file_share_overlay).remove();
                $(".modal-backdrop").remove();
                $(popUp_overlay).modal('show');
                setTimeout(closePopUp, 1.5 * 1000);

            }
        }
    });
});
$(document).on('keyup', '#user-to-share-folder-search', function(e) {
    var value = this.value;
    var sugg = document.getElementById("suggestion-box-folder-share");
    var lang = document.getElementById("lang-js").value;
    if(value.trim() !== ""){
        $.ajax({
            type: "POST",
            url: "get_users",
            data:'keyword=' + value,
            success: function(data){
                if(data.length === 0) {
                    sugg.innerHTML = "";
                    sugg.style.display = "none";
                }else if(data.length > 0) {
                    sugg.innerHTML = "";
                    $.each(data, function (index, data) {
                        var name = data.lastName + " " + data.firstName;
                        var position = "";
                        if(data.middleName != null){
                            name += " " + data.middleName;
                        }
                        if(data.position != null){
                            if(lang === "kz"){
                                position = " (" +  data.position.localeKZ + ")";
                            } else {
                                position =" (" +  data.position.localeRU + ")";
                            }
                        }
                        $(sugg).append("<ul><li onclick=\"selectUserToShareFolder(this);\" value=\"" + data.id_User + "\">" + name + position + "</li></ul>")
                        sugg.style.display = "block";
                    })
                }
            }
        });
    } else{
        sugg.innerHTML = "";
        sugg.style.display = "none";
    }
});
function selectUserToShareFolder(element){
    var search_user_input = document.getElementById("user-to-share-folder-search");
    var sugg = document.getElementById("suggestion-box-folder-share");
    var selected = document.getElementById("selected-users-to-share-folder").children[1];
    var selected_count = document.getElementById("selected-users-count-to-share-folder");
    var isExists = false;
    var selected_users = document.getElementsByClassName("selected-user-to-share-folder");
    for (var i = 0; i < selected_users.length ; i++) {
        if(parseInt(element.value) === parseInt(selected_users[i].value)){
            isExists = true;
        }
    }
    if(!isExists){
        var main_div = document.createElement("div");
        var hidden_input =  document.createElement("input");
        var input =  document.createElement("input");
        var delete_div =  document.createElement("div");
        main_div.className = "selected-user-box-to-share-folder";
        hidden_input.type = "hidden";
        hidden_input.className = "selected-user-to-share-folder";
        hidden_input.value = element.value;
        input.type = "text";
        input.readOnly = true;
        input.className = "selected-user-name-to-share-folder";
        input.value = element.innerText;
        delete_div.className = "delete-selected-user-to-share-folder";
        main_div.append(hidden_input);
        main_div.append(input);
        main_div.append(delete_div);
        selected.append(main_div);
        selected_count.value = parseInt(selected_count.value) + 1;
    }
    sugg.innerHTML = "";
    sugg.style.display = "none";
    search_user_input.value = "";
}
$(document).on('click', '#overlay-share-folder-with-users', function() {
    var users = [];
    var selected_users = document.getElementsByClassName("selected-user-to-share-folder");
    var folder_id = document.getElementById("sharing-folder-id").value;
    for (var i = 0; i < selected_users.length ; i++) {
        users.push(selected_users[i].value);
    }
    var popUp_text = document.getElementById("popUp-info");
    var popUp_overlay = document.getElementById("popUp-overlay");
    var error_field = document.getElementById("folder-share-error-msg");
    var folder_share_overlay = document.getElementById("folder-share-overlay");
    $.ajax({
        type: "POST",
        url: "share-folder",
        data:'id=' + folder_id + "&users=" + JSON.stringify(users),
        success: function(data){
            if(data.error != null){
                error_field.innerText = data.error;
            } else if(data.message != null){
                popUp_text.innerText = data.message;
                $(folder_share_overlay).modal('hide');
                $(folder_share_overlay).remove();
                $(".modal-backdrop").remove();
                $(popUp_overlay).modal('show');
                setTimeout(closePopUp, 1.5 * 1000);

            }
        }
    });
});
$(document).on('click', '#overlay-create-folder', function() {
    var folder_name = document.getElementById("folder-name").value;
    var hidden = $("#folder_flagHidden").is(":checked");
    var popUp_text = document.getElementById("popUp-info");
    var popUp_overlay = document.getElementById("popUp-overlay");
    var error_field = document.getElementById("folder-create-error-msg");
    var folder_share_overlay = document.getElementById("folder-create-overlay");
    $.ajax({
        type: "POST",
        url: "create-folder",
        data:'folder-name=' + folder_name + '&flag-hidden=' + hidden,
        success: function(data){
            if(data.error != null){
                error_field.innerText = data.error;
            } else if(data.message != null){
                popUp_text.innerText = data.message;
                $(folder_share_overlay).modal('hide');
                $(folder_share_overlay).remove();
                $(".modal-backdrop").remove();
                $(popUp_overlay).modal('show');
                setTimeout(closePopUp, 1.5 * 1000);
                $.ajax({
                    type: "POST",
                    url: "my",
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            }
        }
    });
});
$(document).on('click', '#overlay-create-department-folder', function() {
    var folder_name = document.getElementById("department-folder-name").value;
    var departmentID = document.getElementById("department-id").value;
    var hidden = $("#department_folder_flagHidden").is(":checked");
    var popUp_text = document.getElementById("popUp-info");
    var popUp_overlay = document.getElementById("popUp-overlay");
    var error_field = document.getElementById("department-folder-create-error-msg");
    var folder_share_overlay = document.getElementById("department-folder-create-overlay");
    $.ajax({
        type: "POST",
        url: "create-department-folder",
        data:'folder-name=' + folder_name + '&flag-hidden=' + hidden + '&department-id='+departmentID,
        success: function(data){
            if(data.error != null){
                error_field.innerText = data.error;
            } else if(data.message != null){
                popUp_text.innerText = data.message;
                $(folder_share_overlay).modal('hide');
                $(folder_share_overlay).remove();
                $(".modal-backdrop").remove();
                $(popUp_overlay).modal('show');
                setTimeout(closePopUp, 1.5 * 1000);

                $.ajax({
                    type: "POST",
                    url: window.location,
                    success: function(data){
                        document.open();
                        document.write(data);
                        document.close();
                    }
                });
            }
        }
    });
});

function addFiles(sel){
    var element = document.getElementById("attached-files");
    var input1 = document.getElementById("files_to_upload");

    for (var i = 0; i < sel.files.length; i++) {
        var input = document.createElement("input");
        var deleteButton = document.createElement("div");
        var div = document.createElement("div");
        var checkbox = document.createElement("input");
        checkbox.type = 'checkbox';
        checkbox.name = 'file_flagHidden'+i;
        checkbox.id = 'file_flagHidden'+i;
        checkbox.title = 'Скрытый файл';
        checkbox.className = "file_flagHidden";
        checkbox.value = sel.files[i].name;
        deleteButton.id = "delete-file";
        input.className = "file-to-upload";
        input.type = 'text';
        input.id = 'file'+ i;
        input.name = 'file'+ i;
        input.readOnly = true;
        input.value = sel.files[i].name;
        div.appendChild(checkbox);
        div.appendChild(input);
        div.appendChild(deleteButton);
        element.appendChild(div);
        tmp_files.push(sel.files[i]);
    }
    input1.files = new FileListItem(tmp_files);
}
var tmp_files = [];
function FileListItem(a) {
    a = [].slice.call(Array.isArray(a) ? a : arguments);
    for (var c, b = c = a.length, d = !0; b-- && d;) d = a[b] instanceof File;
    if (!d) throw new TypeError("expected argument to FileList is File or array of File objects");
    for (b = (new ClipboardEvent("")).clipboardData || new DataTransfer; c--;) b.items.add(a[c]);
    return b.files
}
function closePopUp() {
    $('#popUp-overlay').modal('hide');
}
$(document).on('click', '#attachments-clear', function(){
    document.getElementById("files_to_upload").value = "";
    var element = document.getElementById("attached-files");
    element.innerHTML = '';
});
$(document).on('click', '#delete-file', function(){
    this.parentNode.parentNode.removeChild(this.parentNode);
    var value = this.parentNode.children[0].value.trim();
    var input = document.getElementById("files_to_upload");
    var new_tmp_files = [];
    for (var i = 0; i < tmp_files.length; i++) {
        if(tmp_files[i].name.trim() !== value){
            new_tmp_files.push(tmp_files[i]);
        }
    }
    tmp_files = new_tmp_files;
    input.files = new FileListItem(tmp_files);
});
$(document).on('click', '#overlay-upload-files', function(e) {
    e.preventDefault();
    e.stopPropagation();
    if(document.getElementsByClassName("document-opened")[0] != null){
        var popUp_text = document.getElementById("popUp-info");
        var popUp_overlay = document.getElementById("popUp-overlay");
        var error_field = document.getElementById("file-upload-error-msg");
        var file_upload_overlay = document.getElementById("file-upload-overlay");
        var formData = new FormData(document.getElementById("fileForm"));
        $.ajax({
            type: "POST",
            url: "upload-files",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function(data){
                if(data.error != null){
                    error_field.innerText = data.error;
                } else if(data.message != null){
                    popUp_text.innerText = data.message;
                    $(file_upload_overlay).modal('hide');
                    $(file_upload_overlay).remove();
                    $(".modal-backdrop").remove();
                    $(popUp_overlay).modal('show');
                    setTimeout(closePopUp, 1.5 * 1000);
                }
            }
        });
    }
});

$(document).on('click', '.user-sort-option', function (e) {

    e.stopPropagation();
    e.preventDefault();
    if ($(this).hasClass("active")) {
        $(this).removeClass('active');
    } else {
        $(".active").each(function () {
            $(this).removeClass("active");
        });
        $(this).toggleClass('active');
    }

    /*
    $(".documents-sort").each(function () {
        $(this).removeClass(".active");
    });*/



    var folders = document.getElementById("preload-documents");
    var current_sort_id = document.getElementById("current-sort-id").value;
    var order_type = document.getElementById("sort-order-type").value;
    var req_order_type;
    var this_sort_id = this.value;
    if (parseInt(this_sort_id) !== parseInt(current_sort_id)) {
        $("div.documents-sort-name").text(this.textContent);
        req_order_type = "ASC";
        $("#current-sort-id").val(this_sort_id);
        $("#sort-order-type").val("ASC");
    } else {
        if (order_type === "ASC") {
            req_order_type = "DESC";
            $("#sort-order-type").val("DESC");
        } else if (order_type === "DESC") {
            req_order_type = "ASC";
            $("#sort-order-type").val("ASC");
        }
    }
    var document_opened = document.getElementsByClassName("document-opened");
    var document_opened_id = 0;
    if(document_opened.length > 0){
        document_opened_id = document_opened[0].children[0].value;
    }

    if(folders.children[0].className !== "empty-box"){
        $.ajax({
            type: "POST",
            url: "check-if-searched-folders",
            success: function(data){
                if(data.current_page != null){
                    if(data.current_page === "searchedFolders" ){
                        var preloaded_documents = document.getElementsByClassName("preloaded-document");
                        var folderIDs = [];
                        for(var i = 0; i<preloaded_documents.length; i++){
                            folderIDs.push(preloaded_documents[i].children[0].value);
                        }
                        $.ajax({
                            type: "POST",
                            url: "sort_folders",
                            data: "sort_id=" + this_sort_id + "&order_type=" + req_order_type + "&folderIDs="+JSON.stringify(folderIDs),
                            success: function (data) {
                                folders.innerHTML = "";
                                $.each(data, function (index, data) {
                                    var folder_id = data.id_Folder;
                                    var folder_name = data.name;
                                    var m = new Date(data.creationDate);
                                    var folder_creationDate = ("0" + m.getUTCDate()).slice(-2) + "." + ("0" + (m.getUTCMonth()+1)).slice(-2) + "." +
                                        ("0" + m.getUTCFullYear()).slice(-2) + " " + ("0" + m.getHours()).slice(-2) + ":" + ("0" +
                                            m.getUTCMinutes()).slice(-2) + ":" + ("0" + m.getUTCSeconds()).slice(-2);
                                    var user = data.userCreated;
                                    var user_name = user.lastName + " " + user.firstName.charAt(0).toString();
                                    if (user.middleName != null) {
                                        user_name += ". " + user.middleName.charAt(0).toString()+".";
                                    }
                                    if(document_opened_id == folder_id){
                                        $(folders).append("<div class=\"preloaded-document document-opened\">" +
                                            "<input type=\"hidden\" class=\"folder-id\" value=\"" + folder_id + "\">" +
                                            "<div class=\"folder-icon-div\">" +
                                            "<span class=\"folder-icon\"></span></div>" +
                                            "<div class=\"preloaded-document-info\">" +
                                            "<div class=\"preloaded-document-title\"><span>" + folder_name + "</span></div>" +
                                            "<div class=\"preloaded-document-reg-number-date\">" +
                                            "<div class=\"preloaded-document-reg-number\">" +
                                            "<span>" + user_name + "</span></div>" +
                                            "<div class=\"preloaded-document-date\"><span>" + folder_creationDate + "</span></div>" +
                                            "</div></div></div>");
                                    } else {
                                        $(folders).append("<div class=\"preloaded-document\">" +
                                            "<input type=\"hidden\" class=\"folder-id\" value=\"" + folder_id + "\">" +
                                            "<div class=\"folder-icon-div\">" +
                                            "<span class=\"folder-icon\"></span></div>" +
                                            "<div class=\"preloaded-document-info\">" +
                                            "<div class=\"preloaded-document-title\"><span>" + folder_name + "</span></div>" +
                                            "<div class=\"preloaded-document-reg-number-date\">" +
                                            "<div class=\"preloaded-document-reg-number\">" +
                                            "<span>" + user_name + "</span></div>" +
                                            "<div class=\"preloaded-document-date\"><span>" + folder_creationDate + "</span></div>" +
                                            "</div></div></div>");
                                    }
                                });
                            }
                        });
                    } else if(data.current_page !== "searchedFolders" ) {
                        $.ajax({
                            type: "POST",
                            url: "sort_folders",
                            data: "sort_id=" + this_sort_id + "&order_type=" + req_order_type,
                            success: function (data) {
                                folders.innerHTML = "";
                                $.each(data, function (index, data) {
                                    var folder_id = data.id_Folder;
                                    var folder_name = data.name;
                                    var m = new Date(data.creationDate);
                                    var folder_creationDate = ("0" + m.getUTCDate()).slice(-2) + "." + ("0" + (m.getUTCMonth()+1)).slice(-2) + "." +
                                        ("0" + m.getUTCFullYear()).slice(-2) + " " + ("0" + m.getHours()).slice(-2) + ":" + ("0" +
                                            m.getUTCMinutes()).slice(-2) + ":" + ("0" + m.getUTCSeconds()).slice(-2);
                                    var user = data.userCreated;
                                    var user_name = user.lastName + " " + user.firstName.charAt(0).toString();
                                    if (user.middleName != null) {
                                        user_name += ". " + user.middleName.charAt(0).toString()+".";
                                    }
                                    if(document_opened_id == folder_id){
                                        $(folders).append("<div class=\"preloaded-document document-opened\">" +
                                            "<input type=\"hidden\" class=\"folder-id\" value=\"" + folder_id + "\">" +
                                            "<div class=\"folder-icon-div\">" +
                                            "<span class=\"folder-icon\"></span></div>" +
                                            "<div class=\"preloaded-document-info\">" +
                                            "<div class=\"preloaded-document-title\"><span>" + folder_name + "</span></div>" +
                                            "<div class=\"preloaded-document-reg-number-date\">" +
                                            "<div class=\"preloaded-document-reg-number\">" +
                                            "<span>" + user_name + "</span></div>" +
                                            "<div class=\"preloaded-document-date\"><span>" + folder_creationDate + "</span></div>" +
                                            "</div></div></div>");
                                    } else {
                                        $(folders).append("<div class=\"preloaded-document\">" +
                                            "<input type=\"hidden\" class=\"folder-id\" value=\"" + folder_id + "\">" +
                                            "<div class=\"folder-icon-div\">" +
                                            "<span class=\"folder-icon\"></span></div>" +
                                            "<div class=\"preloaded-document-info\">" +
                                            "<div class=\"preloaded-document-title\"><span>" + folder_name + "</span></div>" +
                                            "<div class=\"preloaded-document-reg-number-date\">" +
                                            "<div class=\"preloaded-document-reg-number\">" +
                                            "<span>" + user_name + "</span></div>" +
                                            "<div class=\"preloaded-document-date\"><span>" + folder_creationDate + "</span></div>" +
                                            "</div></div></div>");
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }
});

$(document).on('click', '.file-sort-option', function () {

    e.stopPropagation();
    e.preventDefault();
    if ($(this).hasClass("active")) {
        $(this).removeClass('active');
    } else {
        $(".active").each(function () {
            $(this).removeClass("active");
        });
        $(this).toggleClass('active');
    }


   /* $(".files-sort").each(function () {
        $(this).removeClass(".active");
    });*/


    var current_sort_id = document.getElementById("current-sort-id").value;
    var order_type = document.getElementById("sort-order-type").value;
    var req_order_type;
    var this_sort_id = this.value;
    if (parseInt(this_sort_id) !== parseInt(current_sort_id)) {
        $("div.files-sort-name").text(this.textContent);
        req_order_type = "ASC";
        $("#current-sort-id").val(this_sort_id);
        $("#sort-order-type").val("ASC");
    } else {
        if (order_type === "ASC") {
            req_order_type = "DESC";
            $("#sort-order-type").val("DESC");
        } else if (order_type === "DESC") {
            req_order_type = "ASC";
            $("#sort-order-type").val("ASC");
        }
    }

    var currentViewID = document.getElementById("current-view-id");
    if(currentViewID != null) {
        if (parseInt(currentViewID.value) === 2) {
            var files = document.getElementsByClassName("file-details");
            var file_info = document.getElementById("file-info-details");
        } else if (parseInt(currentViewID.value) === 3) {
            var files = document.getElementsByClassName("file-list");
            var file_info = document.getElementById("file-info-list");
        } else {
            var files = document.getElementsByClassName("file");
            var file_info = document.getElementById("file-info");
        }

        if (files.length > 1) {
            var fileIDs = [];
            for (var i = 0; i < files.length; i++) {
                fileIDs.push(files[i].children[0].value);
            }
            $.ajax({
                type: "POST",
                url: "sort_files",
                data: "sort_id=" + this_sort_id + "&order_type=" + req_order_type + "&fileIDs=" + JSON.stringify(fileIDs),
                success: function (data) {
                    file_info.innerHTML = "";
                    var contentButtons = document.getElementById("content-buttons");
                    var filesAttributesInfo = document.getElementById("files-attributes-info");
                    contentButtons.innerHTML = "";
                    $.ajax({
                        type: "POST",
                        url: "loadfilesattributesinfo",
                        success: function (data) {
                            filesAttributesInfo.innerHTML = data;
                        }
                    });

                    if (parseInt(currentViewID.value) === 2) {
                        $(file_info).append("<div class=\"file-details-head\">" +
                            "<input type=\"hidden\" class=\"file-id-details\" >" +
                            "<div class=\"file-icon-div-details\">" + "<span class=\"icons-details\"></span></div>"  +
                            "<div class=\"file-name-details\">" + "<span>Название файла</span></div>" +
                            "<div class=\"file-type-details\">" + "<span class=\"file-type-span-details\">Тип файла</span></div>" +
                            "<div class=\"file-date-details\">" + "<span>Дата создания</span></div>" +
                            "</div>");

                        $.each(data, function (index, data) {
                            var file_id = data.id_File;
                            var file_name = data.name;
                            var user = data.userCreated;
                            var user_name = user.lastName + " " + user.firstName.charAt(0).toString();
                            var id_FileType = data.id_FileType;
                            var fileType = "";
                            var fileIcon = "";
                            if (id_FileType == 1){ fileIcon = "<span class=\"pdf-icon\"></span></div>"; fileType = "Файл pdf";}
                            if (id_FileType == 2) {fileIcon = "<span class=\"doc-icon\"></span></div>"; fileType = "Файл doc";}
                            if (id_FileType == 3) {fileIcon = "<span class=\"docx-icon\"></span></div>"; fileType = "Файл docx";}
                            if (id_FileType == 4) {fileIcon = "<span class=\"xls-icon\"></span></div>"; fileType = "Файл xls";}
                            if (id_FileType == 5) {fileIcon = "<span class=\"xlsx-icon\"></span></div>"; fileType = "Файл xlsx";}
                            if (id_FileType == 6) {fileIcon = "<span class=\"rar-icon\"></span></div>"; fileType = "Файл rar";}
                            if (id_FileType == 7) {fileIcon = "<span class=\"zip-icon\"></span></div>"; fileType = "Файл zip";}
                            if (id_FileType == 8) {fileIcon = "<span class=\"txt-icon\"></span></div>"; fileType = "Файл txt";}
                            if (id_FileType == 9) {fileIcon = "<span class=\"ppt-icon\"></span></div>"; fileType = "Файл ppt";}
                            if (id_FileType == 10) {fileIcon = "<span class=\"pptx-icon\"></span></div>"; fileType = "Файл pptx";}
                            if (id_FileType == 11) {fileIcon = "<span class=\"jpg-icon\"></span></div>"; fileType = "Файл jpg";}
                            if (id_FileType == 12) {fileIcon = "<span class=\"png-icon\"></span></div>"; fileType = "Файл png";}
                            if (id_FileType == 13) {fileIcon = "<span class=\"unknown-file-icon\"></span></div>"; fileType = "Неизвестный формат";}
                            if (user.middleName != null) {
                                user_name += ". " + user.middleName.charAt(0).toString() + ".";
                            }
                            var m = new Date(data.creationDate);
                            var file_creationDate = ("0" + m.getUTCDate()).slice(-2) + "." + ("0" + (m.getUTCMonth()+1)).slice(-2) + "." +
                                ("0" + m.getUTCFullYear()).slice(-2) + " " + ("0" + m.getHours()).slice(-2) + ":" + ("0" +
                                    m.getUTCMinutes()).slice(-2) + ":" + ("0" + m.getUTCSeconds()).slice(-2);

                            $(file_info).append("<div class=\"file-details\">" +
                                "<input type=\"hidden\" class=\"file-id-details\" value=\"" + file_id + "\">" +
                                "<div class=\"file-icon-div-details\">" + fileIcon  +
                                "<div class=\"file-name-details\">" + "<span>" + file_name + "</span></div>" +
                                "<div class=\"file-type-details\">" + "<span class=\"file-type-span-details\">"+fileType+"</span></div>" +
                                "<div class=\"file-date-details\">" + "<span>"+file_creationDate+"</span></div></div>");
                        });
                    } else if (parseInt(currentViewID.value) === 3) {
                        $.each(data, function (index, data) {
                            var file_id = data.id_File;
                            var file_name = data.name;
                            var user = data.userCreated;
                            var user_name = user.lastName+ " " + user.firstName.charAt(0).toString();
                            var id_FileType = data.id_FileType;
                            var fileIcon = "";
                            if (id_FileType == 1) fileIcon = "<span class=\"pdf-icon\"></span></div>";
                            if (id_FileType == 2) fileIcon = "<span class=\"doc-icon\"></span></div>";
                            if (id_FileType == 3) fileIcon = "<span class=\"docx-icon\"></span></div>";
                            if (id_FileType == 4) fileIcon = "<span class=\"xls-icon\"></span></div>";
                            if (id_FileType == 5) fileIcon = "<span class=\"xlsx-icon\"></span></div>";
                            if (id_FileType == 6) fileIcon = "<span class=\"rar-icon\"></span></div>";
                            if (id_FileType == 7) fileIcon = "<span class=\"zip-icon\"></span></div>";
                            if (id_FileType == 8) fileIcon = "<span class=\"txt-icon\"></span></div>";
                            if (id_FileType == 9) fileIcon = "<span class=\"ppt-icon\"></span></div>";
                            if (id_FileType == 10) fileIcon = "<span class=\"pptx-icon\"></span></div>";
                            if (id_FileType == 11) fileIcon = "<span class=\"jpg-icon\"></span></div>";
                            if (id_FileType == 12) fileIcon = "<span class=\"png-icon\"></span></div>";
                            if (id_FileType == 13) fileIcon = "<span class=\"unknown-file-icon\"></span></div>";
                            if (user.middleName != null) {
                                user_name += ". " + user.middleName.charAt(0).toString() + ".";
                            }

                            $(file_info).append("<div class=\"file-list\">" +
                                "<input type=\"hidden\" class=\"file-id-list\" value=\"" + file_id + "\">" +
                                "<div class=\"file-icon-div-list\">" + fileIcon  +
                                "<div class=\"file-name-list\">" +
                                "<span>" + file_name + "</span></div></div>");
                        });
                    } else {
                        $.each(data, function (index, data) {
                            var file_id = data.id_File;
                            var file_name = data.name;
                            var user = data.userCreated;
                            var user_name = user.lastName+ " " + user.firstName.charAt(0).toString();
                            var id_FileType = data.id_FileType;
                            var fileIcon = "";
                            if (id_FileType == 1) fileIcon = "<span class=\"pdf-icon\"></span></div>";
                            if (id_FileType == 2) fileIcon = "<span class=\"doc-icon\"></span></div>";
                            if (id_FileType == 3) fileIcon = "<span class=\"docx-icon\"></span></div>";
                            if (id_FileType == 4) fileIcon = "<span class=\"xls-icon\"></span></div>";
                            if (id_FileType == 5) fileIcon = "<span class=\"xlsx-icon\"></span></div>";
                            if (id_FileType == 6) fileIcon = "<span class=\"rar-icon\"></span></div>";
                            if (id_FileType == 7) fileIcon = "<span class=\"zip-icon\"></span></div>";
                            if (id_FileType == 8) fileIcon = "<span class=\"txt-icon\"></span></div>";
                            if (id_FileType == 9) fileIcon = "<span class=\"ppt-icon\"></span></div>";
                            if (id_FileType == 10) fileIcon = "<span class=\"pptx-icon\"></span></div>";
                            if (id_FileType == 11) fileIcon = "<span class=\"jpg-icon\"></span></div>";
                            if (id_FileType == 12) fileIcon = "<span class=\"png-icon\"></span></div>";
                            if (id_FileType == 13) fileIcon = "<span class=\"unknown-file-icon\"></span></div>";
                            if (user.middleName != null) {
                                user_name += ". " + user.middleName.charAt(0).toString() + ".";
                            }

                            $(file_info).append("<div class=\"file\">" +
                                "<input type=\"hidden\" class=\"file-id\" value=\"" + file_id + "\">" +
                                "<div class=\"file-icon-div\">" + fileIcon  +
                                "<div class=\"file-name\">" +
                                "<span>" + file_name + "</span></div></div>");
                        });
                    }
                }
            });



        }
    }
});

$(document).on('click', '.file-view-option', function (e) {

    e.stopPropagation();
    e.preventDefault();
    if ($(this).hasClass("active")) {
        $(this).removeClass('active');
    } else {
        $(".active").each(function () {
            $(this).removeClass("active");
        });
        $(this).toggleClass('active');
    }

    /*
    $(".files-view-btn").each(function () {
        $(this).removeClass(".active");
    });*/


    var fileViewID = this.value;
    var currentViewID = document.getElementById("current-view-id");
    if (currentViewID != null) {
        if (parseInt(currentViewID.value) !== parseInt(fileViewID)) {
            if (parseInt(currentViewID.value) === 2) {
                var files = document.getElementsByClassName("file-details");
                var file_info = document.getElementById("file-info-details");
                var fileInfoBox = document.getElementById("file-info-box");
            } else if (parseInt(currentViewID.value) === 3) {
                var files = document.getElementsByClassName("file-list");
                var file_info = document.getElementById("file-info-list");
                var fileInfoBox = document.getElementById("file-info-box-list");
            } else {
                var files = document.getElementsByClassName("file");
                var file_info = document.getElementById("file-info");
                var fileInfoBox = document.getElementById("file-info-box");
            }
            currentViewID.value = parseInt(fileViewID);
            if(parseInt(fileViewID) === 3){
                fileInfoBox.className = "file-info-box-list";
                fileInfoBox.id = "file-info-box-list";
            } else {
                fileInfoBox.className = "file-info-box";
                fileInfoBox.id = "file-info-box";
            }

            if (files.length > 1) {
                $.ajax({
                    type: "POST",
                    url: "getfilesview",
                    data: "filesViewID=" + parseInt(fileViewID),                  //+ "&fileIDs=" + JSON.stringify(fileIDs),
                    success: function (data) {
                        if (data.message != null) {
                            if (data.message == "getFilesFromThePage") {
                                var searchResultFilesIDs = [];
                                for (var i = 0; i < files.length; i++) {
                                    searchResultFilesIDs.push(files[i].children[0].value);
                                }

                                if (fileViewID === 2) {
                                    $.ajax({
                                        type: "POST",
                                        url: "getsearchedfiles",
                                        data: "searchResultFilesIDs=" + JSON.stringify(searchResultFilesIDs),
                                        success: function (data) {
                                            file_info.innerHTML = "";
                                            file_info.className = "file-info-details";
                                            file_info.id = "file-info-details";

                                            $(file_info).append("<div class=\"file-details-head\">" +
                                                "<input type=\"hidden\" class=\"file-id-details\">" +
                                                "<div class=\"file-icon-div-details\">" +
                                                "<span class=\"icons-details\"></span></div>" +
                                                "<div class=\"file-name-details\"><span>Название файла</span></div>" +
                                                "<div class=\"file-type-details\"><span class=\"file-type-span-details\">Тип файла</span></div>" +
                                                "<div class=\"file-date-details\"><span>Дата создания</span></div></div>");


                                            $.each(data, function (index, data) {
                                                var file_id = data.id_File;
                                                var file_name = data.name;
                                                var user = data.userCreated;
                                                var user_name = user.lastName + " " + user.firstName.charAt(0).toString();
                                                var id_FileType = data.id_FileType;
                                                var fileIcon = ""; var fileType = "";
                                                var m = new Date(data.creationDate);
                                                var file_creationDate = ("0" + m.getUTCDate()).slice(-2) + "." + ("0" + (m.getUTCMonth()+1)).slice(-2) + "." +
                                                    ("0" + m.getUTCFullYear()).slice(-2) + " " + ("0" + m.getHours()).slice(-2) + ":" + ("0" +
                                                        m.getUTCMinutes()).slice(-2) + ":" + ("0" + m.getUTCSeconds()).slice(-2);
                                                if (id_FileType == 1){ fileIcon = "<span class=\"pdf-icon\"></span></div>"; fileType = "Файл pdf";}
                                                if (id_FileType == 2) {fileIcon = "<span class=\"doc-icon\"></span></div>"; fileType = "Файл doc";}
                                                if (id_FileType == 3) {fileIcon = "<span class=\"docx-icon\"></span></div>"; fileType = "Файл docx";}
                                                if (id_FileType == 4) {fileIcon = "<span class=\"xls-icon\"></span></div>"; fileType = "Файл xls";}
                                                if (id_FileType == 5) {fileIcon = "<span class=\"xlsx-icon\"></span></div>"; fileType = "Файл xlsx";}
                                                if (id_FileType == 6) {fileIcon = "<span class=\"rar-icon\"></span></div>"; fileType = "Файл rar";}
                                                if (id_FileType == 7) {fileIcon = "<span class=\"zip-icon\"></span></div>"; fileType = "Файл zip";}
                                                if (id_FileType == 8) {fileIcon = "<span class=\"txt-icon\"></span></div>"; fileType = "Файл txt";}
                                                if (id_FileType == 9) {fileIcon = "<span class=\"ppt-icon\"></span></div>"; fileType = "Файл ppt";}
                                                if (id_FileType == 10) {fileIcon = "<span class=\"pptx-icon\"></span></div>"; fileType = "Файл pptx";}
                                                if (id_FileType == 11) {fileIcon = "<span class=\"jpg-icon\"></span></div>"; fileType = "Файл jpg";}
                                                if (id_FileType == 12) {fileIcon = "<span class=\"png-icon\"></span></div>"; fileType = "Файл png";}
                                                if (id_FileType == 13) {fileIcon = "<span class=\"unknown-file-icon\"></span></div>"; fileType = "Неизвестный формат";}
                                                if (user.middleName != null) {
                                                    user_name += ". " + user.middleName.charAt(0).toString() + ".";
                                                }
                                                $(file_info).append("<div class=\"file-details\">" +
                                                    "<input type=\"hidden\" class=\"file-id-details\" value=\"" + file_id + "\">" +
                                                    "<div class=\"file-icon-div-details\">" + fileIcon +
                                                    "<div class=\"file-name-details\"><span>" + file_name + "</span></div>" +
                                                    "<div class=\"file-type-details\"><span class=\"file-type-span-details\">"+fileType+"</span></div>" +
                                                    "<div class=\"file-date-details\">" + "<span>"+file_creationDate+"</span></div></div>");
                                            });
                                        }
                                    });
                                } else if (fileViewID === 3) {
                                    $.ajax({
                                        type: "POST",
                                        url: "getsearchedfiles",
                                        data: "searchResultFilesIDs=" + JSON.stringify(searchResultFilesIDs),
                                        success: function (data) {
                                            file_info.innerHTML = "";
                                            file_info.className = "file-info-list";
                                            file_info.id = "file-info-list";
                                            $.each(data, function (index, data) {
                                                var file_id = data.id_File;
                                                var file_name = data.name;
                                                var user = data.userCreated;
                                                var user_name = user.lastName + " " + user.firstName.charAt(0).toString();
                                                var id_FileType = data.id_FileType;
                                                var fileIcon = "";
                                                if (id_FileType == 1) fileIcon = "<span class=\"pdf-icon\"></span></div>";
                                                if (id_FileType == 2) fileIcon = "<span class=\"doc-icon\"></span></div>";
                                                if (id_FileType == 3) fileIcon = "<span class=\"docx-icon\"></span></div>";
                                                if (id_FileType == 4) fileIcon = "<span class=\"xls-icon\"></span></div>";
                                                if (id_FileType == 5) fileIcon = "<span class=\"xlsx-icon\"></span></div>";
                                                if (id_FileType == 6) fileIcon = "<span class=\"rar-icon\"></span></div>";
                                                if (id_FileType == 7) fileIcon = "<span class=\"zip-icon\"></span></div>";
                                                if (id_FileType == 8) fileIcon = "<span class=\"txt-icon\"></span></div>";
                                                if (id_FileType == 9) fileIcon = "<span class=\"ppt-icon\"></span></div>";
                                                if (id_FileType == 10) fileIcon = "<span class=\"pptx-icon\"></span></div>";
                                                if (id_FileType == 11) fileIcon = "<span class=\"jpg-icon\"></span></div>";
                                                if (id_FileType == 12) fileIcon = "<span class=\"png-icon\"></span></div>";
                                                if (id_FileType == 13) fileIcon = "<span class=\"unknown-file-icon\"></span></div>";
                                                if (user.middleName != null) {
                                                    user_name += ". " + user.middleName.charAt(0).toString() + ".";
                                                }
                                                $(file_info).append("<div class=\"file-list\">" +
                                                    "<input type=\"hidden\" class=\"file-id-list\" value=\"" + file_id + "\">" +
                                                    "<div class=\"file-icon-div-list\">" + fileIcon +
                                                    "<div class=\"file-name-list\">" +
                                                    "<span>" + file_name + "</span></div></div>");
                                            });
                                        }
                                    });
                                } else {
                                    $.ajax({
                                        type: "POST",
                                        url: "getsearchedfiles",
                                        data: "searchResultFilesIDs=" + JSON.stringify(searchResultFilesIDs),
                                        success: function (data) {
                                            file_info.innerHTML = "";
                                            file_info.className = "file-info";
                                            file_info.id = "file-info";
                                            $.each(data, function (index, data) {
                                                var file_id = data.id_File;
                                                var file_name = data.name;
                                                var user = data.userCreated;
                                                var user_name = user.lastName + " " + user.firstName.charAt(0).toString();
                                                var id_FileType = data.id_FileType;
                                                var fileIcon = "";
                                                if (id_FileType == 1) fileIcon = "<span class=\"pdf-icon\"></span></div>";
                                                if (id_FileType == 2) fileIcon = "<span class=\"doc-icon\"></span></div>";
                                                if (id_FileType == 3) fileIcon = "<span class=\"docx-icon\"></span></div>";
                                                if (id_FileType == 4) fileIcon = "<span class=\"xls-icon\"></span></div>";
                                                if (id_FileType == 5) fileIcon = "<span class=\"xlsx-icon\"></span></div>";
                                                if (id_FileType == 6) fileIcon = "<span class=\"rar-icon\"></span></div>";
                                                if (id_FileType == 7) fileIcon = "<span class=\"zip-icon\"></span></div>";
                                                if (id_FileType == 8) fileIcon = "<span class=\"txt-icon\"></span></div>";
                                                if (id_FileType == 9) fileIcon = "<span class=\"ppt-icon\"></span></div>";
                                                if (id_FileType == 10) fileIcon = "<span class=\"pptx-icon\"></span></div>";
                                                if (id_FileType == 11) fileIcon = "<span class=\"jpg-icon\"></span></div>";
                                                if (id_FileType == 12) fileIcon = "<span class=\"png-icon\"></span></div>";
                                                if (id_FileType == 13) fileIcon = "<span class=\"unknown-file-icon\"></span></div>";
                                                if (user.middleName != null) {
                                                    user_name += ". " + user.middleName.charAt(0).toString() + ".";
                                                }
                                                $(file_info).append("<div class=\"file\">" +
                                                    "<input type=\"hidden\" class=\"file-id\" value=\"" + file_id + "\">" +
                                                    "<div class=\"file-icon-div\">" + fileIcon +
                                                    "<div class=\"file-name\">" +
                                                    "<span>" + file_name + "</span></div></div>");
                                            });
                                        }
                                    });
                                }
                            }
                        } else {
                            var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
                            var documentBox = document.getElementById("content-box");
                            $.ajax({
                                type: "POST",
                                url: "openfolder",
                                data: "id=" + folderId,             //+ "&fileIDs=" + JSON.stringify(fileIDs),
                                success: function (data) {
                                    documentBox.innerHTML = data;
                                }
                            });
                        }
                    }
                });
            }
        }
    }
});

function countLines() {
    var target = document.getElementById("preload-box-header-name-department");
    if(target !== null){
        var style = window.getComputedStyle(target, null);
        var height = parseInt(style.getPropertyValue("height"));
        var font_size = parseInt(style.getPropertyValue("font-size"));
        var line_height = parseInt(style.getPropertyValue("line-height"));
        var box_sizing = style.getPropertyValue("box-sizing");

        if(isNaN(line_height)) line_height = font_size * 1.2;
        if(box_sizing=='border-box')
        {
            var padding_top = parseInt(style.getPropertyValue("padding-top"));
            var padding_bottom = parseInt(style.getPropertyValue("padding-bottom"));
            var border_top = parseInt(style.getPropertyValue("border-top-width"));
            var border_bottom = parseInt(style.getPropertyValue("border-bottom-width"));
            height = height - padding_top - padding_bottom - border_top - border_bottom
        }

        var lines = Math.floor(height / line_height);

        if(lines < 2){
            target.className = "preload-box-header-name";
        }
    }
}







/*

$(document).on('mouseover', '#files-info-box', function(){
    e.preventDefault();
    e.stopPropagation();
    var folderId = document.getElementsByClassName("document-opened")[0].children[0].value;
    var holder = document.getElementById('files-info-box');

    $.ajax({
        type: "POST",
        url: "upload_file_overlay",
        data: "folder_id="+folderId,
        success: function(data) {
            $("body").append(data);
            $('#file-upload-overlay').modal('show');
        }
    });
    holder.ondragenter = function (e) { e.preventDefault(); return false; };
    holder.ondragleave = function (e) { e.preventDefault(); this.className = 'newdoc-attachments'; return false; };
    holder.ondragover = function (e) { e.preventDefault(); this.className = 'newdoc-attachments drag-n-drop'; return false; };
    holder.ondrop = function (e) {
        e.preventDefault();
        this.className = 'file-upload-overlay';
        addFiles(e.dataTransfer);
        return false;
    };
});
*/

