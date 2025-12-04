## Guía de uso — Servicios Médicos UDLAP

Esta guía explica, de forma sencilla, cómo usar la aplicación "Servicios Médicos UDLAP" para dos tipos de usuario: **Administrador** y **Alumno**. 

**Antes de empezar**
- Esta aplicación está escrita en Java y las ventanas principales son: `VentanaGeneral` (pantalla de inicio), `VentanaAdmin` (panel de administrador) y `VentanaAlumno` (panel de alumno).
- Para iniciar el programa en un entorno de desarrollo (VS Code con la extensión de Java) basta con ejecutar la clase principal `App` o abrir `VentanaGeneral`.

**Inicio de sesión**
1. Abra la aplicación; verá la ventana de inicio con campos `ID` y `Contraseña`.
2. Ingrese las credenciales:
   - Administrador: `ID = 12345`, `Contraseña = Admin.1`
   - Alumno: `ID = 123456`, `Contraseña = Alumno.1`
3. Pulse el botón `Iniciar`.

Si las credenciales son correctas, se abrirá el panel correspondiente según el rol.

**Modo Administrador** 
- Acceso: Iniciar con las credenciales de administrador.
- Panel: `VentanaAdmin` presenta botones y opciones para gestionar recursos y revisiones.

Funciones principales del Administrador:
- **Ver Registros**: abre la ventana `VerRegistros` que muestra pestañas con los registros guardados (recursos faltantes, sobrecarga y citas). Use el botón `Actualizar` para recargar la información.
- **Solicitar Recursos**: abre la interfaz para registrar recursos faltantes y opciones de validación. Esto guarda entradas en la base de datos de recursos.
- **Sobrecarga**: permite revisar y gestionar registros de pacientes en modo de alta demanda (sobrecarga). Se registran nombre, síntomas, prioridad y acción tomada.

Flujo de uso típico como Administrador:
1. Iniciar sesión como administrador.
2. Pulsar `Ver Registros` para revisar las entradas recientes.
3. Si detecta faltantes, usar `Solicitar Recursos` para registrar el recurso y marcar si está validado.
4. Para limpieza o comprobaciones en la base de datos, usar las herramientas de `VerRegistros` y refrescar.

Nota: Las acciones que escriben datos (insertar recursos, sobrecarga o citas) quedan persistidas en la base SQLite usada por la aplicación.

**Modo Alumno** 
- Acceso: Iniciar con las credenciales de alumno.
- Panel: `VentanaAlumno` con botones para agendar, modificar cita, usar el chatbot y opciones de urgencias.

Funciones principales del Alumno:
- **Agendar Cita** (`AgendarCita`):
  - Ingrese su `Nombre`, `Edad` y `Peso` en la pantalla de registro.
  - Pulse `Continuar` y seleccione un horario disponible en `SeleccionarHorario`.
  - Confirme para reservar la cita; la reserva se persiste en la base de datos.
- **Modificar Cita** (`ModificarCita`):
  - Permite seleccionar su nombre entre las citas registradas.
  - Cambiar edad, peso o horario y guardar los cambios.
  - Si sólo cambia edad/peso/horario, la operación modifica la cita existente.
- **Chatbot** (`Chatbot`):
  - Interfaz de ayuda para elegir síntomas o guías rápidas.
  - También sugiere opciones para agendar cita y permite guardar reportes simples.
- **Urgencias** (`Urgencias`):
  - Ofrece botones para acciones inmediatas (simuladas), por ejemplo `Llamar Servicios Médicos UDLAP` o `Llamar 911`.

Flujo de uso típico como Alumno:
1. Iniciar sesión con credenciales de alumno.
2. Pulsar `Agendar Cita` → completar datos → seleccionar horario → confirmar.
3. Para cambios futuros, entrar en `Modificar Cita`, seleccionar su nombre y actualizar datos.
4. Si necesita orientación, usar `Chatbot`.
5. En caso de emergencia, usar `Urgencias` para realizar la acción simulada.

**Ejemplos concretos**
- Agendar una cita:
  1. `Iniciar` como alumno.
  2. Abrir `Agendar Cita`.
  3. Rellenar `Nombre = Ana Martínez`, `Edad = 22`, `Peso = 65.5`.
  4. Pulsar `Continuar` y en la ventana `SeleccionarHorario` elegir `02:00 PM - 02:30 PM`.
  5. Confirmar la reserva.

- Modificar una cita existente:
  1. `Iniciar` como alumno.
  2. Abrir `Modificar Cita`.
  3. Seleccionar su nombre en el listado, cambiar edad o horario y pulsar `Guardar`.

**Validaciones y mensajes**
- El programa valida campos como edad (debe ser número entre 0 y 120) y peso (valor entre 0.0 y 300.0). Si se ingresan valores inválidos, aparecerá una advertencia y no se guardará la cita.
- En formularios, campos obligatorios (nombre, edad, peso y horario) deben completarse para avanzar.

**Dónde se guardan los datos**
- La aplicación usa una base de datos SQLite local (`formularios.db`). Las funciones de acceso están en `DatabaseHelper` y la conexión en `ConexionSQLite`.

**Consejos y resolución de problemas**
- Si la aplicación no arranca: asegúrese de tener Java instalado y configurado en su sistema.
- Si un horario aparece ocupado: pruebe otro horario o verifique que no haya otra cita con el mismo horario en `VerRegistros`.
- Si obtiene excepciones al introducir edad/peso, revise que sean números válidos (sin texto ni símbolos).