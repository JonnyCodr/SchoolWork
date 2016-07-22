package spring.tutorial.model;

/**
 * -------------------------------------------------------------------------
 * Project: HW6
 * Date: 10/21/15
 *
 * @author Michael Menard
 */
public class Permission {

    public Permission(String permission){
        this.permission = permission;
        this.isStarred = permission.endsWith("*");
    }

    private String permission;
    private boolean isStarred;

    public boolean isStarred() {
        return isStarred;
    }

    public void setIsStarred(boolean isStarred) {
        this.isStarred = isStarred;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
