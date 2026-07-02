/*     private static void iniciarSesion() {
        ImageIcon icono = new ImageIcon("imagenes/iniciarsesion.png");
    
        String usuario = (String) JOptionPane.showInputDialog(
                null, 
                "Usuario:", 
                "Inicio de Sesión", 
                JOptionPane.QUESTION_MESSAGE, 
                icono, 
                null, 
                "");
    
        if (usuario == null) {
            return;
        }

        String password = (String) JOptionPane.showInputDialog(
                null, 
                "Contraseña:", 
                "Inicio de Sesión", 
                JOptionPane.QUESTION_MESSAGE, 
                icono, 
                null, 
                "");
    
        if (password == null) {
            return;
        }
    
        for (User u : usuarios) {
            if (u.getName().equals(usuario) && u.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(null, "Login exitoso. Rol: " + u.getRol(), 
                                              "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarMenuPorRol(u.getRol(), u);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", 
                                      "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    private static void registrarAcudiente() {
        ImageIcon icono = new ImageIcon("imagenes/registrarusuario.png");
    
        String usuario;
        do {
            usuario = (String) JOptionPane.showInputDialog(
                    null,
                    "Usuario (solo letras):",
                    "Registro de Acudiente",
                    JOptionPane.QUESTION_MESSAGE,
                    icono,
                    null,
                    ""
            );
    
            if (usuario == null) return;
    
            if (!usuario.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario solo debe contener letras.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
                usuario = null;
            }
    
        } while (usuario == null);
    
        String password = (String) JOptionPane.showInputDialog(
                null,
                "Contraseña:",
                "Registro de Acudiente",
                JOptionPane.QUESTION_MESSAGE,
                icono,
                null,
                ""
        );
    
        if (password == null) return;
    
        String nombreEstudiante;
        String identificacionStr;
        Estudiante estudianteAsociado = null;
    
        do {
            nombreEstudiante = (String) JOptionPane.showInputDialog(
                    null,
                    "Nombre del estudiante:",
                    "Registro de Acudiente",
                    JOptionPane.QUESTION_MESSAGE,
                    icono,
                    null,
                    ""
            );
    
            if (nombreEstudiante == null) return;
    
            identificacionStr = (String) JOptionPane.showInputDialog(
                    null,
                    "Identificación del estudiante:",
                    "Registro de Acudiente",
                    JOptionPane.QUESTION_MESSAGE,
                    icono,
                    null,
                    ""
            );
    
            if (identificacionStr == null) return;
    
            if (!identificacionStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Identificación inválida. Ingrese solo números.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
    
            for (Estudiante e : estudiantes) {
                if (e.getNombre().equalsIgnoreCase(nombreEstudiante.trim()) &&
                    e.getIdentificacion().equals(identificacionStr.trim())) {
                    estudianteAsociado = e;
                    break;
                }
            }
    
            if (estudianteAsociado == null) {
                JOptionPane.showMessageDialog(null, "Estudiante no encontrado. Verifique el nombre y la identificación.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
    
        } while (estudianteAsociado == null);
    
        User nuevoUsuario = new Acudiente(usuario, password, nombreEstudiante, Integer.parseInt(identificacionStr));
        usuarios.add(nuevoUsuario);
    
        JOptionPane.showMessageDialog(null, "Acudiente registrado exitosamente.",
                                      "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }        

    private static void solicitarInformacionAcademica(User usuario) {
        ImageIcon icono = new ImageIcon("imagenes/solicitud.png");

        if (usuario instanceof Acudiente) {
            Acudiente acudiente = (Acudiente) usuario;
            String nombreEstudiante = acudiente.getNameEstudiante();

            Estudiante estudianteSeleccionado = null;
            for (Estudiante e : estudiantes) {
                if (e.getNombre().equals(nombreEstudiante)) {
                    estudianteSeleccionado = e;
                    break;
                }
            }

            if (estudianteSeleccionado.getinformacionActualizada() && acudiente.getApartadoInformacionAcademica()) {
                JOptionPane.showMessageDialog(null, 
                                            "La información del estudiante ya se encuentra actualizada y disponible para visualizar.", 
                                            "Información Disponible", 
                                            JOptionPane.INFORMATION_MESSAGE, 
                                            icono);
                return;
            }

            if (estudianteSeleccionado.getinformacionActualizada() && !acudiente.getApartadoInformacionAcademica()) {
                JOptionPane.showMessageDialog(null, 
                                            "La información del estudiante ya se encuentra actualizada, pero aún no está disponible para visualizar.", 
                                            "Información Disponible", 
                                            JOptionPane.INFORMATION_MESSAGE, 
                                            icono);
                return;
            }

            String nombreArchivo = "solicitudes.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
                writer.write("Solicitud de " + acudiente.getName() + 
                            " para el estudiante " + acudiente.getNameEstudiante());
                writer.newLine();
                JOptionPane.showMessageDialog(null, "Solicitud de información académica realizada.", 
                                            "Solicitud Enviada", JOptionPane.INFORMATION_MESSAGE, icono);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al manejar el archivo: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE, icono);
            }
        }
    }

    public static List<String> cargarApartadoNotificaciones() {
        ImageIcon icono = new ImageIcon("imagenes/notificaciones.png");
        String nombreArchivo = "solicitudes.txt";
        List<String> notificaciones = new ArrayList<>();
    
        if (!new File(nombreArchivo).exists()) {
            JOptionPane.showMessageDialog(null, "No hay notificaciones pendientes.", 
                                          "Notificaciones", JOptionPane.INFORMATION_MESSAGE, icono);
            return null;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                notificaciones.add(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE, icono);
            return null;
        }
    
        if (notificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay notificaciones pendientes.", 
                                          "Notificaciones", JOptionPane.INFORMATION_MESSAGE, icono);
            return null;
        }

        return notificaciones;
    }    
    
    private static void actualizarInformacionAcademica() {
        ImageIcon icono = new ImageIcon("imagenes/academica.png");
    
        String[] nombresEstudiantes = new String[estudiantes.size()];
        for (int i = 0; i < estudiantes.size(); i++) {
            nombresEstudiantes[i] = estudiantes.get(i).getNombre();
        }
    
        String seleccionado = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione un estudiante para editar:",
                "Actualizar Información Académica",
                JOptionPane.PLAIN_MESSAGE,
                icono, 
                nombresEstudiantes,
                nombresEstudiantes[0]
        );
    
        if (seleccionado == null) {
            return;
        }
    
        Estudiante estudianteSeleccionado = null;
        for (Estudiante e : estudiantes) {
            if (e.getNombre().equals(seleccionado)) {
                estudianteSeleccionado = e;
                break;
            }
        }
    
        String[] opcionesEdicion = {
            "Nombre",
            "Identificación",
            "Estado de matrícula",
            "Actualizar notas",
            "Cancelar"
        };
    
        while (true) {
            int opcionEdicion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione un atributo para editar:",
                    "Editar Estudiante: " + estudianteSeleccionado.getNombre(),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    icono, 
                    opcionesEdicion,
                    opcionesEdicion[0]
            );
    
            if (opcionEdicion == -1 || opcionEdicion == 4) {
                break; 
            }
    
            switch (opcionEdicion) {
                case 0:
                    String nuevoNombre = (String) JOptionPane.showInputDialog(
                            null,
                            "Nuevo nombre:",
                            "Actualizar Nombre",
                            JOptionPane.QUESTION_MESSAGE,
                            icono, 
                            null, 
                            estudianteSeleccionado.getNombre()
                    );
                    if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                        estudianteSeleccionado.setNombre(nuevoNombre);
                    }
                    break;
                case 1: 
                    String nuevaIdentificacion = (String) JOptionPane.showInputDialog(
                            null,
                            "Nueva identificación:",
                            "Actualizar Identificación",
                            JOptionPane.QUESTION_MESSAGE,
                            icono, 
                            null, 
                            estudianteSeleccionado.getIdentificacion()
                    );
                    if (nuevaIdentificacion != null && !nuevaIdentificacion.isEmpty()) {
                        estudianteSeleccionado.setIdentificacion(nuevaIdentificacion);
                    }
                    break;
                case 2: 
                    String nuevoEstado = (String) JOptionPane.showInputDialog(
                            null,
                            "Nuevo estado de matrícula (true/false):",
                            "Actualizar Estado de Matrícula",
                            JOptionPane.QUESTION_MESSAGE,
                            icono, 
                            null, 
                            estudianteSeleccionado.matricualPaga() ? "true" : "false"
                    );
                    if (nuevoEstado != null && !nuevoEstado.isEmpty()) {
                        estudianteSeleccionado.setmatriculaPaga(Boolean.parseBoolean(nuevoEstado));
                    }
                    break;
                case 3: 
                    boolean actualizado = actualizarNotas(estudianteSeleccionado);

                    if(actualizado) {
                        estudianteSeleccionado.setinformacionActualizada(true);
                    }

                    break;
            }
            
            if (opcionEdicion != 3) {
                JOptionPane.showMessageDialog(null, "Atributo actualizado exitosamente.", 
                                          "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE, icono);
            }

        }
    }
       
    private static void habilitarApartadoInformacionAcademica() {
        ImageIcon icono = new ImageIcon("imagenes/academica.png");
    
        List<Acudiente> acudientes = new ArrayList<>();
        
        for (User u : usuarios) {
            if (u instanceof Acudiente) {
                Acudiente acudiente = (Acudiente) u; 
                if (!acudiente.getApartadoInformacionAcademica()) {
                    acudientes.add(acudiente);
                }
            }
        }
    
        if (acudientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay acudientes en espera.", 
                                          "Información", JOptionPane.INFORMATION_MESSAGE, icono);
            return;
        }
    
        String[] nombresAcudientes = new String[acudientes.size()];
        for (int i = 0; i < acudientes.size(); i++) {
            nombresAcudientes[i] = acudientes.get(i).getName();
        }
    
        String seleccionado = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione un acudiente para modificar su acceso a la información académica:",
                "Habilitar Información Académica",
                JOptionPane.PLAIN_MESSAGE,
                icono, 
                nombresAcudientes,
                nombresAcudientes[0]
        );
    
        if (seleccionado == null) {
            return;
        }
    
        for (Acudiente a : acudientes) {
            if (a.getName().equals(seleccionado)) {
                boolean nuevoEstado = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea habilitar el acceso a la información académica para el acudiente " + a.getName() + "?",
                        "Confirmar Cambio",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icono 
                ) == JOptionPane.YES_OPTION;
    
                a.setApartadoInformacionAcademica(nuevoEstado);
    
                JOptionPane.showMessageDialog(null, "Acceso actualizado exitosamente.", 
                                              "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE, icono);
                return;
            }
        }
    }
      

    private static void visualizarInformacionAcademica(User usuario) {
        ImageIcon icono = new ImageIcon("imagenes/academica.png");
    
        if (usuario instanceof Acudiente) {
            Acudiente acudiente = (Acudiente) usuario;
            
            Estudiante estudianteSeleccionado = null;
            for (Estudiante e : estudiantes) {
                if (e.getNombre().equals(acudiente.getNameEstudiante())) {
                    estudianteSeleccionado = e;
                    break;
                }
        }

            if (!acudiente.getApartadoInformacionAcademica() && estudianteSeleccionado.getinformacionActualizada()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Tu apartado de información académica no está habilitado.",
                        "Acceso Denegado",
                        JOptionPane.WARNING_MESSAGE,
                        icono 
                );
                return;
            }

            if (acudiente.getApartadoInformacionAcademica() && (!estudianteSeleccionado.getinformacionActualizada())) {
                JOptionPane.showMessageDialog(
                        null,
                        "Tu apartado de información académica no está actualizado.",
                        "Acceso Denegado",
                        JOptionPane.WARNING_MESSAGE,
                        icono 
                );
                return;
            }

            if (!acudiente.getApartadoInformacionAcademica() && (!estudianteSeleccionado.getinformacionActualizada())) {
                JOptionPane.showMessageDialog(
                        null,
                        "Tu apartado de información académica no está habilitado.",
                        "Acceso Denegado",
                        JOptionPane.WARNING_MESSAGE,
                        icono 
                );
                return;
            }
    
            for (Estudiante e : estudiantes) {
                if (e.getNombre().equals(acudiente.getNameEstudiante())) {
                    String info = e.mostrarInformacion();
                    JOptionPane.showMessageDialog(
                            null,
                            info,
                            "Información Académica",
                            JOptionPane.INFORMATION_MESSAGE,
                            icono 
                    );
                    return;
                }
            }
    
            JOptionPane.showMessageDialog(
                    null,
                    "No se encontró información del estudiante asociado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    icono 
            );
        }
    }    
    
    private static boolean actualizarNotas(Estudiante estudianteSeleccionado) {
        ImageIcon icono = new ImageIcon("imagenes/notas.png");
        
        String[] opcionesEdicion = {
            "Nota matemáticas",
            "Nota biología",
            "Nota inglés",
            "Nota sociales",
            "Salir"
        };
        
        boolean nota1 = false;
        boolean nota2 = false;
        boolean nota3 = false;
        boolean nota4 = false;

        while (true) {
            int opcionEdicion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione un atributo para editar:",
                    "Editar Estudiante: " + estudianteSeleccionado.getNombre(),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    icono, 
                    opcionesEdicion,
                    opcionesEdicion[0]
            );
    
            if (opcionEdicion == -1 || opcionEdicion == 4) {
                break;
            }
    
            double notaActual = 0;
            switch (opcionEdicion) {
                case 0: notaActual = estudianteSeleccionado.getMatematicas(); break;
                case 1: notaActual = estudianteSeleccionado.getBiologia(); break;
                case 2: notaActual = estudianteSeleccionado.getIngles(); break;
                case 3: notaActual = estudianteSeleccionado.getSociales(); break;
            }
    
            double nuevaNota = 0;
            while (true) {
                String notaIngresada = (String) JOptionPane.showInputDialog(
                        null,
                        "Ingrese la nueva nota (0 - 5):",
                        "Actualizar " + opcionesEdicion[opcionEdicion],
                        JOptionPane.QUESTION_MESSAGE,
                        icono,
                        null,
                        notaActual
                );
    
                if (notaIngresada == null) {
                    break; 
                }
    
                try {
                    nuevaNota = Double.parseDouble(notaIngresada);
                    if (nuevaNota >= 0 && nuevaNota <= 5) {
                        break; 
                    } else {
                        JOptionPane.showMessageDialog(null, "La nota debe estar entre 0 y 5.", 
                                                      "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ingrese un número válido.", 
                                                  "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
    
            if (nuevaNota >= 0 && nuevaNota <= 5) {
                switch (opcionEdicion) {
                    case 0: estudianteSeleccionado.setMatematicas(nuevaNota); nota1=true; break;
                    case 1: estudianteSeleccionado.setBiologia(nuevaNota); nota2=true; break;
                    case 2: estudianteSeleccionado.setIngles(nuevaNota); nota3=true; break;
                    case 3: estudianteSeleccionado.setSociales(nuevaNota); nota4=true; break;
                }
    
                JOptionPane.showMessageDialog(null, "Nota actualizada exitosamente.",
                                              "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE, icono);
            }
        }

        if (nota1 && nota2 && nota3 && nota4) {
            return true;
        } else {
            return false;
        }
    }      
    
    private static void mostrarMenuPorRol(String rol, User usuario) {
        switch (rol) {
            case "acudiente" -> mostrarMenuAcudiente(usuario);
            case "docente" -> mostrarMenuDocente(usuario);
            case "rectora" -> mostrarMenuRector(usuario);
            case "secretaria" -> mostrarMenuSecretaria(usuario);
            default -> JOptionPane.showMessageDialog(null, "Rol no reconocido.");
        }
    }

    private static void mostrarMenuAcudiente(User usuario) {
        if (usuario instanceof Acudiente) {
            Acudiente acudiente = (Acudiente) usuario;
            
            Estudiante estudianteSeleccionado = null;
            for (Estudiante e : estudiantes) {
                if (e.getNombre().equals(acudiente.getNameEstudiante())) {
                    estudianteSeleccionado = e;
                    break;
                }
            }

            if (acudiente.getApartadoInformacionAcademica() && estudianteSeleccionado.getinformacionActualizada()) {
                JOptionPane.showMessageDialog(null, "Tu apartado de información académica está actualizado y disponible para visualizar.");
            }
        }
    
        while (true) {
            String[] opciones = {
                "Solicitar información académica",
                "Visualizar apartado de información académica",
                "Cerrar sesión"
            };
    
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Menú Acudiente",
                    "Opciones",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );
    
            switch (opcion) {
                case 0 -> solicitarInformacionAcademica(usuario);
                case 1 -> visualizarInformacionAcademica(usuario);
                case 2 -> { return; }
            }
        }
    }    

    private static void mostrarMenuDocente(User usuario) {
        while (true) {
            String[] opciones = {"Funcionalidad sin implementar", "Cerrar sesión"};
            int opcion = JOptionPane.showOptionDialog(null, "Menú Docente", "Opciones",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch (opcion) {
                case 0 -> JOptionPane.showMessageDialog(null, "Funcionalidad sin implementar");
                case 1 -> { return; }
            }
        }
    }

    private static void mostrarMenuRector(User usuario) {
        while (true) {
            String[] opciones = {"Habilitar información académica", "Cerrar sesión"};
            int opcion = JOptionPane.showOptionDialog(null, "Menú Rectora", "Opciones",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch (opcion) {
                case 0 -> habilitarApartadoInformacionAcademica(); 
                case 1 -> { return; }
            }
        }
    }

    private static void mostrarMenuSecretaria(User usuario) {
        while (true) {
            String[] opciones = {"Revisar bandeja de notificaciones", "Actualizar información académica", "Cerrar sesión"};
            int opcion = JOptionPane.showOptionDialog(null, "Menú Secretaria", "Opciones",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch (opcion) {
                case 0 -> cargarApartadoNotificaciones();
                case 1 -> actualizarInformacionAcademica();
                case 2 -> { return; }
            }
        }
    }
*/