package student;

import utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
    public static final String SELECT_STUDENT="select * from studentProject.students;";
    public static final String ADD_STUDENT="insert into studentProject.students (firstName, lastName, email) values (?,?,?);";
    public static final String GET_STUDENT="select * from studentProject.students where id =?;";
    public static final String UPDATE_STUDENT="update studentProject.students set firstName = ?, lastName = ?,email = ? where id = ?;";
    public static final String DELETE_USER="delete from studentProject.students where id=?";
    public static void addStudent(Student student) {
        try(Connection connection=DbUtil.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(ADD_STUDENT)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Student getStudent(int id) {
        Student student=null;
        try(Connection connection= DbUtil.getConnection()){
        PreparedStatement preparedStatement=connection.prepareStatement(GET_STUDENT);
        preparedStatement.setInt(1,id);
        ResultSet resultSet=preparedStatement.executeQuery();

            if (resultSet.next()){
                String firstName=resultSet.getString("firstName");
                String lastName=resultSet.getString("lastName");
                String email=resultSet.getString("email");
                student=new Student(firstName,lastName,email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public static void updateStudent(Student student) {
        try(Connection connection=DbUtil.getConnection()) {
            PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_STUDENT);
            preparedStatement.setString(1,student.getFirstName());
            preparedStatement.setString(2,student.getLastName());
            preparedStatement.setString(3,student.getEmail());
            preparedStatement.setInt(4, student.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteStudent(String id) throws Exception{
        try(Connection connection=DbUtil.getConnection()){
            int Id=Integer.parseInt(id);
            PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1,Id);
            preparedStatement.execute();
        }
    }

    public List<Student> getStudents() throws Exception{
        List<Student> students=new ArrayList<>();
        try(Connection connection= DbUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SELECT_STUDENT);
            ResultSet resultSet= preparedStatement.executeQuery()){
            while (resultSet.next()){
                int id =resultSet.getInt("id");
                String firstName=resultSet.getString("firstName");
                String lastName=resultSet.getString("lastName");
                String email=resultSet.getString("email");
                Student student=new Student(id,firstName,lastName,email);
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return students;
    }
}
