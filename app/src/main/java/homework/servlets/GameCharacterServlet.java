package homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.Dao;
import homework.dao.GameCharacterDao;
import homework.models.GameCharacter;
import homework.utils.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "characterServlet", value = "/characters")
public class GameCharacterServlet extends HttpServlet {
    Dao<GameCharacter> dao = new GameCharacterDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String factionId = req.getParameter("character_id");
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
        String factionId = req.getParameter("faction_id");
        String characterId = req.getParameter("character_id");
        try (var writer = resp.getWriter()) {
            if (factionId != null && characterId != null) {
                try {
                    boolean isSuccessful = addFaction(Long.valueOf(characterId), Long.valueOf(factionId));
                    if (isSuccessful) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        writer.println("Faction was added to this character");
                    } else {
                        writer.println("Nothing happened");
                    }
                } catch (Exception e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    writer.println("For some unknown reason an exception was thrown. Maybe character is already in this faction");
                }
            } else {
                Long returnedId = dao.save(parseCharacter(req));
                if (returnedId != null) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    writer.println("Your character was saved with id " + returnedId);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    writer.println("Something went horribly wrong and your character vanished");
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            try {
                dao.update(parseCharacter(req));
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                writer.println("Character was updated");
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Character was not updated and that's super sad");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String factionId = req.getParameter("faction_id");
        String characterId = req.getParameter("character_id");
        try (var writer = resp.getWriter()) {
            if (factionId != null && characterId != null) {
                boolean isDeleted = deleteFaction(Long.valueOf(characterId), Long.valueOf(factionId));
                if (isDeleted) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    writer.println("Character is no longer in this faction");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer.println("Nothing changed. Maybe you got non-existing ids");
                }
            } else {
                try {
                    boolean isDeleted = dao.delete(Long.valueOf(req.getParameter("character_id")));
                    if (isDeleted) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        writer.println("Character was deleted");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        writer.println("Character was not deleted. Maybe you got non-existing character_id");
                    }
                } catch (Exception e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    writer.println("Character was not deleted. Provide character_id, maybe that's the case");
                }
            }
        }
    }

    private boolean deleteFaction(Long characterId, Long factionId) {
        if (characterId == null || factionId == null) {
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM character_factions WHERE char_id = ? AND fac_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, characterId);
            pr.setLong(2, factionId);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addFaction(Long characterId, Long factionId) {
        if (characterId == null || factionId == null) {
            throw new IllegalArgumentException();
        }
        String sql = "INSERT INTO character_factions VALUES (?, ?)";
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, characterId);
            pr.setLong(2, factionId);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static GameCharacter parseCharacter(HttpServletRequest req) {
        String idHolder = req.getParameter("character_id");
        String weaponIdHolder = req.getParameter("character_weapon_id");
        String powerHolder = req.getParameter("character_powerlevel");

        Long characterId = idHolder == null ? null : Long.valueOf(idHolder);
        Long weaponId = weaponIdHolder == null ? null : Long.valueOf(weaponIdHolder);
        Integer powerlevel = powerHolder == null ? null : Integer.valueOf(powerHolder);
        String characterName = req.getParameter("character_name");
        return new GameCharacter(characterId, weaponId, characterName, powerlevel);
    }
}
