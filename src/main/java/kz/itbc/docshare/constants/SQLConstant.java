package kz.itbc.docshare.constants;

public final class SQLConstant {
    public static final String GET_USER_BY_LOGIN_SQL_QUERY = "SELECT * FROM [Users] WHERE Login = ? AND FlagDeleted = 0";
    public static final String GET_USER_BY_ID_SQL_QUERY = "SELECT * FROM [Users] WHERE ID_User = ? AND FlagDeleted = 0";
    public static final String GET_USERS_SQL_QUERY = "SELECT * FROM [Users]";
    public static final String GET_USERS_BY_KEYWORD_SQL_QUERY = "SELECT * FROM [Users] WHERE [FirstName] LIKE ? or [LastName] LIKE ? or [MiddleName] LIKE ?";

    public static final String GET_FILE_BY_ID_SQL_QUERY = "SELECT * FROM [Files] WHERE ID_File = ?";
    public static final String GET_FILES_BY_FOLDER_ID_SQL_QUERY = "SELECT * FROM [Files] WHERE FlagDeleted = 0 AND FlagErased = 0 AND ID_Folder = ?";
    public static final String GET_DELETED_FILES_BY_FOLDER_ID_SQL_QUERY = "SELECT * FROM [Files] WHERE ID_Folder = ? and FlagDeleted = 1 and FlagErased = 0";
    public static final String UPDATE_FILE_SQL_QUERY = "UPDATE Files SET Name = ?, FlagHidden = ?, FlagDeleted = ?, ID_UserDeleted = ? WHERE ID_File = ?";
    public static final String ERASE_FILE_SQL_QUERY = "UPDATE Files SET FlagErased = ?, ID_UserErased = ? WHERE ID_File = ?";
    public static final String UPDATE_FILES_FLAGDELETED_BY_FOLDERID_SQL_QUERY = "UPDATE Files SET FlagDeleted = ?, ID_UserDeleted = ? WHERE ID_Folder = ?";
    public static final String UPDATE_FILES_FLAGERASED_BY_FOLDERID_SQL_QUERY = "UPDATE Files SET FlagErased = ?, ID_UserErased = ? WHERE ID_Folder = ?";
    public static final String CREATE_FILE_SQL_QUERY = "INSERT INTO Files (Name, Data, FlagHidden, FlagDeleted, ID_FileType, ID_Folder, ID_UserCreated, CreationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String CHECK_RECORD_IN_FILES_SQL_QUERY = "  SELECT * FROM [Files] WHERE ID_UserCreated = ? AND ID_File = ?";

    public static final String GET_ALL_FILES_BY_KEYWORD_SQL_QUERY = "SELECT * FROM [Files] WHERE [Name] LIKE ? and FlagDeleted = 0 and FlagErased = 0";
    public static final String GET_ALL_FOLDERS_BY_KEYWORD_SQL_QUERY = "SELECT * FROM [Folders] WHERE [Name] LIKE ? and FlagDeleted = 0 and FlagErased = 0";

    public static final String GET_FOLDER_BY_ID_SQL_QUERY = "SELECT * FROM [Folders] WHERE ID_Folder = ?";
    public static final String GET_FOLDER_BY_NAME_AND_USER_SQL_QUERY = "SELECT * FROM [Folders] WHERE Name = ? and ID_UserCreated = ?";


    public static final String GET_FOLDERS_DELETED_BY_USER_SQL_QUERY = "SELECT * FROM [Folders] WHERE FlagDeleted = 1 and FlagErased = 0";
    public static final String GET_FOLDERS_FROM_DELETED_FILES_BY_USER_SQL_QUERY = "SELECT DISTINCT [ID_Folder] FROM [Files] WHERE ID_File in " +
                                                                                     "(SELECT DISTINCT ID_File FROM [Files] WHERE FlagDeleted = 1 and FlagErased = 0 and (ID_UserCreated = ? or ID_UserDeleted = ?)) ";
    public static final String CREATE_FOLDER_SQL_QUERY = "INSERT INTO [Folders] (NAME, FlagHidden, ID_UserCreated, CreationDate) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_FOLDER_SQL_QUERY = "UPDATE Folders SET Name = ?, FlagHidden = ?, FlagDeleted = ?, ID_UserDeleted = ? WHERE ID_Folder = ?";
    public static final String ERASE_FOLDER_SQL_QUERY = "UPDATE Folders SET FlagErased = ?, ID_UserErased = ? WHERE ID_Folder = ?";
    public static final String UPDATE_FOLDER_FLAGDELETED_SQL_QUERY = "UPDATE Folders SET FlagDeleted = ?, ID_UserDeleted = ? WHERE ID_Folder = ?";
    public static final String CREATE_USERTOFOLDER_SQL_QUERY = "INSERT INTO [UserToFolder] ([ID_User], [ID_Folder]) VALUES (?, ?)"; //  CHECK if there is a record when inserting into UserToFolder
    public static final String CREATE_DEPARTMENTTOFOLDER_SQL_QUERY = "INSERT INTO [DepartmentToFolder] ([ID_Department], [ID_Folder]) VALUES (?, ?)"; //CHECK if there is a record when inserting into DepartmentToFolder
    public static final String GET_DEPARTMENTTOFOLDERS_BY_DEPARTMENT_ID_SQL_QUERY = "SELECT * FROM [DepartmentToFolder] WHERE ID_Department = ?";
    public static final String GET_DEPARTMENT_FOLDER_BY_NAME_SQL_QUERY = "SELECT DISTINCT [ID_Folder] FROM [DepartmentToFolder] WHERE ID_Department = ? and ID_Folder in " +
                                                                            "(SELECT DISTINCT ID_Folder FROM [Folders] WHERE Name = ?)";
    public static final String GET_USERTOFOLDERS_BY_USER_ID_SQL_QUERY = "SELECT DISTINCT * FROM [UserToFolder] WHERE ID_User = ?";
    public static final String CHECK_RECORD_IN_USERTOFOLDERS_SQL_QUERY = "  SELECT * FROM [UserToFolder] WHERE ID_User = ? AND ID_Folder = ?";
    public static final String GET_USERFOLDERS_BY_USER_ID_SQL_QUERY = "SELECT * FROM [FoldersAccess] WHERE ID_User = ?";
    public static final String GET_USERFILES_BY_USER_ID_SQL_QUERY = " SELECT DISTINCT [ID_Folder], ID_File FROM [Files] WHERE FlagErased = 0 and ID_File in " +
                                                                    "(SELECT DISTINCT ID_File FROM [FilesAccess] WHERE ID_User = ?)";

    public static final String GET_DEPARTMENT_BY_ID_SQL_QUERY = "SELECT * FROM [UR_Departments] WHERE ID_Department = ?";
    public static final String GET_DEPARTMENTS_SQL_QUERY = "SELECT * FROM [UR_Departments]";

    public static final String GET_ROLE_BY_ID_SQL_QUERY = "SELECT * FROM [SR_Roles] WHERE ID_Role = ? AND FlagDeleted = 0";

    public static final String SHARE_FILE_WITH_USERS_SQL_QUERY = "IF NOT EXISTS (SELECT * FROM [FilesAccess] WHERE ID_User = ? AND ID_File = ? ) " +
                                                "BEGIN INSERT INTO [FilesAccess] (ID_User, ID_File) VALUES (?, ?) END";


    public static final String SHARE_FOLDER_WITH_USERS_SQL_QUERY = "  IF NOT EXISTS (SELECT * FROM [FoldersAccess] WHERE ID_User = ? AND ID_Folder = ? ) " +
            "BEGIN INSERT INTO [FoldersAccess] (ID_User, ID_Folder) VALUES (?, ?) END";
    public static final String CHECK_RECORD_IN_FOLDERS_ACCESS_SQL_QUERY = "  SELECT * FROM [FoldersAccess] WHERE ID_User = ? AND ID_Folder = ?";
    public static final String CHECK_RECORD_IN_FILES_ACCESS_SQL_QUERY = "  SELECT * FROM [FilesAccess] WHERE ID_User = ? AND [ID_File] = ?";



    public static final String ID_USER_COLUMN = "ID_User";
    public static final String LOGIN_COLUMN = "Login";
    public static final String PASSWORD_COLUMN = "Password";
    public static final String EMAIL_COLUMN = "Email";
    public static final String PHONE_COLUMN = "Phone";
    public static final String FIRST_NAME_COLUMN = "FirstName";
    public static final String LAST_NAME_COLUMN = "LastName";
    public static final String MIDDLE_NAME_COLUMN = "MiddleName";
    public static final String FLAG_DELETED_COLUMN = "FlagDeleted";
    public static final String FLAG_ERASED_COLUMN = "FlagErased";
    public static final String FLAG_ACCESS_COLUMN = "FlagAccess";
    public static final String ACCESS_IP_COLUMN = "AccessIP";
    public static final String SESSION_ID_COLUMN = "SessionID";
    public static final String IIN_COLUMN = "IIN";
    public static final String LAST_LOGIN_COLUMN = "LastLogin";
    public static final String LAST_LOGIN_IP_COLUMN = "LastLoginIP";
    public static final String CLIENT_NAME_COLUMN = "ClentName";
    public static final String LAST_CLIENT_LOGIN_COLUMN = "LastClientLogin";
    public static final String ID_DEPARTMENT_COLUMN = "ID_Department";
    public static final String ID_USER_POSITION_COLUMN = "ID_UserPosition";
    public static final String ID_ROLE_COLUMN = "ID_Role";
    public static final String BLOCKED_DATE_COLUMN = "BlockedDate";
    public static final String FLAG_FIRST_LOGIN_COLUMN = "FlagFirstLogin";

    public static final String ID_FILE_COLUMN = "ID_File";
    public static final String NAME_COLUMN = "Name";
    public static final String DATA_COLUMN = "Data";
    public static final String FLAG_HIDDEN_COLUMN = "FlagHidden";
    public static final String ID_FILE_TYPE_COLUMN = "ID_FileType";
    public static final String ID_FOLDER_COLUMN = "ID_Folder";
    public static final String ID_USERCREATED_COLUMN = "ID_UserCreated";
    public static final String ID_USERDELETED_COLUMN = "ID_UserDeleted";
    public static final String ID_USERERASED_COLUMN = "ID_UserErased";
    public static final String CREATIONDATE_COLUMN = "CreationDate";

    public static final String ID_PARENT_DEPARTMENT_COLUMN = "ID_ParentDepartment";
    public static final String LOCALE_RU_COLUMN = "LocaleRU";
    public static final String LOCALE_KZ_COLUMN = "LocaleKZ";






}
