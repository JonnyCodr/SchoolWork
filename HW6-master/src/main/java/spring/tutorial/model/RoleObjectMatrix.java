package spring.tutorial.model;

import java.util.HashMap;
import java.util.List;

/**
 * -------------------------------------------------------------------------
 * Project: HW6
 * Date: 10/20/15
 *
 * @author Michael Menard
 */
public class RoleObjectMatrix {

    private RoleHierarchy roleHierarchyMap;
    private List<String> resources;

    private HashMap<String, Integer> rolesHeader = new HashMap<>();
    private HashMap<String, Integer> objectsHeader = new HashMap<>();

    // row > column
    private MatrixSquare[][] matrix;


    /************************ Constructor *********************************************************/
    public RoleObjectMatrix(final RoleHierarchy roles, final List<String> resources){
        this.roleHierarchyMap = roles;
        this.resources = resources;

        populateRolesHeader();
        populateObjectsHeader();

        matrix = new MatrixSquare[roleHierarchyMap.getRoles().size() + 2][resources.size() + 2];
    }

    private void populateRolesHeader(){

        int rowNum = 0;

        for (String r : this.roleHierarchyMap.getAllRoles()){
            rowNum++;
            this.rolesHeader.put(r, rowNum);
        }
    }

    private void populateObjectsHeader(){
        int rowNum = 0;

        for(String o : this.resources){
            rowNum++;
            this.objectsHeader.put(o, rowNum);
        }
    }

    public void addRoleHeadersToDisplayMatrix(){

        for (String roleName : roleHierarchyMap.getAllRoles()){
            
            if (matrix[rolesHeader.get(roleName)][0] == null){
                matrix[rolesHeader.get(roleName)][0] = new MatrixHeader(roleName);

            } else {
                System.out.println("ERR: failed to add roles titles");
                break;
            }
        }
    }

    public void addObjectsToDisplayMatrix(){

        for (String obj : resources){
            if (matrix[0][objectsHeader.get(obj)] == null){
                matrix[0][objectsHeader.get(obj)] = new MatrixHeader(obj);
            } else {
                System.out.println("ERR: failed to add object titles");
                break;
            }
        }
    }

    public void addMatrixBodySquare(int object, int role, MatrixBodySquare square){

//        object > role
        matrix[object][role] = square;
    }


    public void displayRoleObjectMatrix() {

        System.out.format("%4s|", "");
        for (String res : objectsHeader.keySet()){
            System.out.format("%8s", res);
        }
        System.out.println('\n');
        for(String role : rolesHeader.keySet()){
            System.out.format("%-4s|", role);
            for (String res : objectsHeader.keySet()){
                MatrixBodySquare ms =
                        (MatrixBodySquare) getMatrixBodySquare(objectsHeader.get(res), rolesHeader.get(role));
                if (ms == null){
                    System.out.format("%8s", "-");
                } else{
                    for (Permission permission : ms.getPermissions()){
                        if (permission.getPermission() == null)
                            System.out.format("%8s", "-");
                        else
                            System.out.format("%8s", permission.getPermission());
                    }
                }
            }
            System.out.println('\n');

        }

//        System.out.print("     ");
//        for(String role : roleHierarchyMap.getAllRoles()){
//            System.out.format("%5s", "|");
//            for(int i = 0 ; i< resources.size(); i++){
//                System.out.format("%4s", resources.get(i));
//            }
//            System.out.print('\n');
//            System.out.format("%4s|%4s%4s%4s%4s%4s", role,"","","","","" +
//                    "");
//            System.out.format("%n%5s", "");
//            System.out.print('\n');
//        }

    }

    /**************************************** Mutators ****************************************/
    public MatrixSquare[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(MatrixSquare[][] matrix) {
        this.matrix = matrix;
    }

    public MatrixSquare getMatrixBodySquare(int object, int role){
        return matrix[object][role];
    }

    public HashMap<String, Integer> getObjectsHeader() {
        return objectsHeader;
    }

    public void setObjectsHeader(HashMap<String, Integer> objectsHeader) {
        this.objectsHeader = objectsHeader;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public RoleHierarchy getRoleHierarchyMap() {
        return roleHierarchyMap;
    }

    public void setRoleHierarchyMap(RoleHierarchy roleHierarchyMap) {
        this.roleHierarchyMap = roleHierarchyMap;
    }

    public HashMap<String, Integer> getRolesHeader() {
        return rolesHeader;
    }

    public void setRolesHeader(HashMap<String, Integer> rolesHeader) {
        this.rolesHeader = rolesHeader;
    }

}
