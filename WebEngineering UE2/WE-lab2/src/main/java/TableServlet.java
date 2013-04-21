/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import beans.Player;
import beans.TableBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
    
    private TableBean getTableBean(HttpSession session) {
        TableBean bean;
        
        if (session.getAttribute("info") == null) { 
            bean = new TableBean();
            bean.setPlayer1(new Player("Super Mario"));
            bean.setPlayer2(new Player("Super C"));
            bean.setRound(1);
            
            session.setAttribute("info", bean);
        } else {
            bean = (TableBean) session.getAttribute("info");
        }
        
        return bean;
    }

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
        TableBean bean = getTableBean(request.getSession(true));
        
        if (bean.getPlayer1().getNextPosition() > bean.getPlayer2().getNextPosition()) {
            bean.setLeader(bean.getPlayer1().getName());
        } else if (bean.getPlayer1().getNextPosition() < bean.getPlayer2().getNextPosition()) {
            bean.setLeader(bean.getPlayer2().getName());
        } else {
            bean.setLeader("mehrere");
        }
        
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/table.jsp");
        dispatcher.forward(request, response);
    }

    private void resetPlayer(Player player) {
        player.setPosition(0);
        player.setLastResult(0);
        player.setNextPosition(0);
    }

    private void movePlayer(Player player) {
        player.setLastResult(random.nextInt(3) + 1);
        player.setNextPosition(player.getPosition() + player.getLastResult());
        
        if (isOil(player.getNextPosition())) {
            player.setNextPosition(0);
        }
    }

    private boolean isOil(int position) {
        return position == 2 || position == 5;
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
        System.out.println("goGet");
        processRequest(request, response);
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
        System.out.println("goPost");
        TableBean bean = getTableBean(request.getSession(true));
        
        bean.getPlayer1().setPosition(bean.getPlayer1().getNextPosition());
        bean.getPlayer2().setPosition(bean.getPlayer2().getNextPosition());
        
        if ("true".equals(request.getParameter("newGame"))) {
            resetPlayer(bean.getPlayer1());
            resetPlayer(bean.getPlayer2());
            bean.setRound(1);
        }
        if ("true".equals(request.getParameter("rollDice"))) {
            movePlayer(bean.getPlayer1());
            movePlayer(bean.getPlayer2());
            bean.setRound(bean.getRound()+1);
            
        }
        
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
