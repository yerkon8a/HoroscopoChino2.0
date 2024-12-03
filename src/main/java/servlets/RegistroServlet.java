package servlets;

import dao.UsuarioDAO;
import dao.UsuarioDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.HoroscopoDAO;
import dao.HoroscopoDAOImp;
import modelo.Usuario;
import modelo.Horoscopo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImp();
    private final HoroscopoDAO horoscopoDAO = new HoroscopoDAOImp();

    private String calcularAnimal(Date fechaNacimiento, List<Horoscopo> listaHoroscopo) {
        for (Horoscopo horoscopo : listaHoroscopo) {
            Date fechaInicio = horoscopo.getFecha_inicio();
            Date fechaFin = horoscopo.getFecha_fin();

            // Ajustar al año de nacimiento
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaNacimiento);
            int yearNacimiento = cal.get(Calendar.YEAR);

            Calendar calInicio = Calendar.getInstance();
            Calendar calFin = Calendar.getInstance();
            calInicio.setTime(fechaInicio);
            calFin.setTime(fechaFin);

            calInicio.set(Calendar.YEAR, yearNacimiento);
            calFin.set(Calendar.YEAR, yearNacimiento);

            // Si la fecha fin es menor que la fecha inicio, significa que cruza año nuevo
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Verificar si el usuario ya existe
            String username = request.getParameter("username");
            if (usuarioDAO.obtenerUsuario(username) != null) {
                request.setAttribute("error", "El nombre de usuario ya existe");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setUsername(username);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(request.getParameter("fechaNacimiento"));
            usuario.setFechaNacimiento(fechaNacimiento);

            // Calcular el animal
            List<Horoscopo> listaHoroscopo = horoscopoDAO.obtenerHoroscopo();
            String animal = calcularAnimal(fechaNacimiento, listaHoroscopo);
            usuario.setAnimal(animal);

            usuario.setPassword(request.getParameter("password"));
            usuarioDAO.crearUsuario(usuario);

            // Redirigir con mensaje de éxito
            response.sendRedirect("login.jsp?mensaje=registro_exitoso");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar el registro");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}
