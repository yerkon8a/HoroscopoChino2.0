HOROSOCOPO CHINO SERVLET Y JSP

DESCRIPCIÓN:
Se solicita la creación de un sistema web fabricado expresamente en lenguaje Java y la
especificación empresarial que sirva para consultar el animal representante en el horóscopo
chino de acuerdo con el año de nacimiento. El sistema debe poder registrar los datos básicos
del usuario para crear una sesión y así permitir el inicio de sesión. Usuarios no registrados no
pueden visualizar el buscador.
Consulta tu horóscopo chino.
Para conocer el signo del horóscopo chino, debemos dividir el año de nacimiento entre 12. El
resto, entre 0 y 11, está asociado a un signo según la siguiente tabla:


Para conocer el signo del horóscopo chino, se consulta primero la base de datos en un
método que reciba un usuario, obteniendo todos los signos con sus años almacenándolos en
una lista de entidad Horóscopo.
List<Horoscopo>listaHoroscopo =(new HoroscopoDAOImp()).obtenerHoroscopo();
Teniendo la lista de signos, con el usuario recibido en el método se puede calcular el
signo recorriendo la lista y verificando con los métodos .after (Prueba si esta fecha es
posterior a la fecha especificada) y .before (Prueba si esta fecha es anterior a la fecha
especificada), de esta manera se buscará el signo correspondiente mediante la fecha de
nacimiento del usuario.

EL SISTEMA DEBE CONTAR CON:
• Una página de inicio de sesión
• Una página de registro de usuarios, de búsqueda, de modificación y eliminación.
• Una página de consulta del signo chino
• Mensajes informativos para el usuario.
LA ENTREGA SE DESARROLLARÁ EN 2 HITOS:

• Hito 1: Desarrollo de capa de cliente utilizando jsp y servlets que harán el trabajo de
controlador de la aplicación.

• Hito 2: Desarrollo de la capa de negocio y persistencia utilizando jdbc para el manejo
de los datos.

HITO 1
1- Generar modelo de datos para soportar la aplicación con las siguientes tablas, además
de cargar el archivo horóscopo.xsl ubicado en el apoyo para la carga de los datos en la tabla
horóscopo.

CREATE TABLE HOROSCOPO(
ANIMAL VARCHAR2(30 BYTE),
FECHA_INICIO DATE,
FECHA_FIN DATE
);
CREATE TABLE USUARIOS(
ID NUMBER,
NOMBRE VARCHAR2(30 BYTE),
USERNAME VARCHAR2(30 BYTE),
EMAIL VARCHAR2(30 BYTE),
FECHA_NACIMIENTO DATE,
PASSWORD VARCHAR2(30 BYTE),
ANIMAL VARCHAR2(30 BYTE)
);

2- Generar un proyecto java web dinámico.

3- Generar páginas JSP para:
• Consulta de horóscopo chino
• Inicio de sesión
• Creación de usuario
• Modificación de usuario.
• Eliminación de usuarios.
• Listar usuarios

HITO 2
1- Generar paquetes de nombre:
• servlets
• dao
• modelo
• procesaconexion

2- En el paquete com.edutecno.modelo generar:
• Entidad java para horóscopo
• Entidad java para usuario

3- En el paquete com.edutecno.servlets generar los servlets para:
• Iniciar sesión
• Crear usuario
• Modificar usuario
• Eliminar Usuario
• Buscar usuarios
• Consultar horóscopo

4- En el paquete com.edutecno.procesaconexion se requiere generar una clase que se
encargue de la conexión a la base de datos utilizando jdbc.

5- En el paquete com.edutecno.dao generar las clases con los distintos métodos que
generaran la consulta a la base de datos.
