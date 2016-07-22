package spring.tutorial.DAO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.tutorial.model.MatrixBodySquare;
import spring.tutorial.model.Permission;
import spring.tutorial.model.RoleObjectMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * -------------------------------------------------------------------------
 * Project: FileReadingTest
 * Date: 10/18/15
 *
 * @author Michael Menard
 */
@Component
public class FileDAO {

    @Value("${properties.roles}")
    private File roles;
    @Value("${properties.objects}")
    private File resource;
    @Value("${properties.permissions}")
    private File permissions;



    Scanner reader = null;

    public Map<String, String> readInRoleHierarchy() {

        Map<String, String> roleTree = new HashMap<>();
        int lineNum = 0;

        try {
            reader = new Scanner(new BufferedReader(new java.io.FileReader(roles)));

            while (reader.hasNext() && reader != null) {

                lineNum++;

                String nextAscendant = reader.next();
                String nextDescendant = reader.next();

                if (roleTree.containsKey(nextAscendant)) {
                    System.out.println("invalid line is found in roleHierarchy.txt: line <"+ lineNum +">");
                    System.out.println("enter any key to read it again");
                    pressAnyKeyToContinue();
                    reader.close();
                    readInRoleHierarchy();
                }

                roleTree.put(nextAscendant, nextDescendant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return roleTree;

    }

    public List<String> readInObjects(){

        List<String> resources = new ArrayList<>();
        try {
            reader = new Scanner(new BufferedReader(new java.io.FileReader(resource)));

            while (reader.hasNext() && reader != null) {

                String nextResource= reader.next();

                if (resources.contains(nextResource)) {
                    System.out.println("duplicate object is found: <" + nextResource + ">,");
                    System.out.println("enter any key to read it again");
                    pressAnyKeyToContinue();
                    reader.close();

                    readInObjects();
                }

                resources.add(nextResource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return resources;
    }

    public void readInPermissions(RoleObjectMatrix rom){

        try {
            reader = new Scanner(new BufferedReader(new java.io.FileReader(permissions)));

            while (reader.hasNext() && reader != null) {

                String nextRole= reader.next();
                String nextPermission= reader.next();
                String nextResource= reader.next();

                if (rom.getMatrixBodySquare(rom.getObjectsHeader().get(nextResource),
                        rom.getRolesHeader().get(nextRole)) == null){
                    rom.addMatrixBodySquare(rom.getObjectsHeader().get(nextResource),
                            rom.getRolesHeader().get(nextRole), new MatrixBodySquare(new Permission(nextPermission)));
                } else {
                    MatrixBodySquare mbs = (MatrixBodySquare) rom.getMatrixBodySquare(rom.getObjectsHeader()
                                    .get(nextResource), rom.getRolesHeader().get(nextRole));
                    mbs.addPermission(new Permission(nextPermission));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /********************************** Helper Functions ***************************/

    private static void pressAnyKeyToContinue() {
        Scanner scanner = new Scanner(System.in);
        try
        {
            System.in.read();
        }
        catch(Exception e)
        { e.printStackTrace(); }
    }

}
