package kz.itbc.docshare.constants;

public final class AppConstant {
    public static final String ENCODING = "UTF-8";
    public static final String EMPTY_STRING = "";
    public static final String DEFAULT_LOCALE = "ru";
    public static final String JSON_CONTENT_TYPE = "application/json;charset=utf-8";
    public static final String UTF_8_CHARSET = "UTF-8";

    public static final String SUPPRESS_WARNINGS_UNCHECKED_VALUE = "unchecked";
    public static final String ADD_TO_OPEN_MENU_LIST_VALUE = "add";
    public static final String DELETE_FROM_OPEN_MENU_LIST_VALUE = "delete";
    public static final String EMPTY_SEARCH_BOX_VALUE = "keywordisnullreturnnothing";

    //Attribute constants
    public static final String MESSAGE_ATTRIBUTE = "message";
    public static final String USER_ATTRIBUTE = "user";
    public static final String OPENED_MENU_ATTRIBUTE = "opened_menu";
    public static final String FOLDER_ID_ATTRIBUTE = "folder-id";
    public static final String SESSION_LOCALE_ATTRIBUTE = "sessionLocale";
    public static final String RU_LOCALE_ATTRIBUTE = "ru";
    public static final String KZ_LOCALE_ATTRIBUTE = "kz";
    public static final String ERROR_ATTRIBUTE = "error";
    public static final String BLOCKED_DATE_ATTRIBUTE = "blockedDate";
    public static final String FOLDERS_ATTRIBUTE = "folders";
    public static final String FILES_ATTRIBUTE = "files";
    public static final String FOLDER_ATTRIBUTE = "folder";
    public static final String DEPARTMENTS_ATTRIBUTE = "departments";
    public static final String CURRENT_PAGE_ATTRIBUTE = "current_page";
    public static final String OPENED_FOLDER_ATTRIBUTE = "folder_id";
    public static final String FILES_VIEW_ID_ATTRIBUTE = "filesView";

    //Error values
    public static final int INCORRECT_LOGIN_ERROR = 1000;
    public static final int INCORRECT_PASSWORD_ERROR = 1001;
    public static final int USER_BLOCKED_ERROR = 1002;

    //Parameters
    public static final String LOGIN_PARAMETER = "login";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String USER_ID_PARAMETER = "user_id";
    public static final String ID_PARAMETER = "id";
    public static final String IDS_PARAMETER = "ids";
    public static final String ACTION_PARAMETER = "action";
    public static final String FOLDER_NAME_PARAMETER = "folder-name";
    public static final String FOLDER_ID_PARAMETER = "folder_id";
    public static final String FILETYPE_ID_PARAMETER = "filetype-id";
    public static final String FILE_ID_PARAMETER = "file-id";
    public static final String DEPARTMENT_ID_PARAMETER = "department-id";
    public static final String FLAG_HIDDEN_PARAMETER = "flag-hidden";
    public static final String FLAG_DELETED_PARAMETER = "flag-deleted";
    public static final String FILE_NAME_PARAMETER = "file-name";
    public static final String USERS_PARAMETER = "users";
    public static final String FOLDER_IDS_PARAMETER = "folderIDs";
    public static final String FILE_IDS_PARAMETER = "fileIDs";
    public static final String FILES_PARAMETER = "files";
    public static final String OPENED_FILES_PARAMETER = "opened_files";
    public static final String SOURCE_PATH_CONTEXT_PARAMETER = "sourcePathContext";
    public static final String DESTINATION_PATH_CONTEXT_PARAMETER = "destinationPathContext";
    public static final String IS_TRASH_PARAMETER = "isTrash";
    public static final String TO_HIDE_PARAMETER = "toHide";
    public static final String FILES_VIEW_ID_PARAMETER = "filesViewID";
    public static final String SEARCH_RESULT_FILES_PARAMETER = "searchResultFilesIDs";



    //Page URIs
    public static final String LOGIN_PAGE_URI = "/login";
    public static final String MAIN_PAGE_URI = "/main";
    public static final String DEPARTMENT_PAGE_URI = "/department";
    public static final String SWITCH_LANGUAGE_URI = "/switchlang";
    public static final String MYSTORAGE_PAGE_URI = "/my";
    public static final String SHARED_PAGE_URI = "/shared";
    public static final String TRASH_PAGE_URI = "/trash";
    public static final String OPENFOLDER_PAGE_URI = "/openfolder";
    public static final String GET_FILES_VIEW_PAGE_URI = "/getfilesview";
    public static final String GET_SEARCHED_FILES_VIEW_PAGE_URI = "/getsearchedfiles";
    public static final String OPEN_LEFT_MENU_URI = "/left-menu";
    public static final String LOAD_FILES_MENU_URI = "/loadfilesmenu";
    public static final String LOAD_MANY_FILES_MENU_URI = "/loadmanyfilesmenu";
    public static final String LOAD_FILES_ATTRIBUTES_INFO_URI = "/loadfilesattributesinfo";
    public static final String LOAD_FOLDERS_MENU_URI = "/loadfoldersmenu";
    public static final String SEARCH_RESULTS_PAGE_URI = "/searchresults";
    public static final String FILES_SEARCH_RESULTS_PAGE_URI = "/filessearchresults";
    public static final String SOURCE_DESTINATION_PATH_CONTEXT_URI = "/sourceDestinationPathContext";
    public static final String RENAME_FOLDER_URI = "/renamefolder";
    public static final String RENAME_FILE_URI = "/renamefile";
    public static final String DOWNLOAD_FILE_PAGE_URI = "/download-file";
    public static final String DOWNLOAD_FILES_PAGE_URI = "/download-files";
    public static final String SHARE_FILE_SERVICE_PAGE_URI = "/share-file";
    public static final String SHARE_FILES_SERVICE_PAGE_URI = "/share-files";
    public static final String DELETE_FILE_SERVICE_PAGE_URI = "/delete-file";
    public static final String DELETE_FILES_SERVICE_PAGE_URI = "/delete-files";
    public static final String ERASE_FILE_SERVICE_PAGE_URI = "/erase-file";
    public static final String ERASE_FILES_SERVICE_PAGE_URI = "/erase-files";
    public static final String HIDE_FILE_SERVICE_PAGE_URI = "/hide-file";
    public static final String UNHIDE_FILE_SERVICE_PAGE_URI = "/unhide-file";
    public static final String HIDE_FILES_SERVICE_PAGE_URI = "/hide-files";
    public static final String UNHIDE_FILES_SERVICE_PAGE_URI = "/unhide-files";
    public static final String RENAME_FILE_SERVICE_PAGE_URI = "/rename-file";
    public static final String RESTORE_FILE_MENU_URI = "/restore-file";
    public static final String RESTORE_FILES_MENU_URI = "/restore-files";
    public static final String UPLOAD_FILES_MENU_URI = "/upload-files";
    public static final String SHARE_FOLDER_SERVICE_PAGE_URI = "/share-folder";
    public static final String DELETE_FOLDER_SERVICE_PAGE_URI = "/delete-folder";
    public static final String ERASE_FOLDER_SERVICE_PAGE_URI = "/erase-folder";

    public static final String HIDE_FOLDER_SERVICE_PAGE_URI = "/hide-folder";
    public static final String UNHIDE_FOLDER_SERVICE_PAGE_URI = "/unhide-folder";
    public static final String RESTORE_FOLDER_SERVICE_PAGE_URI = "/restore-folder";
    public static final String CREATE_FOLDER_SERVICE_PAGE_URI = "/create-folder";
    public static final String CREATE_DEPARTMENT_FOLDER_SERVICE_PAGE_URI = "/create-department-folder";
    public static final String RENAME_FOLDER_SERVICE_PAGE_URI = "/rename-folder";
    public static final String GET_FILE_SHARE_OVERLAY_PAGE_URI = "/get_file_share_overlay";
    public static final String GET_FILES_SHARE_OVERLAY_PAGE_URI = "/get_files_share_overlay";
    public static final String UPLOAD_FILE_OVERLAY_PAGE_URI = "/upload_file_overlay";
    public static final String GET_FOLDER_SHARE_OVERLAY_PAGE_URI = "/get_folder_share_overlay";
    public static final String CREATE_FOLDER_OVERLAY_PAGE_URI = "/create_folder_overlay";
    public static final String CREATE_DEPARTMENT_FOLDER_OVERLAY_PAGE_URI = "/create_department_folder_overlay";
    public static final String GET_USERS_AJAX_URI = "/get_users";
    public static final String SORT_FOLDERS_AJAX_URI = "/sort_folders";
    public static final String SORT_FILES_AJAX_URI = "/sort_files";
    public static final String CHECK_IF_SEARCHED_FOLDERS_AJAX_URI = "/check-if-searched-folders";
    public static final String CHECK_IF_SEARCHED_FILES_AJAX_URI = "/check-if-searched-files";


    //Page IDs
    public static final int MYSTORAGE_PAGE_ID = 2;
    public static final int SHARED_PAGE_ID = 3;
    public static final int TRASH_PAGE_ID = 4;
    public static final int SEARCH_PAGE_ID = 5;

    //Files View IDs
    public static final int TILES_VIEW_ID = 1;
    public static final int DETAILS_VIEW_ID = 2;
    public static final int LIST_VIEW_ID = 3;

    //JSP PAGE Constants
    public static final String LOGIN_PAGE_JSP = "/jsp/login.jsp";
    public static final String MAIN_PAGE_JSP = "/WEB-INF/jsp/main.jsp";
    public static final String LOAD_FILES_MENU_JSP = "/WEB-INF/jsp-parts/files-content-buttons.jsp";
    public static final String LOAD_MANY_FILES_MENU_JSP = "/WEB-INF/jsp-parts/manyfiles-content-buttons.jsp";
    public static final String LOAD_FILES_ATTRIBUTES_INFO_JSP = "/WEB-INF/jsp-parts/files-attributes-info.jsp";
    public static final String LOAD_FOLDERS_MENU_JSP = "/WEB-INF/jsp-parts/folders-content-buttons.jsp";
    public static final String SEARCH_RESULTS_JSP = "/WEB-INF/jsp-parts/search-result-preload-documents.jsp";
    public static final String FILES_SEARCH_RESULTS_JSP = "/WEB-INF/jsp-parts/search-result-files.jsp";
    public static final String FILES_SEARCH_RESULTS_DETAILS_JSP = "/WEB-INF/jsp-parts/search-result-files-details.jsp";
    public static final String FILES_SEARCH_RESULTS_LIST_JSP = "/WEB-INF/jsp-parts/search-result-files-list.jsp";

    public static final String FILES_JSP = "/WEB-INF/jsp/files.jsp";
    public static final String FILES_DETAILS_JSP = "/WEB-INF/jsp/files-details.jsp";
    public static final String FILES_LIST_JSP = "/WEB-INF/jsp/files-list.jsp";



}
