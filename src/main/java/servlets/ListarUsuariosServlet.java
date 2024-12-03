package servlets;

import dao.UsuarioDAO;
import dao.UsuarioDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;


import java.io.IOException;
import java.util.List;


@WebServlet("/listarUsuarios")
public class ListarUsuariosServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("listarUsuarios.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
