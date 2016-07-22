package spring.tutorial.model;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------------------------------------------
 * Project: HW6
 * Date: 10/20/15
 *
 * @author Michael Menard
 */
public class MatrixBodySquare extends MatrixSquare {

    List<Permission> permissions;

    public MatrixBodySquare(Permission thePermission){
        super();
        this.permissions = new ArrayList<>();
        permissions.add(thePermission);
    }

    public void addPermission(Permission thePermission){
        permissions.add(thePermission);
    }

    public List<Permission> getPermissions() {
        return permissions;
    }



    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /********************************************************************************************************/

}
