package com.itheima.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.Student;
import com.itheima.service.IStudentService;
import com.itheima.service.impl.StudentServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet("/studentServlet")
public class StudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String method = request.getParameter("method");
        if ("selectByPage".equals(method)) {
            selectByPage(request, response);
        }
        if ("addStu".equals(method)) {
            addStu(request, response);
        }
        if ("editStu".equals(method)) {
            editStu(request, response);
        }
        if ("deleteStu".equals(method)){
            deleteStu(request,response);
        }
    }

    private void deleteStu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageSize = null;
        String currentPage = null;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            currentPage = request.getParameter("currentPage");
            pageSize = request.getParameter("pageSize");
            String number = request.getParameter("number");

            IStudentService studentService = new StudentServiceImpl();
            studentService.deleteStu(number);
            out.print(true);
            response.sendRedirect(request.getContextPath() + "/studentServlet?method=selectByPage&currentPage=" + currentPage + "&pageSize=" + pageSize);
        } catch (Exception e) {
            out.print(false);
            e.printStackTrace();
        }
    }

    private void editStu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageSize = null;
        String currentPage = null;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            currentPage = request.getParameter("currentPage");
            pageSize = request.getParameter("pageSize");
            Map<String, String[]> map = request.getParameterMap();
            Student student = new Student();

            //注册日期转换方法,将字符串日期转换成Date类型
            dataConvert();

            BeanUtils.populate(student, map);
            IStudentService studentService = new StudentServiceImpl();
            studentService.editStu(student);
            out.print(true);
            response.sendRedirect(request.getContextPath() + "/studentServlet?method=selectByPage&currentPage=" + currentPage + "&pageSize=" + pageSize);
        } catch (Exception e) {
            out.print(false);
            e.printStackTrace();
        }

    }

    private void addStu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentPage = null;
        String pageSize = null;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String[]> map = request.getParameterMap();
            currentPage = request.getParameter("currentPage");
            pageSize = request.getParameter("pageSize");

            //注册日期转换方法,将字符串日期转换成Date类型
            dataConvert();
            Student student = new Student();

            BeanUtils.populate(student, map);
            IStudentService studentService = new StudentServiceImpl();
            studentService.addStu(student);
            out.print(true);
            response.sendRedirect(request.getContextPath() + "/studentServlet?method=selectByPage&currentPage=" + currentPage + "&pageSize=" + pageSize);
        } catch (Exception e) {
            out.print(false);
        }

    }

    private void dataConvert() {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class aClass, Object o) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return sdf.parse(o.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, Date.class);
    }

    private void selectByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        if (currentPageStr == null) {
            currentPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);
        if (pageSizeStr == null) {
            pageSizeStr = "5";
        }
        int pageSize = Integer.parseInt(pageSizeStr);
        IStudentService studentService = new StudentServiceImpl();
        Page page = studentService.selectByPage(currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(page);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(pageInfo);
        out.write(value);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
