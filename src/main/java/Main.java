import java.io.IOException;
import java.util.Scanner;

class WrongStudentName extends Exception { }
class WrongAge extends Exception { }
class WrongDateOfBirth extends Exception { }
class WrongSwitch extends Exception { } 
class FileReadError extends Exception {} 

public class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            try {
                char ex = menu();
                switch(ex) {
                    case '1': exercise1(); break;
                    case '2': exercise2(); break;
                    case '3': exercise3(); break;
                    case '0': return;
                    default: 
                        throw new WrongSwitch();
                }
            } catch(IOException e) {
                System.out.println("Błąd wejścia/wyjścia: " + e.getMessage());
            } catch(WrongStudentName e) {
                System.out.println("Błędne imie studenta!");
            } catch(WrongAge e) {
                System.out.println("Błędny wiek studenta!");
            } catch(WrongDateOfBirth e) {
                System.out.println("Błędna data urodzenia studenta!");
            } catch (WrongSwitch e) { 
                System.out.println("Nieprawidłowy wybór!");
            } catch (FileReadError e) { 
                System.out.println("Błąd odczytu danych z pliku! Program zakończy działanie.");
                return; 
            } catch (Exception e) {
                System.out.println("Nieoczekiwany błąd: " + e.getMessage());
                return; 
            }
        }
    }

    public static char menu() {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        String input = scan.nextLine();
        return input.isEmpty() ? ' ' : input.charAt(0);
    }

    public static String ReadName() throws WrongStudentName {
        System.out.println("Podaj imie: ");
        String name = scan.nextLine();
        if(name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static int ReadAge() throws WrongAge {
        System.out.println("Podaj wiek: ");
        int age = scan.nextInt();
        scan.nextLine(); 
        if(age < 0 || age > 100)
            throw new WrongAge();
        return age;
    }

    public static String ReadDate() throws WrongDateOfBirth {
        System.out.println("Podaj datę urodzenia DD-MM-YYYY");
        String date = scan.nextLine();
        String[] parts = date.split("-");
        if (parts.length != 3) {
            throw new WrongDateOfBirth(); 
        }
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2024) {
            throw new WrongDateOfBirth();
        }
        return date;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        String name = ReadName();
        int age = ReadAge();
        String date = ReadDate();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException, FileReadError {
        var students = (new Service()).getStudents();
        for(Student current : students) {
            if (current.GetName().equals("Parse Error")) {
                throw new FileReadError(); 
            }
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException, FileReadError {
        System.out.println("Podaj imie: ");
        String name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}