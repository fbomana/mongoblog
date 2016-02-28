# mongoblog
A simple blog using mongodb and spring for learning porpouses

Objetivos:

- Home:
	- Login
	- Configuración de usuarios.
		- Solo un usuario tiene permiso para invitar a otros usuarios.
			- Se envía un correo con una contraseña a la dirección de la invitación.
			- El usuario invitado cuando se loga por primera vez tiene que crear su nick y rellenar el resto de la información.
			- El usuario invitado puede cambiar su contraseña.
				- Sistema que usa como salt el timestamp de la invitación y la dirección de correo de la invitación. ( hay que guardarla en un sitio a parte de la dirección de correo normal ).
		- Un usuario puede cambiar su nick en cualquier momento.
		
	
- Blog multiusuario.
	- Cada usuario registrado tiene su propio blog.
	- Cada usuario puede dar permisos de editor a otros usuarios sobre su blog.
	- Single page interface para la portada de cada blog. http://localhost:8080/usuario
		- Usamos ajax para la paginación de las entradas. Por defecto se ven las últimas 10.
		- Mostramos los primeros x caracteres de cada entrada.
		- Cada entrada del blog tendrá la url http://localhost:8080/usuario/entrada.
			- En el detalle de la entrada se muestran la entrada y los comentarios.
	- Las entradas soportan markdown de la misma manera que en el recetario, usando vue.js.
	- Las entradas tienen fecha de publicación a partir de la cual son visibles. ( tenerlo en cuenta en la búsqueda ).
			
	
- Comentarios:
	- Con usuario.
	- Anónimos.
	- Los editores pueden eliminar los comentarios que consideren inapropiados.

- Busqueda de texto:
	- Usamos MapReduce para crear un índice de la entrada que se actualiza cada vez que se crea o modifica una entrada en el blog.
	- Usamos MapReduce para buscar en el índice los resultados.
	
	
Plataforma:

	- Spring para el MVC
	- MongoDB para la persistencia. Mirar si existe integración con Spring y como funciona.
	- Desarrollo: maven sobre eclipse.
	- Tomcat.
	- Mongoblog.
	
Fases: 
	0.1.0 - Pantalla de Login.
		  - Usuario admin en el primer arranque.
		  - Invitar a usuarios y personalización.
		  - Cambiar contraseña.
		  
	0.1.1 - Edición de entradas.
		  - Single page interface con las entradas de cada portada
		  - Paginación.
		  
	0.1.2 - Pantalla de detalle de las entradas.
		  - Edición de comentarios.
		  - Eliminación de comentarios por parte de los editores/moderadores.
		  
	0.1.3 - Indexación de las entradas en la edición.
		  - Refrescar la indexación de las entradas en batch.
		  - Búsquedas sobre el índice + función de ranking.
		  
Documentación:

	http://docs.spring.io/spring-data/data-mongo/docs/1.8.4.RELEASE/reference/html/
