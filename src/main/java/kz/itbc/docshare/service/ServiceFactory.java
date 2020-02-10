package kz.itbc.docshare.service;

import java.util.HashMap;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class ServiceFactory {
    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    private ServiceFactory() {
        init();
    }

    private void init() {
        SERVICE_MAP.put(LOGIN_PAGE_URI, new LoginService());
        SERVICE_MAP.put(MAIN_PAGE_URI, new MainService());
        SERVICE_MAP.put(SWITCH_LANGUAGE_URI, new SwitchLanguageService());
        SERVICE_MAP.put(DEPARTMENT_PAGE_URI, new MainService());
        SERVICE_MAP.put(MYSTORAGE_PAGE_URI, new MainService());
        SERVICE_MAP.put(SHARED_PAGE_URI, new MainService());
        SERVICE_MAP.put(TRASH_PAGE_URI, new MainService());
        SERVICE_MAP.put(OPENFOLDER_PAGE_URI, new GetFolderFilesAJAX());
        SERVICE_MAP.put(GET_FILES_VIEW_PAGE_URI, new GetFilesViewAJAX());
        SERVICE_MAP.put(OPEN_LEFT_MENU_URI, new LeftMenuServiceAJAX());
        SERVICE_MAP.put(LOAD_FILES_MENU_URI, new LoadFilesMenuService());
        SERVICE_MAP.put(LOAD_MANY_FILES_MENU_URI, new LoadManyFilesMenuService());
        SERVICE_MAP.put(LOAD_FILES_ATTRIBUTES_INFO_URI, new LoadFilesAttributesInfoAJAX());
        SERVICE_MAP.put(SEARCH_RESULTS_PAGE_URI, new SearchResultsService());
        SERVICE_MAP.put(FILES_SEARCH_RESULTS_PAGE_URI, new SearchResultsForFilesService());
        SERVICE_MAP.put(GET_SEARCHED_FILES_VIEW_PAGE_URI, new GetSearchedFilesServiceAJAX());
        SERVICE_MAP.put(SORT_FOLDERS_AJAX_URI, new SortFoldersAJAX());
        SERVICE_MAP.put(SORT_FILES_AJAX_URI, new SortFilesAJAX());
        SERVICE_MAP.put(DOWNLOAD_FILE_PAGE_URI, new DownloadFileService());
        SERVICE_MAP.put(DOWNLOAD_FILES_PAGE_URI, new DownloadFilesService());
        SERVICE_MAP.put(SHARE_FILE_SERVICE_PAGE_URI, new ShareFileService());
        SERVICE_MAP.put(SHARE_FILES_SERVICE_PAGE_URI, new ShareFilesService());
        SERVICE_MAP.put(DELETE_FILE_SERVICE_PAGE_URI, new DeleteFileService());
        SERVICE_MAP.put(DELETE_FILES_SERVICE_PAGE_URI, new DeleteFilesService());
        SERVICE_MAP.put(ERASE_FILE_SERVICE_PAGE_URI, new EraseFileService());
        SERVICE_MAP.put(ERASE_FILES_SERVICE_PAGE_URI, new EraseFilesService());
        SERVICE_MAP.put(HIDE_FILE_SERVICE_PAGE_URI, new HideUnhideFileService());
        SERVICE_MAP.put(UNHIDE_FILE_SERVICE_PAGE_URI, new HideUnhideFileService());
        SERVICE_MAP.put(HIDE_FILES_SERVICE_PAGE_URI, new HideUnhideFilesService());
        SERVICE_MAP.put(UNHIDE_FILES_SERVICE_PAGE_URI, new HideUnhideFilesService());
        SERVICE_MAP.put(RESTORE_FILE_MENU_URI, new RestoreFileService());
        SERVICE_MAP.put(RESTORE_FILES_MENU_URI, new RestoreFilesService());
        SERVICE_MAP.put(UPLOAD_FILES_MENU_URI, new UploadFilesService());
        SERVICE_MAP.put(RENAME_FILE_SERVICE_PAGE_URI, new RenameFileService());
        SERVICE_MAP.put(CREATE_FOLDER_SERVICE_PAGE_URI, new CreateFolderService());
        SERVICE_MAP.put(CREATE_DEPARTMENT_FOLDER_SERVICE_PAGE_URI, new CreateDepartmentFolderService());
        SERVICE_MAP.put(SHARE_FOLDER_SERVICE_PAGE_URI, new ShareFolderService());
        SERVICE_MAP.put(DELETE_FOLDER_SERVICE_PAGE_URI, new DeleteFolderService());
        SERVICE_MAP.put(ERASE_FOLDER_SERVICE_PAGE_URI, new EraseFolderService());
        SERVICE_MAP.put(HIDE_FOLDER_SERVICE_PAGE_URI, new HideUnhideFolderService());
        SERVICE_MAP.put(UNHIDE_FOLDER_SERVICE_PAGE_URI, new HideUnhideFolderService());
        SERVICE_MAP.put(RESTORE_FOLDER_SERVICE_PAGE_URI, new RestoreFolderService());
        SERVICE_MAP.put(RENAME_FOLDER_SERVICE_PAGE_URI, new RenameFolderService());
        SERVICE_MAP.put(GET_FILE_SHARE_OVERLAY_PAGE_URI, new GetFileShareOverlayAJAX());
        SERVICE_MAP.put(GET_FILES_SHARE_OVERLAY_PAGE_URI, new GetFilesShareOverlayAJAX());
        SERVICE_MAP.put(GET_FOLDER_SHARE_OVERLAY_PAGE_URI, new GetFolderShareOverlayAJAX());
        SERVICE_MAP.put(CREATE_FOLDER_OVERLAY_PAGE_URI, new CreateFolderOverlayAJAX());
        SERVICE_MAP.put(UPLOAD_FILE_OVERLAY_PAGE_URI, new UploadFileOverlayAJAX());
        SERVICE_MAP.put(CREATE_DEPARTMENT_FOLDER_OVERLAY_PAGE_URI, new CreateDepartmentFolderOverlayAJAX());
        SERVICE_MAP.put(GET_USERS_AJAX_URI, new GetUsersAJAX());
        SERVICE_MAP.put(CHECK_IF_SEARCHED_FOLDERS_AJAX_URI, new CheckIfSearchedFoldersAJAX());
        SERVICE_MAP.put(CHECK_IF_SEARCHED_FILES_AJAX_URI, new CheckIfSearchedFilesAJAX());


    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public Service getService(String request) {
        return SERVICE_MAP.get(request);
    }
}
