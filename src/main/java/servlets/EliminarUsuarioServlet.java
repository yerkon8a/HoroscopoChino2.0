package servlets;

import dao.UsuarioDAO;
import dao.UsuarioDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/eliminarUsuario")
public class EliminarUsuarioServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                Long id = Long.parseLong(idStr);
                usuarioDAO.eliminarUsuario(id);
                response.sendRedirect("listarUsuarios?mensaje=Usuario eliminado exitosamente");
            } else {
                response.sendRedirect("listarUsuarios?error=ID de usuario no proporcionado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("listarUsuarios?error=Error al eliminar usuario");
        }
    }
}
