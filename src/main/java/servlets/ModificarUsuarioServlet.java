package servlets;


import dao.HoroscopoDAO;
import dao.HoroscopoDAOImp;
import dao.UsuarioDAO;
import dao.UsuarioDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Horoscopo;
import modelo.Usuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/modificarUsuario")
public class ModificarUsuarioServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImp();
    private final HoroscopoDAO horoscopoDAO = new HoroscopoDAOImp();

    // Método para manejar GET (cuando accedes a la página de modificar)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        try {
            Long id = Long.parseLong(idStr);
            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);

            if (usuario != null) {
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/modificarUsuario.jsp").forward(request, response);
            } else {
                response.sendRedirect("listarUsuarios?error=Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("listarUsuarios?error=Error al cargar usuario");
        }
    }

    // Método para calcular el animal del horóscopo
    private String calcularAnimal(Date fechaNacimiento, List<Horoscopo> listaHoroscopo) {
        for (Horoscopo horoscopo : listaHoroscopo) {
            Date fechaInicio = horoscopo.getFecha_inicio();
            Date fechaFin = horoscopo.getFecha_fin();

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaNacimiento);
            int yearNacimiento = cal.get(Calendar.YEAR);

            Calendar calInicio = Calendar.getInstance();
            Calendar calFin = Calendar.getInstance();
            calInicio.setTime(fechaInicio);
            calFin.setTime(fechaFin);

            calInicio.set(Calendar.YEAR, yearNacimiento);
            calFin.set(Calendar.YEAR, yearNacimiento);

            if (calFin.before(calInicio)) {
                calFin.add(Calendar.YEAR, 1);
            }

            if ((fechaNacimiento.after(calInicio.getTime()) || fechaNacimiento.equals(calInicio.getTime())) &&
                    (fechaNacimiento.before(calFin.getTime()) || fechaNacimiento.equals(calFin.getTime()))) {
                return horoscopo.getAnimal();
            }
        }
        return "No asignado";
    }

    // Método para manejar POST (cuando envías el formulario de modificación)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);

            if (usuario != null) {
                usuario.setNombre(request.getParameter("nombre"));
                usuario.setEmail(request.getParameter("email"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimiento = sdf.parse(request.getParameter("fechaNacimiento"));
                usuario.setFechaNacimiento(fechaNacimiento);

                String password = request.getParameter("password");
                if (password != null && !password.trim().isEmpty()) {
                    usuario.setPassword(password);
                }

                List<Horoscopo> listaHoroscopo = horoscopoDAO.obtenerHoroscopo();
                String animal = calcularAnimal(fechaNacimiento, listaHoroscopo);
                usuario.setAnimal(animal);

                usuarioDAO.actualizarUsuario(usuario);
                response.sendRedirect("listarUsuarios?mensaje=Usuario modificado exitosamente");
            } else {
                response.sendRedirect("listarUsuarios?error=Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("listarUsuarios?error=Error al modificar usuario");
        }
    }
}
