/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import beans.Player;
import beans.TableBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peter
 */
public class TableServlet extends HttpServlet {

    private Random random = new Random();

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        TableBean bean;
        
        if (session.isNew()) {
            bean = new TableBean();
            bean.setPlayer1(new Player("Super Mario"));
            bean.setPlayer2(new Player("Super C"));
            
            session.setAttribute("info", bean);
        }
        
        bean = (TableBean) session.getAttribute("info");
        if(bean.getPlayer1().getNextPosition() > bean.getPlayer2().getNextPosition()) {
            bean.setLeader(bean.getPlayer1().getName());
        } else if(bean.getPlayer1().getNextPosition() < bean.getPlayer2().getNextPosition()) {
            bean.setLeader(bean.getPlayer2().getName());
        } else {
            bean.setLeader("mehrere");
        }

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/table.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        if ("true".equals(request.getParameter("newGame"))) {
            TableBean bean = (TableBean) request.getSession().getAttribute("info");
            bean.getPlayer1().setPosition(0);
            bean.getPlayer2().setPosition(0);
            bean.getPlayer1().setLastResult(0);
            bean.getPlayer2().setLastResult(0);
        }
        if ("true".equals(request.getParameter("rollDice"))) {
            TableBean bean = (TableBean) session.getAttribute("info");

            rollDice(bean.getPlayer1());
            rollDice(bean.getPlayer2());
        }
        processRequest(request, response);
    }

    private void rollDice(Player player) {
        player.setPosition(player.getNextPosition());
        player.setLastResult(random.nextInt(3) + 1);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
