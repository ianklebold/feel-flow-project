package com.equipo5.feelflowapp.constants.module.twelvesteps;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;


public class QuestionsConstantsTwelveSteps {
    public static final List<String> QUESTIONS_POOL_CLASSIC_TWELVE_STEPS = List.of(
        "¿Sientes que el propósito del equipo está claramente definido y que entiendes cómo contribuye tu trabajo a ese propósito?",
            "¿Sientes que tienes suficiente libertad para tomar decisiones importantes sobre tu trabajo diario?",
            "¿Sientes que el equipo te da las herramientas y el apoyo necesarios para mejorar tus habilidades y crecer profesionalmente?",
            "¿Sientes que existe un ambiente de conexión y apoyo entre los miembros del equipo?",
            "¿Sientes que tus contribuciones y logros son reconocidos y valorados dentro del equipo?",
            "¿Sientes que el trabajo que realizas tiene un impacto significativo en los resultados del equipo o la organización?",
            "¿Sientes que puedes expresar tus ideas, preocupaciones o errores sin temor a ser juzgado?",
            "¿Sientes que hay espacio para momentos de diversión y relajación en el equipo?",
            "¿Sientes que el equipo te apoya en tu crecimiento personal y profesional?",
            "¿Sientes que el equipo fomenta un ambiente de concentración y atención plena en las tareas?",
            "¿Sientes que la gratitud se expresa regularmente en el equipo?",
            "¿Sientes que tus valores y objetivos personales están alineados con los del equipo?"
    );

    public static final List<String> QUESTIONS_CATEGORY_TWELVE_STEPS = List.of(
        "Propósito",
        "Autonomía",
        "Maestría",
        "Social",
        "Descanso",
        "Disfrute",
        "Ejercicio",
        "Resiliencia",
        "Alegria",
        "Mindfulness",
        "Gratitud",
        "Servicial"
    );

    public static final Map<String,String> RECOMMENDATION = Map.ofEntries(
            entry("Propósito", "¡Hola! Parece que @user del equipo @equipo anda medio desconectado del propósito de lo que hace. Tal vez sea un buen momento para volver a alinear el “por qué” del trabajo con el día a día."),
            entry("Autonomía", "Notamos que @user del equipo @equipo siente que no tiene mucha libertad para decidir cómo hacer su trabajo. Un pequeño empujón de confianza puede motivarlo mucho."),
            entry("Maestría", "@user del equipo @equipo siente que no está creciendo en habilidades. Tal vez sea hora de ofrecerle algo nuevo que lo rete y entusiasme."),
            entry("Social", "Parece que @user se siente algo desconectado del equipo @equipo. Reforzar los lazos puede mejorar mucho el ambiente de trabajo."),
            entry("Descanso", "@user del equipo @equipo está señalando falta de descanso o pausas efectivas. Esto puede afectar su energía y claridad mental."),
            entry("Disfrute", "Parece que @user no está disfrutando mucho su día a día en el equipo @equipo. Un pequeño cambio puede traerle de nuevo el gusto por lo que hace."),
            entry("Ejercicio", "@user del equipo @equipo siente que no está cuidando su cuerpo con actividad física. Esto puede afectar tanto el ánimo como la concentración."),
            entry("Resiliencia", "@user parece estar teniendo dificultades para sobrellevar situaciones adversas en el equipo @equipo. Un poco de contención puede fortalecerlo."),
            entry("Alegría", "Detectamos que @user del equipo @equipo no está sintiendo alegría en su día a día laboral. Una chispa de energía puede reactivar mucho."),
            entry("Mindfulness", "@user del equipo @equipo reporta poca capacidad de estar enfocado y presente. Tal vez necesite menos ruido y más foco."),
            entry("Gratitud", "@user del equipo @equipo siente que hay poca gratitud en el ambiente. Cultivar el agradecimiento puede mejorar la energía general del equipo."),
            entry("Servicial", "@user percibe que en el equipo @equipo no hay mucha actitud de ayuda o colaboración. Esto puede generar distancia o desmotivación.")
    );

    public static final Map<String, List<String>> SUGGESTION = Map.ofEntries(
            entry("Propósito", List.of(
                    "Hacé una breve charla 1:1 para reconectar el trabajo con el propósito más general del equipo.",
                    "Compartí ejemplos o resultados donde se note el impacto colectivo del esfuerzo diario."
            )),
            entry("Autonomía", List.of(
                    "Permití mayor libertad para definir enfoques, horarios o prioridades de tareas.",
                    "Propone espacios donde puedan sugerir y liderar pequeñas iniciativas."
            )),
            entry("Maestría", List.of(
                    "Ofrecé cursos, libros o proyectos donde pueda desarrollar nuevas competencias.",
                    "Propone tareas fuera de su rutina habitual para que se ponga a prueba."
            )),
            entry("Social", List.of(
                    "Agendá un espacio informal como café virtual o charla sin agenda.",
                    "Proponé pequeñas dinámicas sociales en reuniones o en canales internos."
            )),
            entry("Descanso", List.of(
                    "Incentivá micro pausas durante el día y respeto del horario laboral.",
                    "Recordá que no todo es productividad: a veces, parar también es avanzar."
            )),
            entry("Disfrute", List.of(
                    "Consultale qué tipo de tareas o proyectos disfruta más e intentá reequilibrar su carga.",
                    "Incorporá toques de humor o momentos lúdicos al equipo: pequeños gestos suman."
            )),
            entry("Ejercicio", List.of(
                    "Compartí recursos para moverse (gympass, pausas activas).",
                    "Proponé desafíos de movimiento con el equipo ( estiramientos diarios, etc.)."
            )),
            entry("Resiliencia", List.of(
                    "Conversá con él/ella sobre lo que viene costando más y ofrecé tu apoyo directo.",
                    "Proponé herramientas o actividades para trabajar la resiliencia en equipo."
            )),
            entry("Alegría", List.of(
                    "Celebren logros chicos con más frecuencia.",
                    "Promové un entorno más liviano, con humor, cercanía y reconocimiento genuino."
            )),
            entry("Mindfulness", List.of(
                    "Incentivá bloques sin reuniones para trabajo profundo.",
                    "Al final de un sprint evalúa la utilidad y el provecho de los rituales ágiles implementados."
            )),
            entry("Gratitud", List.of(
                    "Proponé un espacio/ceremonia (retrospective) donde todos puedan agradecer algo.",
                    "Compartí agradecimientos/menciones por sprint vos mismo para dar el ejemplo."
            )),
            entry("Servicial", List.of(
                    "Reforzá el valor de “ayudarnos mutuamente” en espacios de equipo.",
                    "Reconocé públicamente las actitudes serviciales y propone dinámicas de colaboración cruzada."
            ))
    );

    public static final String[] ANSWERS_1_POOL_CLASSIC = {

                    "1. Entiendo mi contribución",
                    "2. Claro pero aveces tengo dudas",
                    "3. Algo confuso",
                    "4. Poco claro",
                    "5. Completamente confuso"
            };


    public static final String[] ANSWERS_2_POOL_CLASSIC = {

            "1. Siempre",
            "2. Casi siempre",
            "3. A veces puedo decidir",
            "4. Raramente",
            "5. Nunca tengo autonomía"
    };


    public static final String[] ANSWERS_3_POOL_CLASSIC = {

            "1. Sí, siempre.",
            "2. Hay apoyo suficiente",
            "3. A veces siento que me faltan oportunidades",
            "4. Rara vez recibo apoyo para mejorar.",
            "5. Nunca"
    };

    public static final String[] ANSWERS_4_POOL_CLASSIC = {

            "1. Sí, siempre",
            "2. Generalmente, si existe",
            "3. A veces hay conexión",
            "4. Raramente",
            "5. Nunca"
    };

    public static final String[] ANSWERS_5_POOL_CLASSIC = {

            "1. Siempre se reconocen y valoran",
            "2. Generalmente se reconocen",
            "3. A veces se reconocen",
            "4. Rara vez siento reconocimiento",
            "5. Nunca me siento reconocido."
    };

    public static final String[] ANSWERS_6_POOL_CLASSIC = {

            "1. Siempre",
            "2. Siento que estoy haciendo una diferencia.",
            "3. A veces siento que mi trabajo impacta.",
            "4. Rara vez siento.",
            "5. Nunca."
    };

    public static final String[] ANSWERS_7_POOL_CLASSIC = {

            "1. Siempre",
            "2. Si, puedo hablar con confianza",
            "3. A veces puedo expresarme",
            "4. Raramente me siento cómodo expresándome",
            "5. Nunca me siento seguro"
    };

    public static final String[] ANSWERS_8_POOL_CLASSIC = {

            "1. Siempre",
            "2. Hay oportunidades para relajarse",
            "3. A veces, no muchos",
            "4. Rara vez hay espacio para divertirse",
            "5. Nunca"
    };

    public static final String[] ANSWERS_9_POOL_CLASSIC = {

            "1. Siempre",
            "2. Me siento apoyado",
            "3. Si, podria ser mejor",
            "4. Rara vez siento apoyo para crecer",
            "5. Nunca"
    };

    public static final String[] ANSWERS_10_POOL_CLASSIC = {

            "1. Siempre se fomenta la atención plena",
            "2. Hay un buen ambiente para concentrarse",
            "3. Si, pero no siempre",
            "4. Rara vez se promueve la atención plena",
            "5. Nunca se fomenta la concentración"
    };

    public static final String[] ANSWERS_11_POOL_CLASSIC = {

            "1. Siempre se practica",
            "2. Generalmente se expresa gratitud",
            "3. A veces se expresa, pero no mucho",
            "4. Rara vez siento gratitud",
            "5. Nunca se practica la gratitud"
    };

    public static final String[] ANSWERS_12_POOL_CLASSIC = {

            "1. Totalmente alineados",
            "2. Bastante alineados",
            "3. Algo alineados",
            "4. Poco alineados",
            "5. Completamente desalineados"
    };

}
