import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
class Student1 {
    private String firstname;
    private String lastname;
    private String gender;
    private int age;
    private int studentnumber;
    public Student1(String firstname, String lastname, String gender, int age, int studentnumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.studentnumber = studentnumber;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getGender() {
        return gender;
    }
    public int getAge() {
        return age;
    }
    public int getStudentnumber() {
        return studentnumber;
    }
    @Override
    public String toString() {
        return "first name: " + firstname + " last name: " + lastname + " gender: " + gender + " student number: " + studentnumber;
    }
    public String toCSV() {
        return firstname + "," + lastname + "," + gender + "," + age + "," + studentnumber;
    }
    public static Student1 fromCSV(String csv) {
        String[] parts = csv.split(",");
        if (parts.length <= 4) {
            System.out.println("error" + csv);
            return null;
        }
        try {
            return new Student1(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        } catch (NumberFormatException e) {
            System.out.println("error parsing numbers: " + csv);
            return null;
        }
    }
}
class StudentSystem {
    private List<Student1> students = new ArrayList<>();
    private static final String string = "students.txt";
    public void addStudent(Student1 student) {
        students.add(student);
    }
    public Student1 findStudent(String lastname) {
        for (Student1 student : students) {
            if (student.getLastname().equalsIgnoreCase(lastname)) {
                return student;
            }
        }
        return null;
    }
    public List<Student1> findStudentsByFirstname(String firstname) {
        List<Student1> findingstudent = new ArrayList<>();
        for (Student1 student : students) {
            if (student.getFirstname().equalsIgnoreCase(firstname)) {
                findingstudent.add(student);
            }
        }
        return findingstudent;
    }
    public boolean deleteStudent(int studentnumber) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentnumber() == studentnumber) {
                students.remove(i);
                return true;
            }
        }
        return false;
    }
    public void listAll() {
        if (students.isEmpty()) {
            System.out.println("no student is alive!");
        } else {
            for (Student1 student : students) {
                System.out.println(student);
            }
        }
    }
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(string))) {
            for (Student1 student : students) {
                writer.write(student.toCSV());
                writer.newLine();
            }
            System.out.println("saved successfully");
        } catch (IOException e) {
            System.out.println("error saving: " + e.getMessage());
        }
    }
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(string))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student1 student = Student1.fromCSV(line);
                if (student != null) {
                    students.add(student);
                }
            }
            System.out.println("loading wait a bit...");
        } catch (IOException e) {
            System.out.println("error loading: " + e.getMessage());
        }
    }
}
class StudentListt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentSystem studentSystem = new StudentSystem();
        // Sample students
        studentSystem.addStudent(new Student1("zahra", "mamadi", "female", 23, 4567));
        studentSystem.addStudent(new Student1("ali", "arnavaz", "male", 25, 4009));
        studentSystem.addStudent(new Student1("ayda", "kabiri", "female", 21, 4590));
        studentSystem.addStudent(new Student1("arian", "bavarsad", "male", 22, 4012));
        studentSystem.addStudent(new Student1("aiad", "bavarsad", "female", 20, 4034));
        studentSystem.loadData();
        while (true) {
            System.out.println("1.add");
            System.out.println("2.find");
            System.out.println("3.delete");
            System.out.println("4.list all");
            System.out.println("5.find by first name");
            System.out.println("6.saving");
            System.out.print("choice your weapon:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                System.out.println("enter first name:");
                String firstname = scanner.nextLine();
                System.out.println("enter last name:");
                String lastname = scanner.nextLine();
                System.out.println("enter gender:");
                String gender = scanner.nextLine();
                System.out.println("enter age:");
                int age = scanner.nextInt();
                System.out.println("enter student number:");
                int studentnumber = scanner.nextInt();
                scanner.nextLine();
                studentSystem.addStudent(new Student1(firstname, lastname, gender, age, studentnumber));
                System.out.println("successfully added :)");
            } else if (choice == 2) {
                System.out.println("enter last name:");
                String name = scanner.nextLine();
                Student1 student = studentSystem.findStudent(name);
                if (student != null) {
                    System.out.println(student);
                } else {
                    System.out.println("student does not exist :(");
                }
            } else if (choice == 3) {
                System.out.println("enter student number:");
                int studentnumber = scanner.nextInt();
                boolean deleted = studentSystem.deleteStudent(studentnumber);
                if (deleted) {
                    System.out.println("successfully deleted :)");
                } else {
                    System.out.println("student does not exist :(");
                }
            } else if (choice == 4) {
                studentSystem.listAll();
            } else if (choice == 5) {
                System.out.println("enter first name:");
                String firstname = scanner.nextLine();
                List<Student1> studentsByFirstname = studentSystem.findStudentsByFirstname(firstname);
                if (studentsByFirstname.isEmpty()) {
                    System.out.println("student does not exist :(");
                } else {
                    for (Student1 student : studentsByFirstname) {
                        System.out.println(student);
                    }
                }
            } else if (choice == 6) {
                studentSystem.saveData();
                System.out.println("saved :)");
                break;
            } else {
                System.out.println("you lost! try again");
            }
        }
    }
}
