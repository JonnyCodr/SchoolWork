package spring.tutorial.model;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * -------------------------------------------------------------------------
 * Project: HW6
 * Date: 10/18/15
 *
 * @author Michael Menard
 */
public class RoleHierarchy {

    private HashMap<String, String> roles;

    public RoleHierarchy() { /* intentionally left blank */ }

    public HashMap<String, String> getRoleHierarchy() {
        return roles;
    }

    public boolean addDescendant(String asc, String desc){

        if(roles.containsKey(asc)){
            return false;
        }
        else {
            roles.put(asc,desc);
            return true;
        }
    }

    public boolean desendantExists(String descendant){
        return roles.containsKey(descendant);
    }

    public Set<String> getAllRoles(){
        Set<String> roleSet = roles.keySet().stream().collect(Collectors.toSet());

        roleSet.addAll(roles.values().stream().collect(Collectors.toList()));
        return roleSet;
    }

    public HashMap<String, String> getRoles() {
        return roles;
    }

    public void setRoles(HashMap<String, String> roles) {
        this.roles = roles;
    }
}
