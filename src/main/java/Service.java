import java.util.Collection;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Service {
    public void addStudent(Student student) throws IOException {
        try (var b = new BufferedWriter(new FileWriter("db.txt", true))) {
            b.append(student.ToString());
            b.newLine();
        }
    }

    public Collection<Student> getStudents() throws IOException, FileReadError {
        var ret = new ArrayList<Student>();
        try (var reader = new BufferedReader(new FileReader("db.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ret.add(Student.Parse(line));
            }
        } catch (Exception e) {
            throw new FileReadError();  
        }
        return ret;
    }

    public Student findStudentByName(String name) throws IOException, FileReadError {
        var students = this.getStudents();
        for(Student current : students) {
            if(current.GetName().equals(name))
                return current;
        }
        return null;
    }
}