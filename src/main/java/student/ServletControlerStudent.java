package student;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import java.util.List;

@WebServlet(urlPatterns = "/ServletControllerStudents")
public class ServletControlerStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand=request.getParameter("command");
            if(theCommand==null){
                theCommand="LIST";
            }
            switch (theCommand){
                case "LIST":
                    listStudent(request,response);
                    break;
                case  "ADD":
                    addStudent(request,response);
                    break;
                case  "LOAD":
                    loadStudent(request,response);
                    break;
                case  "UPDATE":
                    updateStudent(request,response);
                    break;
                case  "DELETE":
                    deleteStudent(request,response);
                    break;
                default:
                    listStudent(request,response);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id=request.getParameter("studentId");
        StudentDbUtil.deleteStudent(id);
        listStudent(request,response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int Id= Integer.parseInt( request.getParameter("studentId"));
        String firstName=request.getParameter("firstName");
        String lastName=request.getParameter("lastName");
        String email=request.getParameter("email");
        Student student=new Student(Id,firstName,lastName,email);
        StudentDbUtil.updateStudent(student);
        listStudent(request,response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
        String studentID= request.getParameter("studentId");

        Student student=StudentDbUtil.getStudent(Integer.parseInt(studentID));
        request.setAttribute("THE_STUDENT", student);
        getServletContext().getRequestDispatcher("/update-student-form.jsp").forward(request,response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String firstName=request.getParameter("firstName");
        String lastName=request.getParameter("lastName");
        String email=request.getParameter("email");
        Student student=new Student(firstName,lastName,email);
        StudentDbUtil.addStudent(student);
        listStudent(request,response);


    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudentDbUtil studentDbUtil=new StudentDbUtil();
        List<Student> students = studentDbUtil.getStudents();
        request.setAttribute("STUDENT_LIST",students);
        request.getServletContext().getRequestDispatcher("/list-students.jsp").forward(request,response);
    }

}
