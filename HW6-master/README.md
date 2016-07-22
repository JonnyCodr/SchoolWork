
Part II: Write a Java program to implement a simplified NIST RBAC model. (The topology and examples here are provided for you to test your program. The instructor will use similar but different test cases to test your program.
￼￼￼1. Data structures: it is up to you to choose appropriate data structures to maintain role hierarchy, user-role matrix, role-object matrix, etc.
2. Role Hierarchy: this program must support role hierarchy defined in NIST RBAC.
2.1 This program reads a role hierarchy from file “roleHierarchy.txt”. Each line in this file is formatted as “<ascendant> <descendant>”. For example, for the role hierarchy on the right, the first several lines in roleHiearchy.txt is
R8 R6 R9 R7 R10 R7 ......
2.2 The contents of this text file must be validated to enforce the “limited role hierarchies” defined in NIST RBAC (See the slide titled “Hierarchical RBAC”). For an invalid file:  
  (1) close the file, 
  (2) display “invalid line is found in roleHierarchy.txt: line <line # of the first invalid line>, 
      enter any key to read it again” to allow user to fix the problem, 
  (3) once reading any user input, go back to 2.2. Continue if it’s valid. 2.3 Display the role hierarchy top down. If you may display it as a tree, it is great. Otherwise, the following format is fine.
R1--->R2, R3 
R2--->R4, R5, R6 
R3--->R7
......
Your program will be evaluated
￼according to the tasks that it can accomplish and the test cases that it can pass INSTEAD OF how correct the source code is.)
R1 R2 R3
￼￼￼￼￼￼￼￼￼￼￼￼￼R4 R5 R6 R7
R8 R9 R10

￼￼￼￼￼￼3. Objects: This program reads all resource objects from file “resourceObjects.txt”, which contains ONE line. For example, F1 F2 F3 F4 P1 P2 P3 D1 D2

3.1 Validate objects for duplication. For an invalid file, (1) close the file, (2) display “duplicate object is found: < object>, enter any key to read it again” to allow user to fix the problem, (3) once reading any user input, go back to 3.1. Continue if it’s valid. 
3.2 Display the current role-object matrix with null entries. If necessary, you may break this matrix into multiple sub-matrices for displaying by limiting up to 5 columns per sub-matrix. For example,
R1 ......
R1 ......
R1 R2 R3 R4 R5 R6 R7 R8 R9 R10 F1 F2 F3 F4 P1
R1 ......
R1 ......
P2 P3 D1 D2
4. Granting Permissions to Roles with Inheritance:
4.1 This program must support role inheritance described as “the upper role includes all of the access rights of the lower role, as well as other access rights not available to the lower role” in the slide titled “Role Hierarchy”. In 4.3, 4.4, and 4.5, whenever a permission is granted to a role, it must be inherited by all the descendants of this role.
4.2 Redundancy must be avoided. For example, when adding “read” by R2 to F1, if “read” is already there, don’t add it again. 4.3 Update the role-object matrix to grant “control” by EACH role to itself. (See 4.1). E.g., R8, R6, R2, and R1 “control” R8.
4.4 Update the role-object matrix to grant “own” by the descendent of EACH role to it. (See 4.1). E.g., R6, R2, and R1 “own” R8. 4.5 This program also reads permissions to roles from file “permissionsToRoles.txt”. Each line in this file is formatted as “<role> <access right> <object>”, where a permission is an access right to an object. For example, after reading the following two lines, R6, R2, and R1 have “write*” to F3, and R2 and R1 have “seek” to D1.
R6 write* F3
R2 seek D1
4.6 Display the current role-object matrix in the same format as what is used in step 3.2.
5. SSD: This program reads mutually exclusive (when n = 1) and cardinality constraints (when n>= 2) from file “roleSetsSSD.txt”.
5.1 Each line includes a constraint given by the value of n and a set of roles. For example, 2 R2 R4 R5 R6 R8
1 R1 R2 R4
......
5.2 Display all the constraints, one in a line. For example,
Constraint 1, n = 2, set of roles = {R2, R4, R5, R6, R8} ...
6. This program constructs the user-role matrix by reading file usersRoles.txt. Each line is formatted as “<user> <list of roles>”.
6.1 While reading each line and updating the user-role matrix, the SSD constraints configured in Step 5 must be enforced. It is also invalid if two or more lines contain the same user. For an invalid file, (1) close the file, (2) display “invalid line is found in usersRoles.txt: line <line # of the first invalid line> due to <constraint #? or duplicated user>, enter any key to read it again” to allow user to fix the problem, (3) once reading any user input, go back to Step 6. Continue if it’s valid.
U1 R2 R5
......
6.2 Display the user-role matrix. For example,
R1 R2 R3 R4 R5 R6 R7 R8 R9 R10
U1 + +
......
7. Query for a given User, a given pair of User and Object, or a given combination of (User, Access Right, Object) 7.1 Display the following messages to get three user inputs: user, object, and accessRight.
Please enter the user in your query:
Please enter the object in your query (hit enter if it’s for any): Please enter the access right in your query (hit enter if it’s for any):
7.2 if user isn’t in the user-role matrix, display “invalid user, try again.” and go back to Step 7.1. Otherwise, continue.
7.3 if object and accessRight are empty, display all the objects that this user has access rights to as “<object> <list of access rights to this object>”, and then go to Step 7.7. Otherwise, continue. To keep it simple, if a user has the same or different lists of access rights to the same object due to different roles assumed by this user, you don’t have to merge them. For example,
R4 control, own
F1 read, write, execute R4 own
...
7.4 if object isn’t in the user-role matrix, display “invalid object, try again.” and go back to Step 7.1. Otherwise, continue. 7.5 if accessRight is empty, display all the access rights this user has to this object and go to Step 7.7. Otherwise, continue. 7.6checkwhetherthisuserhasthegivenaccessRighttothisobject. Ifyes,display“authorized”,otherwise,display “rejected”. Continue.
7.7 Display “Would you like to continue for the next query?” If the user inputs “yes”, go back to Step 7.1. Otherwise, exit.
