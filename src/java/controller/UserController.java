/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Usuarios;
import model.dao.UsuariosDAO;

/**
 *
 * @author Senai
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController", "/home", "/login", "/logar"})
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paginaAtual = request.getServletPath();
        String nome = "";
        String sobrenome = "";
        String status = "";
        
        Cookie[] cookies = request.getCookies();
        
        for(Cookie c: cookies) {
	// buscar um cookie especifico
        if(c.getName().equals("nome")) {
        	// pegar o valor daquele coockie
                nome = c.getValue();
	}
        if(c.getName().equals("sobrenome")) {
        	// pegar o valor daquele coockie
                sobrenome = c.getValue();
	}
        if(c.getName().equals("status")) {
        	// pegar o valor daquele coockie
                status = c.getValue();
	}
}
        if (paginaAtual.equals("/home")) {
            request.setAttribute("nome", nome);
            request.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(request, response);
        } else if (paginaAtual.equals("/login")) {
            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
        }        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paginaAtual = request.getServletPath();
        if (paginaAtual.equals("/logar")) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            Usuarios user = new UsuariosDAO().logar(email, senha);
            
            if (user.getId() > 0) {
                Cookie cNome = new Cookie("nome", user.getNome());
                Cookie cSobrenome = new Cookie("sobrenome", user.getSobrenome());
                Cookie cStatus = new Cookie("status", user.getStatus());
                
                response.addCookie(cNome);
                response.addCookie(cSobrenome);
                response.addCookie(cStatus);
                
                response.sendRedirect("./home");
            } else {
                response.sendRedirect("./login");
            }
            
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
