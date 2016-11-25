/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    //Si es true, esta activida la respuesta automatica
    private boolean respuestaAuto;

    private String asuntoRespuestaAuto;

    private String mensajeRespuestaAuto;
    //para poder ver el último email cuantas veces queramos.
    private MailItem lastEmail;
    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        respuestaAuto = false;
        asuntoRespuestaAuto = "";
        mensajeRespuestaAuto = "";
        
        lastEmail = null;
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        
        lastEmail = item;//para almacenar el último email recibido.
        if(respuestaAuto && item != null){

            sendMailItem(item.getFrom(), asuntoRespuestaAuto, mensajeRespuestaAuto);
        }
        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = getNextMailItem();
        lastEmail = item;//para almacenar el último email recibido.
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String subject, String message)
    {
        MailItem item = new MailItem(user, to, message, subject);
        server.post(item);
    }

    /**
     * Imprime por pantalla el numero de emails que hay
     * en el servidor para nosotros sin descargarlos
     */
    public void getNumMensajesNoLeidos()
    {
        System.out.println("El usuario "+ user + " tiene " + 
            server.howManyMailItems(user) + " mensajes."); 
    }

    
    /**
     * Permite configurar el texto del asunto y del mensaje de la respuesta
     * automica.
     */
    public void configurarRespuestaAutomatica(String mensajeAutoAnswer1,String asuntoAutoAnswer1)
    {
        mensajeRespuestaAuto = mensajeAutoAnswer1;
        asuntoRespuestaAuto = asuntoAutoAnswer1;
    }

    /**
     * Habilita o deshabilita la respuesta automática
     */   
    public void habilitaRespuestaAuto()
    {
        respuestaAuto = !respuestaAuto;

    }
    
    /**
     * muestra por pantalla cuantas veces queramos los datos del último mensaje recibido.
     */
    public void muestraUltimoEmail(){
        lastEmail.print();
    }
}

