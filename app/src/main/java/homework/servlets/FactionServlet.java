package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.Dao;
import homework.dao.FactionDao;
import homework.models.Faction;
import homework.utils.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "factionServlet", value = "/factions")
public class FactionServlet extends HttpServlet {
    Dao<Faction> dao = new FactionDao(HibernateUtil.getSessionFactory());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String factionId = req.getParameter("faction_id");
            if (factionId != null) {
                writer.println(new ObjectMapper().writeValueAsString(dao.getById(Long.valueOf(factionId))));
            } else {
                writer.println(new ObjectMapper().writeValueAsString(dao.getAll()));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("faction_id") != null) {
                writer.println("You actually don't need to specify id here. For updating use PUT method.");
                writer.println("Aborting operation...");
            } else {
                dao.save(parseFaction(req));
                writer.println("Your faction must have been saved");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            if (req.getParameter("faction_id") == null) {
                writer.println("You actually do need to specify id here. For creating use POST method.");
                writer.println("Aborting operation...");
            } else {
                dao.update(parseFaction(req));
                writer.println("Your faction must have been updated");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String factionId = req.getParameter("faction_id");
            if (factionId == null) {
                writer.println("You actually do need to specify id here. Cannot delete without id.");
                writer.println("Aborting operation...");
            } else {
                dao.delete(Long.valueOf(factionId));
                writer.println("Your faction must have been deleted");
            }
        }
    }

    private static Faction parseFaction(HttpServletRequest req) {
        String idHolder = req.getParameter("faction_id");

        Long factionId = idHolder == null ? null : Long.valueOf(idHolder);
        String factionName = req.getParameter("faction_name");
        String factionCredo = req.getParameter("faction_credo");
        return new Faction(factionId, factionName, factionCredo);
    }
}
