package spring.tutorial;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.tutorial.DAO.FileDAO;
import spring.tutorial.model.RoleHierarchy;
import spring.tutorial.model.RoleObjectMatrix;

import java.util.*;
import java.util.stream.Collectors;

/**
 * -------------------------------------------------------------------------
 * Project: HW6
 * Date: 10/16/15
 *
 * @author Michael Menard
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        FileDAO reader = context.getBean("fileDAO", FileDAO.class);

        /********************************* Roles **************************************/

        RoleHierarchy roleHierarchy = new RoleHierarchy();
        roleHierarchy.setRoles((HashMap<String, String>) reader.readInRoleHierarchy());

        displayRoleHierarchy(roleHierarchy);


        /********************************* Objects **************************************/
        List<String> resources = reader.readInObjects();

        RoleObjectMatrix matrix = new RoleObjectMatrix(roleHierarchy, resources);

        matrix.displayRoleObjectMatrix();

        /********************************* Permissions *********************************/
        reader.readInPermissions(matrix);

        matrix.addObjectsToDisplayMatrix();
        matrix.addRoleHeadersToDisplayMatrix();

        matrix.displayRoleObjectMatrix();



    }


    public static void displayRoleHierarchy(RoleHierarchy roleHierarchy){

        for(String d : new HashSet<>(roleHierarchy.getRoles().values())) {
            Set values = new HashSet(getKeysByValue(roleHierarchy.getRoles(), d));
            System.out.println(d + " ---> " + values);
        }
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


}
