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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Usuario usuario = usuarioDAO.obtenerUsuario(username);

        if (usuario == null) {
            request.setAttribute("error", "Usuario no encontrado. Por favor, regístrese.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!usuario.getPassword().equals(password)) {
            request.setAttribute("error", "Contraseña incorrecta");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);
        response.sendRedirect("menu.jsp");
    }
}
