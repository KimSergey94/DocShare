package kz.itbc.docshare.entity;

import java.sql.Timestamp;

public class User {
    private long id_User;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String middleName;
    private boolean flagDeleted;
    private boolean flagAccess;
    private String accessIP;
    private String sessionID;
    private String iin;
    private Timestamp lastLogin;
    private String lastLoginIP;
    private String clientName;
    private Timestamp lastClientLogin;
    private Position position;
    private Department department;

    private int id_Role;
    private Timestamp blockedDate;
    private boolean flagFirstLogin;
    private Role role;



    public long getId_User() {
        return id_User;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean isFlagDeleted() {
        return flagDeleted;
    }

    public boolean isFlagAccess() {
        return flagAccess;
    }

    public String getAccessIP() {
        return accessIP;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getIin() {
        return iin;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public String getClientName() {
        return clientName;
    }

    public Timestamp getLastClientLogin() {
        return lastClientLogin;
    }

    public int getId_Role() {
        return id_Role;
    }

    public Timestamp getBlockedDate() {
        return blockedDate;
    }

    public boolean isFlagFirstLogin() {
        return flagFirstLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setId_User(long id_User) {
        this.id_User = id_User;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setFlagDeleted(boolean flagDeleted) {
        this.flagDeleted = flagDeleted;
    }

    public void setFlagAccess(boolean flagAccess) {
        this.flagAccess = flagAccess;
    }

    public void setAccessIP(String accessIP) {
        this.accessIP = accessIP;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public void setLastClientLogin(Timestamp lastClientLogin) {
        this.lastClientLogin = lastClientLogin;
    }

    public void setId_Role(int id_Role) {
        this.id_Role = id_Role;
    }

    public void setBlockedDate(Timestamp blockedDate) {
        this.blockedDate = blockedDate;
    }

    public void setFlagFirstLogin(boolean flagFirstLogin) {
        this.flagFirstLogin = flagFirstLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId_User() + ", name: " + this.getFirstName() + ", " + "email: " + this.getEmail();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
