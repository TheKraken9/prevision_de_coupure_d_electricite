package com.thekraken9.prevision_de_coupure_electrique;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;

import com.thekraken9.prevision_de_coupure_electrique.model.Coupure;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "WEB-INF/index.jsp";
        response.sendRedirect(view);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Date date = Date.valueOf(request.getParameter("date"));
        Coupure coupure = new Coupure();
        ArrayList<Coupure> coupures = new ArrayList<>();
        try {
            coupures = coupure.coupureParSecteur(null, date);
            request.setAttribute("coupures", coupures);
            request.getRequestDispatcher("WEB-INF/coupure.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}