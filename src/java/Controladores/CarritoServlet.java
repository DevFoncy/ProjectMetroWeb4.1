/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Modelo.DAO.ProductoDAO;
import Modelo.DTO.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import CarritoCompras.Carrito;

/**
 *
 * @author Junior Alvarez
 */
public class CarritoServlet extends HttpServlet
{

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
            throws ServletException, IOException
    {
        if(request.getSession().getAttribute("panier") == null)
            request.getSession().setAttribute("panier", new Carrito());
        
        Carrito p = (Carrito)request.getSession().getAttribute("panier");
            
        ProductoDAO dao = new ProductoDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        Producto a = dao.findByID(id);
        
        if(request.getParameter("op") == null || a == null)
        {
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&failed");
            return ;
        }
        
        
        if(request.getParameter("op").equals("add"))
        {
            p.addProducto(a);
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&succes");
        }
        
        if(request.getParameter("op").equals("sous"))
        {
            p.sousProducto(a);
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&succes");
        }
        
        if(request.getParameter("op").equals("rem"))
        {
            p.removeProducto(a);
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&succes");
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
            throws ServletException, IOException
    {
        processRequest(request, response);
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
