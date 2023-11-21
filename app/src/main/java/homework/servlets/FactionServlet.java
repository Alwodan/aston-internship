package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.Dao;
import homework.dao.FactionDao;
import homework.models.Faction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "factionServlet", value = "/factions")
public class FactionServlet extends HttpServlet {
    Dao<Faction> dao = new FactionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String factionId = req.getParameter("faction_id");
        String s;
        if (factionId == null) {
            s = mapper.writeValueAsString(dao.getAll());
        } else {
            s = mapper.writeValueAsString(dao.getById(Long.parseLong(factionId)));
        }
        try (var writer = resp.getWriter()) {
            writer.println(s);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long returnedId = dao.save(parseFaction(req));
        try (var writer = resp.getWriter()) {
            if (returnedId != null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.println("Your faction was saved with id " + returnedId);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Something went horribly wrong and your faction vanished");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            try {
                dao.update(parseFaction(req));
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                writer.println("Faction was updated");
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Faction was not updated and that's super sad");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            try {
                boolean isDeleted = dao.delete(Long.valueOf(req.getParameter("faction_id")));
                if (isDeleted) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    writer.println("Faction was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer.println("Faction was not deleted. Maybe you got non-existing faction_id");
                }
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Faction was not deleted. Provide faction_id, maybe that's the case");
            }
        }
    }

    private static Faction parseFaction(HttpServletRequest req) {
        String idHolder = req.getParameter("faction_id");
        Long factionId = idHolder == null ? null : Long.valueOf(idHolder);
        String factionName = req.getParameter("faction_name");
        return new Faction(factionId, factionName);
    }
}
